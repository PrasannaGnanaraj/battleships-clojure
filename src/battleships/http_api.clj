(ns battleships.http-api
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults
                                              api-defaults]]
            [clojure.pprint :as pp]
            [clojure.string :as str]))

(defn create-new-game [req]
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body    (str "Hello World" req)})

(defroutes app-routes
  (POST "/games" [] create-new-game)
  (route/not-found "Error, page not found!"))

(defonce server-instance (atom nil))

(defn start [{port :port}]
  (reset! server-instance (server/run-server (wrap-defaults #'app-routes api-defaults) {:port port})))

(defn stop []
  (when-not (nil? @server-instance)
    (@server-instance :timeout 5)
    (reset! server-instance nil)))
