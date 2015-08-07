(ns crawler-contrib.users_test
  (:use midje.sweet)
  (:use crawler-contrib.users))

(facts "about 'filter-by-brazilians'"
       (fact "returns only the brazilians one"
             (filter-by-brazilians ["guipdutra" "rodrigomaia17" "wycats" "mviegas" "gregoriomelo"]) => ["guipdutra" "rodrigomaia17"]
             (provided
               (crawler-contrib.github-api-wrapper/get-user "guipdutra") => {:location "Porto Alegre, Brazil"}
               (crawler-contrib.github-api-wrapper/get-user "rodrigomaia17") => {:location "Belo Horizonte, Brazil"}
               (crawler-contrib.github-api-wrapper/get-user "mviegas") => {:location nil}
               (crawler-contrib.github-api-wrapper/get-user "gregoriomelo") => {:location ""}
               (crawler-contrib.github-api-wrapper/get-user "wycats") => {:location "Chicado, USA"})))
