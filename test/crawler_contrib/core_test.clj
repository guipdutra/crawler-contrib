(ns crawler-contrib.core-test
  (:use midje.sweet)
  (:use crawler-contrib.core))

(fact "about 'format-output-with-link-and-location'"
      (format-output-with-link-and-location [{ :name "guipdutra" :location "Belo Horizonte, Brazil" }
                                             { :name "cv" :location "Brazil" }
                                             { :name "gregoriomelo" :location "Porto Alegre" }]) =>
      ["https://github.com/guipdutra - Belo Horizonte, Brazil"
       "https://github.com/cv - Brazil"
       "https://github.com/gregoriomelo - Porto Alegre"])
