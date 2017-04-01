
(ns spa-example.comp.container
  (:require
    [respo.alias :refer [create-comp create-element div button]]
    [respo.comp.text :refer [comp-text]]
    [spa-example.actions :refer [increment decrement increment-if-odd increment-async]]))

(defn recent5 [history]
  (if (> (count history) 5)
    (subvec history (- (count history) 5))
    history))

(def comp-container
  (create-comp :container
    (fn [store]
      (fn [cursor]
        (div {}
          (div {}
            (comp-text (str "Clicked: " (:count store) " times") nil))
          (button {:event {:click increment}}
            (comp-text "+" nil))
          (button {:event {:click decrement}}
            (comp-text "-" nil))
          (button {:event {:click (increment-if-odd (:count store))}}
            (comp-text "Increment if odd" nil))
          (button {:event {:click increment-async}}
            (comp-text "Increment async" nil))
          (div {}
            (comp-text (str "Recent History: " (recent5 (:history store))) nil)))))))
