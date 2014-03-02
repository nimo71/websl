(ns websl.core
	(:use websl.jetty
        websl.routes
        websl.request
        websl.response))

(defn -main [& args]
  (service {:port 8080}
    (route GET "/index.html" (-> "index.html" static text-html ok))
    (route GET "/other.html" (-> "other.html" static text-html ok))
    (route GET "/js/main.js" (-> "/js/main.js" static application-javascript ok))
    (default (-> "not-found.html" static text-html not-found))))


;TODO:
; Create form using ClojureScript on index.html
;	ClojureScript to create responsive design dsl:
;		- page snippets which resize to % of parent based on client screen size
;		- define snippets completely in ClojureScript
;		- layout snippets completely in ClojureScript
;		- all html and css dynamically generated
;		- snippet, component, glyph, layout, layout -> component
; 		- snippet requested and delivered as ClojureScript/JavaScript
