
(ns spa-example.component.box
  (:require
    [respo.alias :refer [create-comp div span]]
    [spa-example.component.button :refer [comp-button]]))

(defn init-state [] 0)

(defn update-state [state step]
  (+ step state))

(defn handle-click [mutate-parent! step]
  (fn [simple-event dispatch! mutate!]
    (mutate-parent! step)))

(defn text [x]
  (span {:attrs {:inner-text (str x)}}))

(defn render [n]
  (fn [state mutate!]
    (div {}
      (text (str n ". "))
      (comp-button "inc" (handle-click mutate! n))
      (text state))))

(def comp-box (create-comp :box init-state update-state render))
