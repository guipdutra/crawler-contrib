(ns crawler-contrib.contributions
  (:use crawler-contrib.github-api-wrapper))

(defn map-users-to-total-commits [contributions]
  (apply merge-with + (map (fn [contribution]
                             (hash-map (:login (:author contribution)) (:total contribution)))
                           (remove nil? contributions))))

(defn sort-by-total-commits [users-with-commits]
  (sort-by val > users-with-commits))

(defn group-users-by-total-contributions [contributions]
  (cond
    (nil? contributions) nil
    (empty? contributions) nil
    :else
    (map-users-to-total-commits contributions)))

(defn get-all-repositories-contributions [all-repositories]
  (map (fn [repo] (get-repository-statistics repo)) all-repositories))

(defn group-all-contributions-by-user-for-all-repositories []
  (map (fn [contrib] (group-users-by-total-contributions contrib))
       (get-all-repositories-contributions (get-all-repositories))))
