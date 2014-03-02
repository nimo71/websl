(ns websl.mime
  (:gen-class))

(def mimetypes {
  :html "text/html",
  :js "application/javascript"})

(defn file-extension [filepath]
  (let [i (.lastIndexOf filepath ".")
        ext (.substring filepath (inc i))]
        ext))

(defn mimetype [filepath]
	(let [ext (file-extension filepath)
		ext-key (keyword ext)]
		(ext-key mimetypes)))

; TODO:
; application/javascript is correct but for compatibilty reasons we should check the user-agent
; ie 6-8 need text/javascript
