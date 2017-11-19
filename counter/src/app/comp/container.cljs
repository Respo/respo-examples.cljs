
(ns app.comp.container
  (:require
    [respo.macros :refer [defcomp <> div button span]]
    [app.actions :refer [increment decrement increment-if-odd increment-async]]))

(defcomp comp-container (store)
  (div {}
    (<> div (str "Clicked: " store " times") nil)
    (button {:on {:click increment}}
      (<> span "+" nil))
    (button {:on {:click decrement}}
      (<> span "-" nil))
    (button {:on {:click (increment-if-odd store)}}
      (<> span "Increment if odd" nil))
    (button {:on {:click increment-async}}
      (<> span "Increment async" nil))))
