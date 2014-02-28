(ns websl.routes
	(:gen-class)
	(:require 
  		[clojure.java.io :as io]
  		[websl.mime :as mime])
  	(:import
  		[java.io InputStreamReader]
  		[org.eclipse.jetty.server Server]
  		[org.eclipse.jetty.server.handler AbstractHandler]))

(def GET :GET)

(def POST :POST)

(def OK javax.servlet.http.HttpServletResponse/SC_OK)

(def NOT-FOUND javax.servlet.http.HttpServletResponse/SC_NOT_FOUND)

(defn load-content [resource-name]
    (let [f (io/file resource-name)]
      (slurp f)))

(defn load-resource-content [resource-path]
    (load-content (str "resources/" resource-path)))

(defn static-file [filepath]
	(fn [target base-request request response]
	    (let [content (load-resource-content filepath)
	    	mime (mime/mimetype filepath)]
	    	(.setContentType response (str mime ";charset=utf-8"))
			(.setStatus response OK)
			(.setHandled base-request true)
	        (.println (.getWriter response) content))))

(defn not-found-handler []
	(fn [target base-request request response]
		(.setContentType response "text/html;charset=utf-8")
		(.setStatus response NOT-FOUND)
		(.setHandled base-request true)
    	(with-open [rdr (io/reader (io/resource "not-found.html"))]
		    (doseq [line (line-seq rdr)]
		        (.println (.getWriter response) line)))))

(defn static-string [response-string]
	(fn [target base-request request response]
		(.setContentType response "text/html;charset=utf-8")
		(.setStatus response OK)
		(.setHandled base-request true)
		(.println (.getWriter response) response-string)))


(defn method-of [request]
	(keyword (.getMethod request)))

(defn url-path-of [request]
	(.getRequestURI request))


(defn handle-route [route-map]
	(proxy [AbstractHandler]
		[]
		(handle [target base-request request response]
			(let [method (keyword (method-of request))
				url-path (url-path-of request)
				handlers (method route-map)
				hdlr (get handlers url-path (not-found-handler))]
				(hdlr target base-request request response)))))

(defn merge-routes [route-map route]
	(let [method (-> route keys first)
		path-handler (method route)
		url-path (-> path-handler keys first)
		handler (get path-handler url-path)]
		(assoc route-map method (assoc (method route-map) url-path handler))))

(defn service [config & routes]
	(let [port (:port config)
		server (new Server port)
		route-map (reduce merge-routes {} routes)]
		(println "route-map: " route-map)
		(println "serving on port: " port "...")
		(.setHandler server (handle-route route-map))
		(.start server)
		(.join server)))

(defn route [method url-path handler]
	{method {url-path handler}})
