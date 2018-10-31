
(ns app.comp.button
  (:require
    [respo.core :refer [defcomp <> div span]]
    [hsl.core :refer [hsl]]))

(defcomp comp-button [text on-click]
  (div {:style {:background-color (hsl 200 80 70)
                :display "inline-block"
                :padding "0 8px"
                :color "white"
                :margin "0 8px"
                :cursor "pointer"}
        :on-click on-click}
    (<> text)))
