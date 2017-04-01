
(ns spa-example.component.todo
  (:require
    [clojure.string :as string]
    [spa-example.actions :refer [delete-todo toggle-todo input-keyup]]
    [respo.comp.text :refer [comp-text]]
    [respo.alias :refer [create-comp create-element div input button]]))

(defn li [props & children]
  (create-element :li props children))

(defn label [props & children]
  (create-element :label props children))

(def initial-state {:text "" :editing false})

(defn render [states todo]
  (fn [cursor]
    (let [state (or (:data states) initial-state)]
        (li {:attrs {:class-name
            (->> ["toggle"
                  (if (:done todo) "completed")
                  (if (:editing state) "editing")]
              (filter some?)
              (string/join " "))}}
          (div {:attrs {:class-name "view"}}
            (input {:attrs {:class-name "toggle" :type "checkbox"
                            :checked (:done todo)}
                    :event {:change (toggle-todo (:id todo))}})
            (label {:event {:dblclick (fn [e dispatch!] (dispatch! :states [cursor (assoc state :editing true)]))}}
              (comp-text (:text todo) nil))
            (button {:attrs {:class-name "destroy"}
                     :event {:click (delete-todo (:id todo))}}))
          (if (:editing state)
            (input {:attrs {:class-name "edit" :autofocus true
                            :value (:text todo)}
                    :event {:input (fn [e dispatch!] (dispatch! :states [cursor (assoc state :text (:value e))]))
                            :keyup (input-keyup (:id todo) cursor state)}}))))))

(def comp-todo (create-comp :todo render))
