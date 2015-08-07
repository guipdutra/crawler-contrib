(ns crawler-contrib.github-api-wrapper
  (require [tentacles.repos :as repos]
           [tentacles.users :as users]
           [tentacles.pulls :as pulls]))

(def auth {:oauth-token (System/getenv "GITHUB_ACCESS_TOKEN")})

(def options {:all-pages true})

(defn get-all-repositories []
  (take 200 (repos/all-repos (merge auth options))))

(defn get-repository-statistics [repo]
  (repos/contributor-statistics (:login (:owner repo)) (:name repo) auth))

(defn get-user [user-name]
  (users/user user-name auth))
