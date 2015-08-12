(defproject crawler-contrib "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:dependencies [[midje "1.7.0"]]
                   :plugins [[lein-midje "3.1.3"]]}}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [tentacles "0.4.0"]]
  :main crawler-contrib.core)
