
(ns app.comp.box
  (:require-macros [respo.macros :refer [defcomp]])
  (:require
    [respo.alias :refer [div]]
    [respo.comp.text :refer [comp-text]]
    [app.comp.button :refer [comp-button]]))

(def initial-state 0)

(defn handle-click [cursor next-state]
  (fn [e dispatch!]
    (dispatch! :states [cursor next-state])))

(defcomp comp-box [states n]
  (let [state (or (:data states) initial-state)]
    (div {}
      (comp-text (str n ". ") nil)
      (comp-button "inc" (handle-click cursor (+ state n)))
      (comp-text state nil))))
