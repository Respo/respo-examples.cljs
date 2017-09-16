
(ns app.comp.container
  (:require-macros [respo.macros :refer [defcomp cursor-> <> div hr span]])
  (:require
    [respo.core :refer [create-comp]]))

(defn on-click [e d! m!]
  (js/alert "Clicked!"))

(defcomp comp-piece []
  [:div {:on {:click on-click}}
    [:span "Click to alert!"]])

(defcomp comp-container [store]
  (let [states (:states store)
        state (:data states)]
    [:div.app-container
      [:header.app-header "This is header"]
      ; need to use parantheses for components
      (comp-piece)
      "Hiccup DSL demo"]))
