
(ns spa-example.component.button
  (:require
    [respo.alias :refer [create-comp div span]]
    [hsl.core :refer [hsl]]))

(defn render [text on-click]
  (fn [state mutate!]
    (div {:style {:background-color (hsl 200 80 70)
                  :display "inline-block"
                  :padding "0 8px"
                  :color "white"
                  :margin "0 8px"
                  :cursor "pointer"}
          :event {:click on-click}}
      (span {:attrs {:inner-text text}}))))

(def comp-button (create-comp :button render))
