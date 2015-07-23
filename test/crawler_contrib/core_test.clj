(ns crawler-contrib.core-test
  (:use midje.sweet)
  (:use crawler-contrib.core))


(facts "about 'group-users-with-pull-request-count'"
       (fact "it returns nil if pulls are nil or empty"
             (group-users-with-pull-request-count nil) => nil
             (group-users-with-pull-request-count []) => nil)
       (fact "it returns hash with user name and pull request count"
             (group-users-with-pull-request-count [{:head {:user {:login "guipdutra"}}},
                                                   {:head {:user {:login "guipdutra"}}}]) => '(["guipdutra" 2])
       (fact "it returns an ordered hash with user name and pull request count"
             (group-users-with-pull-request-count [{:head {:user {:login "guipdutra"}}},
                                                   {:head {:user {:login "guipdutra"}}},
                                                   {:head {:user {:login "otheruser"}}},
                                                   {:head {:user {:login "guipdutra"}}},
                                                   {:head {:user {:login "user"}}},
                                                   {:head {:user {:login "user"}}}]) => '(["guipdutra" 3] ["user" 2] ["otheruser" 1]))))


