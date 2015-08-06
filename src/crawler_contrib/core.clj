(ns crawler-contrib.core
  (:require [crawler-contrib.contributions
             :refer [group-all-contributions-by-user-for-all-repositories]]))

(defn -main []
  (println (group-all-contributions-by-user-for-all-repositories)))
