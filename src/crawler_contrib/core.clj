(ns crawler-contrib.core
  (:require [crawler-contrib.contributions :refer [get-greatest-contributors]]))

(defn -main []
  (dorun
    (map println (get-greatest-contributors {:number-of-commits 5}))))
