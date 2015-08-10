(ns crawler-contrib.github-api-wrapper
  (require [tentacles.repos :as repos]
           [tentacles.users :as users]
           [tentacles.core :as core]
           [tentacles.pulls :as pulls]))

(def auth {:oauth-token (System/getenv "GITHUB_ACCESS_TOKEN")})

(def options {:all-pages true})

(defn make-request [method-to-call]
  (if (> (:remaining (:core (:resources (core/rate-limit auth)))) 0)
    (method-to-call)
    (make-request method-to-call)))

(defn get-all-repositories []
  (take 2 (make-request (fn [] (repos/all-repos (merge auth options))))))

(defn get-repository-statistics [repo]
  (make-request (fn [] (repos/contributor-statistics (:login (:owner repo)) (:name repo) auth))))

(defn get-user [user-name]
  (make-request (fn [] (users/user user-name auth))))
