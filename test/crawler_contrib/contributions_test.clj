(ns crawler-contrib.contributions_test
  (:use midje.sweet)
  (:use crawler-contrib.contributions))


(facts "about 'group-users-by-total-contributions'"
       (fact "it returns nil if contributions are nil or empty"
             (group-users-by-total-contributions  nil) => nil
             (group-users-by-total-contributions  []) => nil)
       (fact "it returns hash with user name and commits count"
             (group-users-by-total-contributions  [{:total 2 :author {:login "guipdutra"}}]) => {"guipdutra" 2}
             (fact "it returns an hash with user name and pull request count"
                   (group-users-by-total-contributions  [{:total 3 :author {:login "guipdutra"}},
                                                         {:total 2 :author {:login "user"}},
                                                         {:total 1 :author {:login "otheruser"}}]) =>
                   {"guipdutra" 3 "user" 2 "otheruser" 1})
             (fact "it maps when user has contributed in more than 1 project"
                   (group-users-by-total-contributions [{:total 3 :author {:login "guipdutra"}},
                                                        {:total 2 :author {:login "user"}},
                                                        {:total 1 :author {:login "user"}}]) =>
                   {"guipdutra" 3 "user" 3})
             (fact "it maps when total commit number is nil"
                   (group-users-by-total-contributions [{:total 3 :author {:login "guipdutra"}},
                                                        {:total nil :author nil}
                                                        {:total 2 :author {:login "user"}},
                                                        {:total 1 :author {:login "user"}}]) =>
                   {"guipdutra" 3 "user" 3})))

(facts "about 'group-all-contributions-by-user-for-all-repositories'"
       (fact "it returns the users with commits sorted by number of commits"
             (group-all-contributions-by-user-for-all-repositories) => '(["guipdutra" 10] ["dhh" 8] ["rodrigomaia17" 3])
             (provided
               (crawler-contrib.github-api-wrapper/get-all-repositories) => '({:name "rails" :owner {:login "rails"}}
                                                                              {:name "linux" :owner {:login "linux"}}
                                                                              {:name "ruby" :owner {:login "ruby"}})

               (crawler-contrib.github-api-wrapper/get-repository-statistics {:name "rails" :owner {:login "rails"}}) => '({:total 5 :author {:login "guipdutra"}} {:total 5 :author {:login "dhh"}})
               (crawler-contrib.github-api-wrapper/get-repository-statistics {:name "linux" :owner {:login "linux"}}) => '({:total 3 :author {:login "dhh"}} {:total 5 :author {:login "guipdutra"}})
               (crawler-contrib.github-api-wrapper/get-repository-statistics {:name "ruby" :owner {:login "ruby"}}) => '({:total 3 :author {:login "rodrigomaia17"}}))))


(facts "about 'get-greatest-contributors'"
       (fact "it returns the greatest contributors username"
             (get-greatest-contributors) => ["guipdutra" "dhh" "rodrigomaia17"]
             (provided
               (crawler-contrib.github-api-wrapper/get-all-repositories) => '({:name "rails" :owner {:login "rails"}}
                                                                              {:name "linux" :owner {:login "linux"}}
                                                                              {:name "ruby" :owner {:login "ruby"}})

               (crawler-contrib.github-api-wrapper/get-repository-statistics {:name "rails" :owner {:login "rails"}}) => '({:total 5 :author {:login "guipdutra"}} {:total 5 :author {:login "dhh"}})
               (crawler-contrib.github-api-wrapper/get-repository-statistics {:name "linux" :owner {:login "linux"}}) => '({:total 3 :author {:login "dhh"}} {:total 5 :author {:login "guipdutra"}})
               (crawler-contrib.github-api-wrapper/get-repository-statistics {:name "ruby" :owner {:login "ruby"}}) => '({:total 3 :author {:login "rodrigomaia17"}})))
       (fact "it returns only with total commits greater or equals than 5"
             (get-greatest-contributors {:number-of-commits 5} ) => ["guipdutra" "dhh"]
             (provided
               (crawler-contrib.github-api-wrapper/get-all-repositories) => '({:name "rails" :owner {:login "rails"}}
                                                                              {:name "linux" :owner {:login "linux"}}
                                                                              {:name "ruby" :owner {:login "ruby"}})

               (crawler-contrib.github-api-wrapper/get-repository-statistics {:name "rails" :owner {:login "rails"}}) => '({:total 5 :author {:login "guipdutra"}} {:total 5 :author {:login "dhh"}})
               (crawler-contrib.github-api-wrapper/get-repository-statistics {:name "linux" :owner {:login "linux"}}) => '({:total 3 :author {:login "dhh"}} {:total 5 :author {:login "guipdutra"}})
               (crawler-contrib.github-api-wrapper/get-repository-statistics {:name "ruby" :owner {:login "ruby"}}) => '({:total 3 :author {:login "rodrigomaia17"}}))))
