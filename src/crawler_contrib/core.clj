(ns crawler-contrib.core
  (:require [crawler-contrib.contributions :refer [get-greatest-contributors]]
            [crawler-contrib.users :refer [filter-by-brazilians]]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.util.response :refer [response]])
  (:use ring.adapter.jetty)
  (require [clj-http.client :as client]))

(def github-url
  "https://github.com/")

(def master-address
  "http://10.74.20.26:3001")

(def concat-github-link-and-location
  (fn [user]
    (str github-url (:name user) " - " (:location user))))

(defn format-output-with-link-and-location [users]
  (map concat-github-link-and-location users))

(def process-repositories
  (fn [repositories]
    (future
      (do
        (let [users (format-output-with-link-and-location
                      (filter-by-brazilians
                        (get-greatest-contributors
                          repositories {:number-of-commits 5})))]
            (client/post master-address
                         {:body (client/json-encode users)
                          :content-type :json
                          :accept :json }))
        (println "Processed.")))))


(defn handler [request]
  (prn (str "Received " (count (:body request)) " repositories to process"))
  (process-repositories (:body request))
  (response "Requested sent."))

(def app
  (wrap-json-body handler {:keywords? true :bigdecimals? true}))

(defn -main []
  (run-jetty app {:port 3000}))
