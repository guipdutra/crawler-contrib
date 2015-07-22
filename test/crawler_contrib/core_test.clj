(ns crawler-contrib.core-test
  (:use midje.sweet)
  (:use crawler-contrib.core))


(facts "about 'group-users-with-pull-request-count'"
       (fact "it returns nil if repos are nil or empty"
             (group-users-with-pull-request-count nil) => nil
             (group-users-with-pull-request-count []) => nil))


