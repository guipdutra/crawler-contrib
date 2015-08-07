(ns crawler-contrib.users
  (:require [crawler-contrib.github-api-wrapper
             :refer [get-user]]))

(defn extract-location [user-info]
  (let [location (:location (get-user user-info))]
    (if (clojure.string/blank? location) "" location)))

(def is-brazilian?
  (fn [user] (.contains (extract-location user) "Brazil")))

(defn filter-by-brazilians [users]
  (filter is-brazilian? users))
