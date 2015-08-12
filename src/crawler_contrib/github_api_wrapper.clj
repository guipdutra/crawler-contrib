(ns crawler-contrib.github-api-wrapper
  (require [tentacles.repos :as repos]
           [tentacles.users :as users]
           [tentacles.core :as core]
           [tentacles.pulls :as pulls]))

(def valid-github-tokens
  (remove nil? (map (fn [token] (System/getenv token)) ["GITHUB_ACCESS_TOKEN_1"
                                                        "GITHUB_ACCESS_TOKEN_2"
                                                        "GITHUB_ACCESS_TOKEN_3"
                                                        "GITHUB_ACCESS_TOKEN_4"])))

(defn get-remaining-request-by-token [token]
  (:remaining (:core (:resources (core/rate-limit {:oauth-token token})))))

(def token-request-is-greater-than-zero?
  (fn [token] (> (get-remaining-request-by-token token) 0)))

(defn pick-token []
  (loop [token (rand-nth valid-github-tokens)]
    (if (token-request-is-greater-than-zero? token)
      token
      (recur (rand-nth valid-github-tokens)))))

(defn auth []
  {:oauth-token (pick-token)})

(def options {:all-pages true})

(defn get-all-repositories []
  (take 8 (repos/all-repos (merge (auth) options))))

(defn get-repository-statistics [repo]
  (repos/contributor-statistics (:login (:owner repo)) (:name repo) (auth)))

(defn get-user [user-name]
  (users/user user-name (auth)))
