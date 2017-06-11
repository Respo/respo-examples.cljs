
(ns app.comp.button
  (:require
    [respo.alias :refer [create-comp div span]]
    [respo.comp.text :refer [comp-text]]
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
      (comp-text text))))

(def comp-button (create-comp :button render))
