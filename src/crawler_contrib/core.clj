(ns crawler-contrib.core
  (require [tentacles.repos :as repos]
           [tentacles.pulls :as pulls]))

(:login (:user (:head (first (pulls/pulls "rails" "rails")))))

(defn group-users-with-pull-request-count [pulls]
  (cond
    (nil? pulls) nil
    (empty? pulls) nil
    :else (sort-by val > (frequencies (map (fn [pull] (:login (:user (:head pull)))) pulls)))))


(defn -main []
 (println (sort-by val > (group-users-with-pull-request-count (pulls/pulls "rails" "rails" {:client_id "baf5a0884abc3cb4a49f", :client_token "d164270549a9a3246aa1a50e560bbd37cfbe4018"})))))
