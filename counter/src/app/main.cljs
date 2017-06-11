
(ns app.main
  (:require [respo.core :refer [render! clear-cache!]]
            [app.updater.core :refer [updater]]
            [app.comp.container :refer [comp-container]]))

(defonce ref-store (atom 0))

(defn dispatch! [op op-data]
  (reset! ref-store (updater @ref-store op op-data)))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @ref-store) target dispatch!)))

(defn -main []
  (enable-console-print!)
  (render-app!)
  (add-watch ref-store :changes render-app!)
  (println "App started."))

(defn on-jsload []
  (clear-cache!)
  (render-app!)
  (println "Code updated."))

(set! (.-onload js/window) -main)
