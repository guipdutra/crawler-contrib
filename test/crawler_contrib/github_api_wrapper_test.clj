(ns crawler-contrib.github-api-wrapper_test
  (:use midje.sweet)
  (:use crawler-contrib.github-api-wrapper))

(facts "about 'get-all-repositories'"
       (fact "it calls github API for all repositories with all-pages true and auth"
             (get-all-repositories) => [{}]
             (provided
               (tentacles.repos/all-repos
                 {:oauth-token (System/getenv "GITHUB_ACCESS_TOKEN")
                  :all-pages true}) => [{}])))

(facts "about 'get-repository-statistics'"
       (fact "it calls github API for repository statistics"
             (get-repository-statistics {:owner {:login "user"} :name "repo_name"}) => [{}]
             (provided
               (tentacles.repos/contributor-statistics
                 "user"
                 "repo_name"
                 {:oauth-token (System/getenv "GITHUB_ACCESS_TOKEN")}) => [{}])))

(facts "about 'get-user'"
       (fact "it calls github API for user informations"
             (get-user "user") => [{}]
             (provided
               (tentacles.users/user "user"
                                     {:oauth-token (System/getenv "GITHUB_ACCESS_TOKEN")}) => [{}])))
