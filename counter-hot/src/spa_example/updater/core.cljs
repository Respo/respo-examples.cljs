
(ns spa-example.updater.core)

(defn updater [store op op-data]
  (case op
    :inc (-> store
             (update :count inc)
             (update :history #(conj %1 "increment")))
    :dec (-> store
             (update :count dec)
             (update :history #(conj %1 "decrement")))
    store))
