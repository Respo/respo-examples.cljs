
(ns spa-example.component.container
  (:require
    [respo.alias :refer [create-comp create-element div span]]
    [spa-example.component.button :refer [comp-button]]
    [spa-example.component.box :refer [comp-box]]))

(defn hr [props & children]
  (create-element :hr props children))

(defn handle-click [simple-event dispatch]
  (dispatch nil nil))

(defn render [store]
  (fn [state mutate]
    (div {}
      (div {}
        (->> (range 10)
          (map-indexed (fn [index n]
            [index (comp-box n)]))))
      (hr {})
      (comp-button "inc" handle-click)
      (span {:attrs {:inner-text (str store)}})
      (div {}
        (comp-box 5)))))

(def comp-container (create-comp :container render))
