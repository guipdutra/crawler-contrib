(ns crawler-contrib.contributions
  (:require [crawler-contrib.github-api-wrapper
             :refer [get-all-repositories
                     get-repository-statistics]]))

(def extract-login-and-total-commits
  (fn [contribution]
    (hash-map (:login (:author contribution))
              (:total contribution))))

(def value-nil? (fn [[k v]] (nil? v)))

(def sum-if-is-both-numbers
  (fn [number next-number]
    (if (and (number? number) (number? next-number))
      (+ number next-number))))

(def extract-user-name
  (fn [user-with-commit]
           (first user-with-commit)))

(defn create-hash-with-user-and-total-commits [contributions]
  (map extract-login-and-total-commits contributions))

(defn remove-any-nil-hash-value [user-with-commits]
  (remove value-nil? user-with-commits))

(defn sum-all-project-commits [user-with-commits]
  (apply merge-with sum-if-is-both-numbers user-with-commits))

(defn map-users-to-total-commits [contributions]
  (into {}
        (-> (create-hash-with-user-and-total-commits contributions)
            (sum-all-project-commits)
            (remove-any-nil-hash-value))))

(defn sort-by-total-commits [users-with-commits]
  (sort-by val > users-with-commits))

(defn group-users-by-total-contributions [contributions]
  (cond
    (nil? contributions) nil
    (empty? contributions) nil
    :else (map-users-to-total-commits contributions)))

(defn get-all-repositories-contributions [all-repositories]
  (map (fn [repo]
         (get-repository-statistics repo)) all-repositories))

(defn group-all-contributions-by-user-for-all-repositories []
  (-> (map (fn [contrib]
             (group-users-by-total-contributions contrib))
           (get-all-repositories-contributions (get-all-repositories)))
      sum-all-project-commits
      sort-by-total-commits))

(defn get-greatest-contributors [filter-options]
    (map extract-user-name
      (group-all-contributions-by-user-for-all-repositories)))
