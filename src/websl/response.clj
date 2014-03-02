(ns websl.response
  (:gen-class)
  (:require [clojure.java.io :as io]
            [websl.mime :as mime]))

(def OK javax.servlet.http.HttpServletResponse/SC_OK)

(def NOT-FOUND javax.servlet.http.HttpServletResponse/SC_NOT_FOUND)

(defn ok [response]
  (assoc response :status OK))

(defn not-found [response]
  (assoc response :status NOT-FOUND))

(defn text-html [response]
  (assoc response :content-type (mime/mimetype "html")))

(defn application-javascript [response]
  (assoc response :content-type (mime/mimetype "js")))

(defn static [path]
  (let [resource-path (str "resources/" path)]
    {:content #(io/reader resource-path)}))

