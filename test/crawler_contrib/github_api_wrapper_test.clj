(ns crawler-contrib.github-api-wrapper_test
  (:use midje.sweet)
  (:use crawler-contrib.github-api-wrapper))

(facts "about 'get-all-repositories'"
       (fact "it calls github API for all repositories with all-pages true and auth"
             (get-all-repositories) => [{}]
             (provided
               (tentacles.repos/all-repos anything) => [{}])))

(facts "about 'get-repository-statistics'"
       (fact "it calls github API for repository statistics"
             (get-repository-statistics {:owner {:login "user"} :name "repo_name"}) => [{}]
             (provided
               (tentacles.repos/contributor-statistics
                 "user"
                 "repo_name"
                 anything) => [{}])))

 (facts "about 'get-user'"
        (fact "it calls github API for user informations"
              (get-user "user") => [{}]
              (provided
               (tentacles.users/user "user" anything) => [{}])))

(facts "about 'pick-token'"
       (fact "it picks the available token"
             (pick-token) => (System/getenv "GITHUB_ACCESS_TOKEN_1")
             (provided
               (tentacles.core/rate-limit {:oauth-token (System/getenv "GITHUB_ACCESS_TOKEN_1")}) => {:resources {:core {:remaining 10}}}
               (tentacles.core/rate-limit {:oauth-token (System/getenv "GITHUB_ACCESS_TOKEN_2")}) => {:resources {:core {:remaining 0}}} :times some?
               (tentacles.core/rate-limit {:oauth-token (System/getenv "GITHUB_ACCESS_TOKEN_3")}) => {:resources {:core {:remaining 0}}} :times some?
               (tentacles.core/rate-limit {:oauth-token (System/getenv "GITHUB_ACCESS_TOKEN_4")}) => {:resources {:core {:remaining 0}}} :times some?)))
