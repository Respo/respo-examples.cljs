
(ns app.comp.todo
  (:require-macros [respo.macros :refer [defcomp <> div input button span li]])
  (:require
    [clojure.string :as string]
    [app.actions :refer [delete-todo toggle-todo input-keyup]]
    [respo.core :refer [create-comp create-element]]))

(def initial-state {:text "" :editing false})

(defcomp comp-todo [states todo]
  (let [state (or (:data states) initial-state)]
      (li {:class-name
            (->> ["toggle"
                  (if (:done todo) "completed")
                  (if (:editing state) "editing")]
              (filter some?)
              (string/join " "))}
        (div {:class-name "view"}
          (input {:class-name "toggle" :type "checkbox"
                  :checked (:done todo)
                  :on {:change (toggle-todo (:id todo))}})
          (create-element :label {:on {:dblclick (fn [e dispatch!] (dispatch! :states [*cursor* (assoc state :editing true)]))}}
            (<> span (:text todo) nil))
          (button {:class-name "destroy"
                   :on {:click (delete-todo (:id todo))}}))
        (if (:editing state)
          (input {:class-name "edit" :autofocus true
                  :value (:text todo)
                  :on {:input (fn [e dispatch!] (dispatch! :states [*cursor* (assoc state :text (:value e))]))
                          :keyup (input-keyup (:id todo) *cursor* state)}})))))
