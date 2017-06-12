
(ns app.comp.container
  (:require-macros [respo.macros :refer [defcomp]])
  (:require
    [respo.alias :refer [create-element div hr]]
    [respo.comp.text :refer [comp-text]]
    [respo.cursor :refer [with-cursor]]
    [app.comp.button :refer [comp-button]]
    [app.comp.box :refer [comp-box]]))

(defn handle-inc [simple-event dispatch!]
  (dispatch! :inc 1))

(defn handle-dec-with-log [state store]
  (fn [simple-event dispatch!]
    (println "some info:" state store)
    (dispatch! :dec 1)))

(defcomp comp-container [store]
  (let [states (:states store)
        state (:data states)]
    (div {}
      (div {}
        (comp-text "Demo of component with mutate")
        (comp-box states 5))
      (hr {})
      (comp-text "Demo of list of them")
      (div {}
        (->> (range 10)
          (map-indexed (fn [index n]
            [index (with-cursor n (comp-box (get states n) n))]))))
      (hr {})
      (div {}
        (comp-text "Demo of dispatch:")
        (comp-button "inc" handle-inc)
        (comp-button "dec" (handle-dec-with-log state store))
        (comp-text (str store))))))
