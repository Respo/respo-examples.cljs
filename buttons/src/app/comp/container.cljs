
(ns app.comp.container
  (:require
    [respo.macros :refer [defcomp list-> cursor-> <> div hr span]]
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
        (<> "Demo of component with mutate")
        (comp-box states 5))
      (hr {})
      (<> "Demo of list of them")
      (list-> :div {}
        (->> (range 10)
          (map-indexed (fn [index n]
            [index (cursor-> n comp-box states n)]))))
      (hr {})
      (div {}
        (<> "Demo of dispatch:")
        (comp-button "inc" handle-inc)
        (comp-button "dec" (handle-dec-with-log state store))
        (<> (str store))))))
