(ns crawler-contrib.core-test
  (:use midje.sweet)
  (:use crawler-contrib.core))


(facts "about 'group-users-with-pull-request-count'"
       (fact "it returns nil if pulls are nil or empty"
             (group-users-by-total-contributions  nil) => nil
             (group-users-by-total-contributions  []) => nil)
       (fact "it returns hash with user name and pull request count"
             (group-users-by-total-contributions  [{:total 2 :author {:login "guipdutra"}}]) => '(["guipdutra" 2])
             (fact "it returns an ordered hash with user name and pull request count"
                   (group-users-by-total-contributions  [{:total 3 :author {:login "guipdutra"}},
                                                         {:total 2 :author {:login "user"}},
                                                         {:total 1 :author {:login "otheruser"}}]) => '(["guipdutra" 3] ["user" 2] ["otheruser" 1]))))


