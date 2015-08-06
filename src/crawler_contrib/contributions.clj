(ns crawler-contrib.contributions
  (:use crawler-contrib.github-api-wrapper))

(defn map-users-to-total-commits [contributions]
  (into {}
        (remove-any-nil-hash-value
          (sum-all-project-commits
            (create-hash-with-user-and-total-commits contributions)))))

(defn create-hash-with-user-and-total-commits [contributions]
  (map (fn [contribution] (hash-map (:login (:author contribution)) (:total contribution))) contributions))

(defn remove-any-nil-hash-value [user-with-commits]
  (remove (fn [[k v]]
            (nil? v)) user-with-commits))

(defn sum-all-project-commits [user-with-commits]
  (apply merge-with
         (fn [a b] (if (and (number? a) (number? b)) (+ a b)))
         user-with-commits))

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
  (sort-by-total-commits
    (sum-all-project-commits
      (map (fn [contrib]
             (group-users-by-total-contributions contrib))
           (get-all-repositories-contributions (get-all-repositories))))))
