(ns crawler-contrib.core
  (require [tentacles.repos :as repos]
           [tentacles.pulls :as pulls]))

(:login (:user (:head (first (pulls/pulls "rails" "rails")))))

(defn group-users-with-pull-request-count [pulls]
  (cond
    (nil? pulls) nil
    (empty? pulls) nil
    :else (frequencies (map (fn [pull] (:login (:user (:head pull)))) pulls))))


(defn -main []
 (println (group-users-with-pull-request-count (count (pulls/pulls "rails" "rails"))))
