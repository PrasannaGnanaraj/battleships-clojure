(ns battleships.core
  (:require [battleships.http-api :as http-api])
  (:gen-class))

(defonce server nil)

(defn -main [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (http-api/start {:port port})
    (println (str "Running webserver at http://127.0.0.1:" port))))
