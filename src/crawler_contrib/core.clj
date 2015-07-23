(ns crawler-contrib.core
  (require [tentacles.repos :as repos]
           [tentacles.pulls :as pulls]))

(defn group-users-by-total-contributions [contributions]
  (cond
    (nil? contributions) nil
    (empty? contributions) nil
    :else
    (-> (map-users-to-total-commits contributions) sort-by-total-commits)))

(defn map-users-to-total-commits [contributions]
  (into {} (map (fn [contribution]
                  (hash-map (:login (:author contribution)) (:total contribution)))
                contributions)))

(defn sort-by-total-commits [users-with-commits]
  (sort-by val > users-with-commits))

(defn -main []
 (println (group-users-by-total-contributions (repos/contributor-statistics "rails" "rails" {:auth "guipdutra:guipereira08"}))))
