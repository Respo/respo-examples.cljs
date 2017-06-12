
(ns app.comp.container
  (:require-macros [respo.macros :refer [defcomp]])
  (:require
    [respo.alias :refer [create-element div button]]
    [respo.comp.text :refer [comp-text]]
    [app.actions :refer [increment decrement increment-if-odd increment-async]]))

(defcomp comp-container [store]
  (div {}
    (div {}
      (comp-text (str "Clicked: " store " times") nil))
    (button {:event {:click increment}}
      (comp-text "+" nil))
    (button {:event {:click decrement}}
      (comp-text "-" nil))
    (button {:event {:click (increment-if-odd store)}}
      (comp-text "Increment if odd" nil))
    (button {:event {:click increment-async}}
      (comp-text "Increment async" nil))))
