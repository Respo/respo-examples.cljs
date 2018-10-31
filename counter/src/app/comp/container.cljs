
(ns app.comp.container
  (:require
    [respo.core :refer [defcomp <> div button span]]
    [app.actions :refer [increment decrement increment-if-odd increment-async]]))

(defcomp comp-container (store)
  (div {}
    (<> div (str "Clicked: " store " times") nil)
    (button {:on-click increment}
      (<> "+"))
    (button {:on-click decrement}
      (<> "-"))
    (button {:on-click (increment-if-odd store)}
      (<> "Increment if odd"))
    (button {:on-click increment-async}
      (<> "Increment async"))))
