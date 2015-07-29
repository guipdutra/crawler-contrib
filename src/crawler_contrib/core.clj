(ns crawler-contrib.core
  (:use crawler-contrib.contributions))

(defn -main []
  (println (group-all-contributions-by-user-for-all-repositories)))
