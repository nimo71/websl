(ns websl.request
  (:gen-class))

(def GET :GET)

(def POST :POST)

(defn method-of [request]
  (keyword (.getMethod request)))

(defn url-path-of [request]
  (.getRequestURI request))
