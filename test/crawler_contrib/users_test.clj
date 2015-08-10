(ns crawler-contrib.users_test
  (:use midje.sweet)
  (:use crawler-contrib.users))

(facts "about 'filter-by-brazilians'"
       (fact "returns only the brazilians one"
             (filter-by-brazilians ["guipdutra"
                                    "rodrigomaia17"
                                    "wycats"
                                    "mviegas"
                                    "gregoriomelo"
                                    "ffrancieli"
                                    "cv"]) => ["guipdutra" "rodrigomaia17" "ffrancieli" "cv"]
             (provided
               (crawler-contrib.github-api-wrapper/get-user "guipdutra") => {:location "Porto Alegre, Brazil" :login "guipdutra"}
               (crawler-contrib.github-api-wrapper/get-user "rodrigomaia17") => {:location "Belo Horizonte, Brazil" :login "rodrigomaia17"}
               (crawler-contrib.github-api-wrapper/get-user "mviegas") => {:location nil :login "mviegas"}
               (crawler-contrib.github-api-wrapper/get-user "ffrancieli") => {:location "Belo Horizonte" :login "ffrancieli"}
               (crawler-contrib.github-api-wrapper/get-user "gregoriomelo") => {:location "" :login "gregoriomelo"}
               (crawler-contrib.github-api-wrapper/get-user "cv") => {:location "São Paulo" :login "cv"}
               (crawler-contrib.github-api-wrapper/get-user "wycats") => {:location "Chicado, USA" :login "wycats"})))
