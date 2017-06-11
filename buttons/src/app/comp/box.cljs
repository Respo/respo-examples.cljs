
(ns app.comp.box
  (:require
    [respo.alias :refer [create-comp div span]]
    [respo.comp.text :refer [comp-text]]
    [app.comp.button :refer [comp-button]]))

(def initial-state 0)

(defn handle-click [cursor next-state]
  (fn [e dispatch!]
    (dispatch! :states [cursor next-state])))

(defn render [states n]
  (fn [cursor]
    (let [state (or (:data states) initial-state)]
      (div {}
        (comp-text (str n ". ") nil)
        (comp-button "inc" (handle-click cursor (+ state n)))
        (comp-text state nil)))))

(def comp-box (create-comp :box render))
