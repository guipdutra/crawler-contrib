(ns crawler-contrib.core
  (:require [crawler-contrib.contributions :refer [get-greatest-contributors]]
            [crawler-contrib.users :refer [filter-by-brazilians]]))

(def github-url
  "https://github.com/")

(def concat-github-link-and-location
  (fn [user]
         (str github-url (:name user) " - " (:location user))))

(defn format-output-with-link-and-location [users]
  (map concat-github-link-and-location users))

(defn -main []
  (dorun
    (map println (format-output-with-link-and-location (filter-by-brazilians (get-greatest-contributors {:number-of-commits 5}))))))
