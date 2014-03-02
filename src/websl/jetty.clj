(ns websl.jetty
  (:gen-class)
  (:require [websl.routes :as routes]
            [websl.request :as request]
            [websl.response :as response])
  (:import [org.eclipse.jetty.server Server]
           [org.eclipse.jetty.server.handler AbstractHandler]))

(defn response-handler [response-map]
  (fn [target base-req req res]
    (.setContentType res (str (:content-type response-map) ";charset=utf-8"))
    (.setStatus res (:status response-map))
    (.setHandled base-req true)
    (with-open [rdr ((:content response-map))]
      (doseq [line (line-seq rdr)]
        (.println (.getWriter res) line)))))

(defn handle-route [route-map]
  (proxy [AbstractHandler]
    []
    (handle [target base-req req res]
      (let [method (keyword (request/method-of req))
            url-path (request/url-path-of req)
            handlers (method (:routes route-map))
            response-map (get handlers url-path (:default route-map))
            hdlr (response-handler response-map)]
        (hdlr target base-req req res)))))

(defn service [config & routes]
  (let [port (:port config)
        server (new Server port)
        route-map (routes/route-map routes)]
    (println "serving on port: " port "...")
    (.setHandler server (handle-route route-map))
    (.start server)
    (.join server)))
