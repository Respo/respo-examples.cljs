
(ns spa-example.component.todo
  (:require
    [clojure.string :as string]
    [spa-example.actions :refer [delete-todo toggle-todo done-edit input-keyup]]
    [respo.comp.text :refer [comp-text]]
    [respo.alias :refer [create-comp create-element div input button]]))

(defn li [props & children]
  (create-element :li props children))

(defn label [props & children]
  (create-element :label props children))

(defn init-state [todo]
  {:text "" :editing false})

(defn update-state [state op op-data]
  (assoc state op op-data))

(defn render [todo index]
  (fn [state mutate!]
    (li {:attrs {:class-name
        (->> ["toggle"
              (if (:done todo) "completed")
              (if (:editing state) "editing")]
          (filter some?)
          (string/join " "))}}
      (div {:attrs {:class-name "view"}}
        (input {:attrs {:class-name "toggle" :type "checkbox"
                        :checked (:done todo)}
                :event {:change (toggle-todo index)}})
        (label {:event {:dblclick (fn [e dispatch!] (mutate! :editing true))}}
          (comp-text (:text todo) nil))
        (button {:attrs {:class-name "destroy"}
                 :event {:click (delete-todo index)}}))
      (if (:editing state)
        (input {:attrs {:class-name "edit" :autofocus true
                        :value (:text todo)}
                :event {:input (fn [e dispatch!] (mutate! :text (:value e)))
                        ; :blur (done-edit index (:text state) mutate!) ; duplicated event
                        :keyup (input-keyup index (:text state) mutate!)}})))))

(def comp-todo (create-comp :todo init-state update-state render))
