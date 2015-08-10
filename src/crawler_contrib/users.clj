(ns crawler-contrib.users
  (:require [crawler-contrib.github-api-wrapper
             :refer [get-user]]))

(def defualt-locations
  ["Belo Horizonte" "Brazil" "São Paulo" "Porto Alegre" "Recife" "Rio de Janeiro"])

(defn extract-location [user-info]
  (let [location (:location user-info)]
    (if (clojure.string/blank? location) "" location)))

(def is-location-valid?
  (fn [user location] (.contains (extract-location user) location)))

(defn is-location-valid-for-user? [user]
  (partial is-location-valid? user))

(def is-brazilian?
  (fn [user]
   (some (is-location-valid-for-user? user) defualt-locations)))

(def extract-login
  (fn [user-info] (:login user-info)))

(def get-user-info
 (fn [user-name] (get-user user-name)))

(defn filter-by-brazilians [users]
  (map extract-login (filter is-brazilian? (pmap get-user-info users))))
