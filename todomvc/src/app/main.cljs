
(ns app.main
  (:require [respo.core :refer [render! clear-cache!]]
            [app.updater.core :refer [updater]]
            [app.comp.container :refer [comp-container]]))

(defonce *store (atom {:tasks [] :states {}}))

(defonce *id-counter (atom 0))

(defn get-id! []
  (swap! *id-counter inc)
  @*id-counter)

(defn dispatch! [op op-data]
  (reset! *store (updater @*store op op-data (get-id!))))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! target (comp-container @*store) dispatch!)))

(defn -main []
  (render-app!)
  (add-watch *store :changes render-app!)
  (println "App started."))

(defn on-jsload []
  (clear-cache!)
  (render-app!)
  (println "Code updated."))

(set! (.-onload js/window) -main)
