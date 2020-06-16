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
    (let [uuid-length 36
          url  "http://localhost:7172/games"
          params {"opponent_address" "http://example.com:1234"}
          request-new-game (fn [] (http/post url {:form-params params}))
          response (request-new-game)]
      (is (= (:status response) 201))
      (is (= (count (:body response)) uuid-length))
      (is (not (= (:body response) (:body (request-new-game)))))))

  (testing "Making moves"
    (let [url  "http://localhost:7172"
          new-game-params {:form-params {"opponent_address" "http://example.com:1234"}}
          request-new-game (fn [] (http/post (str url "/games") new-game-params))
          game-id (:body (request-new-game))
          make-move (fn [x] (http/post (str url "/games/" game-id) {:form-params {"location" x} }))
          move-result (make-move "a1")]
      (is (= (:status move-result) 200))
      (is (= (:body move-result) "miss")))))
