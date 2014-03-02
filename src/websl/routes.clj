(ns websl.routes
  (:gen-class)
	(:require [websl.request :as request]
            [websl.response :as response]))

(defn merge-routes [routes route]
  (let [method (-> route keys first)
        path-handler (method route)
        url-path (-> path-handler keys first)
        handler (get path-handler url-path)]
    (assoc routes method (assoc (method routes) url-path handler))))

(defn route-map [routes]
  (let [default-route (first (filter #(contains? % :default) routes))
        known-routes (filter #(not (contains? % :default)) routes)]
     (merge default-route {:routes (reduce merge-routes {} known-routes)})))

(defn route [method url-path handler] {method {url-path handler}})

(defn default [response] {:default response})
