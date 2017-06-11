
(ns app.updater.core)

(defn updater [store op op-data]
  (case op
    :inc (inc store)
    :dec (dec store)
    store))
