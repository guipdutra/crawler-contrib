(ns crawler-contrib.contributions
  (:require [crawler-contrib.github-api-wrapper
             :refer [get-all-repositories
                     get-repository-statistics]]))

(defn create-hash-with-user-and-total-commits [contributions]
  (map (fn [contribution]
         (hash-map (:login (:author contribution))
                   (:total contribution)))
       contributions))

(defn remove-any-nil-hash-value [user-with-commits]
  (remove (fn [[k v]]
            (nil? v)) user-with-commits))

(defn sum-if-is-both-numbers [number next-number]
  (if (and (number? number) (number? next-number))
    (+ number next-number)))

(defn sum-all-project-commits [user-with-commits]
  (apply
    merge-with (fn [a b]
                 (sum-if-is-both-numbers a b))
    user-with-commits))

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
    :else
    (map-users-to-total-commits contributions)))

(defn get-all-repositories-contributions [all-repositories]
  (map (fn [repo] (get-repository-statistics repo)) all-repositories))

(defn group-all-contributions-by-user-for-all-repositories []
  (-> (map (fn [contrib]
             (group-users-by-total-contributions contrib))
           (get-all-repositories-contributions (get-all-repositories)))
      sum-all-project-commits
      sort-by-total-commits))

(defn get-bigger-contributors []
    (map (fn [user-with-commit]
           (first user-with-commit)) (group-all-contributions-by-user-for-all-repositories)))
