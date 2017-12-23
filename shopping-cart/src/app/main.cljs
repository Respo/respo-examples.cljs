
(ns app.main
  (:require [respo.core :refer [render! clear-cache!]]
            [app.updater.core :refer [updater]]
            [app.comp.container :refer [comp-container]]
            [app.actions :refer [get-all-products]]))

(defonce *store (atom {:all [] :added [] :last-checkout nil}))

(defn dispatch! [op op-data]
  (reset! *store (updater @*store op op-data)))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! target (comp-container @*store) #(dispatch! %1 %2))))

(defn main! []
  (render-app!)
  (add-watch *store :changes render-app!)
  (get-all-products dispatch!)
  (println "App started."))

(defn reload! []
  (clear-cache!)
  (render-app!)
  (println "Code updated."))

(set! (.-onload js/window) main!)
