
(ns app.comp.button
  (:require-macros [respo.macros :refer [defcomp]])
  (:require
    [respo.alias :refer [div]]
    [respo.comp.text :refer [comp-text]]
    [hsl.core :refer [hsl]]))

(defcomp comp-button [text on-click]
  (div {:style {:background-color (hsl 200 80 70)
                :display "inline-block"
                :padding "0 8px"
                :color "white"
                :margin "0 8px"
                :cursor "pointer"}
        :event {:click on-click}}
    (comp-text text)))
