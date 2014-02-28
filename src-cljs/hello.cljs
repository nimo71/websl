(ns hello)
(defn ^:export greet [n]
  (str "Hello cljs" n))