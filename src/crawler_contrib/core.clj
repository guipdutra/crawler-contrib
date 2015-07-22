(ns crawler-contrib.core
  (require [tentacles.repos :as repos]))


(take 1 (repos/user-repos "guipdutra"))

(defn -main []
 (println "test"))
