
(ns spa-example.core
  (:require [respo.core :refer [render! clear-cache!]]
            [spa-example.updater.core :refer [updater]]
            [spa-example.component.container :refer [comp-container]]))

(defonce store-ref (atom 0))

(defonce states-ref (atom {}))

(defn dispatch! [op op-data]
  (reset! store-ref (updater @store-ref op op-data)))

(defn render-app []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch! states-ref)))

(defn -main []
  (enable-console-print!)
  (render-app)
  (add-watch store-ref :changes render-app)
  (add-watch states-ref :changes render-app)
  (println "app started!"))

(defn on-jsload []
  (clear-cache!)
  (render-app)
  (println "code updated."))

(set! (.-onload js/window) -main)
