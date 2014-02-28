(ns websl.core
	(:use websl.routes))

(defn -main [& args]
  	(service {:port 8080}
  		(route GET "/index.html" (static-file "index.html"))
  		(route GET "/other.html" (static-file "other.html"))
  		(route GET "/js/main.js" (static-file "/js/main.js"))))


;TODO:
;	response map interpreted by handler to build up correct response code, mime-type, encoding, headers...
;	header map
; 	doto
;	ClojureScript to create responsive design dsl:
;		- page snippets which resize to % of parent based on client screen size
;		- define snippets completely in ClojureScript
;		- layout snippets completely in ClojureScript
;		- all html and css dynamically generated
;		- snippet, component, glyph, layout, layout -> component
; 		- snippet requested and delivered as ClojureScript/JavaScript
