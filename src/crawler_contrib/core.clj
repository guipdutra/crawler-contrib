(ns crawler-contrib.core
  (require [tentacles.repos :as repos]
           [tentacles.pulls :as pulls]))

;(:login (:user (:head (first (pulls/pulls "rails" "rails")))))

;((first (repos/user-repos "guipdutra")) :name)

(defn group-users-with-pull-request-count [pulls]
  nil)

(defn -main []
 (println "test"))
