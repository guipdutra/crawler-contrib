(ns crawler-contrib.core
  (:require [crawler-contrib.contributions :refer [get-greatest-contributors]]
            [crawler-contrib.users :refer [filter-by-brazilians]]))

(defn -main []
  (dorun
    (map println (filter-by-brazilians (get-greatest-contributors {:number-of-commits 5})))))
