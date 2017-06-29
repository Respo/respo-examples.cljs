
(ns app.comp.container
  (:require-macros [respo.macros :refer [defcomp <> div button span]])
  (:require
    [respo.core :refer [create-comp]]
    [app.actions :refer [increment decrement increment-if-odd increment-async]]))

(defn recent5 [history]
  (if (> (count history) 5)
    (subvec history (- (count history) 5))
    history))

(defcomp comp-container [store]
  (div {}
    (div {}
      (<> span (str "Clicked: " (:count store) " times") nil))
    (button {:on {:click increment}}
      (<> span "+" nil))
    (button {:on {:click decrement}}
      (<> span "-" nil))
    (button {:on {:click (increment-if-odd (:count store))}}
      (<> span "Increment if odd" nil))
    (button {:on {:click increment-async}}
      (<> span "Increment async" nil))
    (div {}
      (<> span (str "Recent History: " (recent5 (:history store))) nil))))
