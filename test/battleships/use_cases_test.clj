(ns battleships.use-cases-test
  (:require [clj-http.client :as http]
            [clojure.test :refer :all]
            [battleships.http-api :as api]))

(defn with-server-fixture [f]
  (api/start {:port 7172})
  (f)
  (api/stop))

(use-fixtures :once with-server-fixture)

(deftest start-new-game-test
  (testing "POST /games creates new game"
    (let [response (http/post "http://localhost:7172/games"
                              {:form-params {"opponent_address" "http://example.com:1234"}})]
      (is (= (:status response) 200)))))
