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
    (keyword? contributions) nil
    (nil? contributions) nil
    (empty? contributions) nil
    :else (map-users-to-total-commits contributions)))

(defn print-information-about-getting-repositories [repo-count total-repositories]
  (println (str "Getting " repo-count " of " total-repositories)))

(defn get-all-repositories-contributions [all-repositories]
  (let [repo-count (atom 0)]
    (map (fn [repo]
           (do
             (print-information-about-getting-repositories (swap! repo-count inc)
                                                           (count all-repositories))
             (get-repository-statistics repo))) all-repositories)))

(defn group-all-contributions-by-user-for-all-repositories []
  (-> (map (fn [contrib]
             (group-users-by-total-contributions contrib))
           (get-all-repositories-contributions (get-all-repositories)))
      sum-all-project-commits
      sort-by-total-commits))


(defn extract-commit-number [user-with-commit]
  (last user-with-commit))

(defn filter-by-number-of-commits [users-with-commits number-of-commits]
  (filter
    (fn [user-with-commit]
      (> (extract-commit-number user-with-commit) number-of-commits))
    users-with-commits))

(defn get-greatest-contributors
  ([] (map extract-user-name (group-all-contributions-by-user-for-all-repositories)))

  ([{:keys [number-of-commits]}]
   (map extract-user-name (filter-by-number-of-commits
                            (group-all-contributions-by-user-for-all-repositories) number-of-commits))))
