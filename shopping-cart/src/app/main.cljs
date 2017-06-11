
(ns app.main
  (:require [respo.core :refer [render! clear-cache!]]
            [app.updater.core :refer [updater]]
            [app.comp.container :refer [comp-container]]
            [app.actions :refer [get-all-products]]))

(defonce store-ref (atom {:all [] :added [] :last-checkout nil}))

(defn dispatch! [op op-data]
  (reset! store-ref (updater @store-ref op op-data)))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch!)))

(defn -main []
  (enable-console-print!)
  (render-app!)
  (add-watch store-ref :changes render-app!)
  (get-all-products dispatch!)
  (println "App started."))

(defn on-jsload []
  (clear-cache!)
  (render-app!)
  (println "Code updated."))

(set! (.-onload js/window) -main)
