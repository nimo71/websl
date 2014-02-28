(defproject websl "0.1.0-SNAPSHOT"
    :description "FIXME: write description"
  	:url "http://example.com/FIXME"
  	:license {:name "Eclipse Public License"
        :url "http://www.eclipse.org/legal/epl-v10.html"}
    :plugins [
        [lein-cljsbuild "1.0.2"]
        [lein-autoreload "0.1.0"]]
  	:dependencies [
  		  [org.clojure/clojure "1.5.1"]
  		  [org.eclipse.jetty.aggregate/jetty-all "9.1.2.v20140210"]
        [org.clojure/clojurescript "0.0-2173"]]
    :main ^:skip-aot websl.core
    :target-path "target/%s"
    :profiles {:uberjar {:aot :all}}
    :cljsbuild {
        :builds [{
            ; The path to the top-level ClojureScript source directory:
            :source-paths ["src-cljs"]
            ; The standard ClojureScript compiler options:
            ; (See the ClojureScript compiler documentation for details.)
            :compiler {
                :output-to "resources/js/main.js"  ; default: target/cljsbuild-main.js
                :optimizations :whitespace
                :pretty-print true}}]})
