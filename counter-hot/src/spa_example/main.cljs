
(ns spa-example.main
  (:require [respo.core :refer [render! clear-cache!]]
            [spa-example.updater.core :refer [updater]]
            [spa-example.comp.container :refer [comp-container]]))

(defonce store-ref (atom {:count 0 :history []}))

(defn dispatch! [op op-data]
  (reset! store-ref (updater @store-ref op op-data)))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch!)))

(defn -main []
  (enable-console-print!)
  (render-app!)
  (add-watch store-ref :changes render-app!)
  (println "App started!"))

(defn on-jsload []
  (clear-cache!)
  (render-app!)
  (println "Code updated."))

(set! (.-onload js/window) -main)
