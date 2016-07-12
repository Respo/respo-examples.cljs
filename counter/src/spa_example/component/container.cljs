
(ns spa-example.component.container
  (:require
    [respo.alias :refer [create-comp create-element div button]]
    [respo.component.text :refer [comp-text]]
    [spa-example.actions :refer [increment decrement increment-if-odd increment-async]]))

(defn render [store]
  (fn [state mutate!]
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
        (comp-text "Increment async" nil)))))

(def comp-container (create-comp :container render))
