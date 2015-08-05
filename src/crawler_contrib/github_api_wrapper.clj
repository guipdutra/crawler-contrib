(ns crawler-contrib.github-api-wrapper
  (require [tentacles.repos :as repos]
           [tentacles.pulls :as pulls]))

(def auth {:oauth-token (System/getenv "GITHUB_ACCESS_TOKEN")})

(def options {:all-pages true})

(defn get-all-repositories []
  (take 300 (repos/all-repos (merge auth options))))

(defn get-repository-statistics [repo]
  (repos/contributor-statistics (:login (:owner repo)) (:name repo) auth))
