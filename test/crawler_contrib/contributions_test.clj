(ns crawler-contrib.contributions_test
  (:use midje.sweet)
  (:use crawler-contrib.contributions))


(facts "about 'group-users-by-total-contributions'"
       (fact "it returns nil if pulls are nil or empty"
             (group-users-by-total-contributions  nil) => nil
             (group-users-by-total-contributions  []) => nil)
       (fact "it returns hash with user name and pull request count"
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
                                                        {:total nil :author "user"}
                                                        {:total 2 :author {:login "user"}},
                                                        {:total 1 :author {:login "user"}}]) =>
                   {"guipdutra" 3 "user" 3}) ))
