
(ns app.main
  (:require [respo.core :refer [render! clear-cache!]]
            [app.updater.core :refer [updater]]
            [app.comp.container :refer [comp-container]]))

(defonce *store (atom {:point 0 :states {}}))

(defn dispatch! [op op-data]
  (println "dispatch:" op op-data)
  (reset! *store (updater @*store op op-data)))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! target (comp-container @*store) #(dispatch! %1 %2))))

(defn main! []
  (enable-console-print!)
  (render-app!)
  (add-watch *store :changes render-app!)
  (println "App started."))

(defn reload! []
  (clear-cache!)
  (render-app!)
  (println "Code updated."))
