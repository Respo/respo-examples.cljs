
(ns app.comp.container
  (:require
    [respo.macros :refer [defcomp <> div button span]]
    [app.actions :refer [increment decrement increment-if-odd increment-async]]))

(defn recent5 [history]
  (if (> (count history) 5)
    (subvec history (- (count history) 5))
    history))

(defcomp comp-container [store]
  (div {}
    (div {}
      (<> (str "Clicked: " (:count store) " times")))
    (button {:on {:click increment}}
      (<> "+"))
    (button {:on {:click decrement}}
      (<> "-"))
    (button {:on {:click (increment-if-odd (:count store))}}
      (<> "Increment if odd"))
    (button {:on {:click increment-async}}
      (<> "Increment async"))
    (div {}
      (<> (str "Recent History: " (recent5 (:history store)))))))
