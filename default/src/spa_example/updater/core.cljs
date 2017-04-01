
(ns spa-example.updater.core)

(defn updater [store op op-data]
  (case op
    :states
      (let [[cursor next-state] op-data]
        (update store :states
          (fn [states]
            (assoc-in states (conj cursor :data) next-state))))
    :inc (update store :pointer inc)
    :dec (update store :pointer dec)
    store))
