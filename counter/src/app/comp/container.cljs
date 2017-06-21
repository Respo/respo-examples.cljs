
(ns app.comp.container
  (:require-macros [respo.macros :refer [defcomp <> div button span]])
  (:require
    [respo.core :refer [create-comp]]
    [app.actions :refer [increment decrement increment-if-odd increment-async]]))

(defcomp comp-container (store)
  (div {}
    (<> div (str "Clicked: " store " times") nil)
    (button {:event {:click increment}}
      (<> span "+" nil))
    (button {:event {:click decrement}}
      (<> span "-" nil))
    (button {:event {:click (increment-if-odd store)}}
      (<> span "Increment if odd" nil))
    (button {:event {:click increment-async}}
      (<> span "Increment async" nil))))
