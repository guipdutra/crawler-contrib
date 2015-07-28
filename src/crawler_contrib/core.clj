(ns crawler-contrib.core
  (require [tentacles.repos :as repos]
           [tentacles.pulls :as pulls]))

(def auth )


(defn map-users-to-total-commits [contributions]
  (into {} (map (fn [contribution]
                  (hash-map (:login (:author contribution)) (:total contribution)))
                contributions)))

(defn sort-by-total-commits [users-with-commits]
  (sort-by val > users-with-commits))

(defn group-users-by-total-contributions [contributions]
  (cond
    (nil? contributions) nil
    (empty? contributions) nil
    :else
    (map-users-to-total-commits contributions)))

(defn get-repository-statistics [repo]
  (repos/contributor-statistics (:login (:owner repo)) (:name repo) auth))

(defn get-all-repositories []
  (take 3 (repos/all-repos auth)))

(defn get-all-repositories-contributions [all-repositories]
  (map (fn [repo] (get-repository-statistics repo)) all-repositories))

(defn group-all-contributions-by-user-for-all-repositories []
  (into {} (mapcat (fn [contrib] (group-users-by-total-contributions contrib)) (get-all-repositories-contributions (get-all-repositories)))))

(defn -main []
 (println (group-all-contributions-by-user-for-all-repositories)))
