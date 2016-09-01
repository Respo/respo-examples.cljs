
(ns spa-example.updater.core)

(defn updater [store op op-data op-id]
  (case op
    :add-todo
    (conj store {:text op-data :done false :id op-id})
    :delete-todo
    (->> store (filterv (fn [item]
      (not= op-data (:id item)))))
    :toggle-todo
    (->> store (mapv (fn [item]
      (if (= (:id item) op-data)
        (update item :done not)
        item))))
    :edit-todo
    (let [[id text] op-data]
      (->> store (mapv (fn [item]
        (if (= id (:id item))
          (assoc item :text text)
          item)))))
    :toggle-all
    (->> store (mapv #(update %1 :done not)))
    :clear-completed
    (->> store (filterv #(not (:done %1))))
    store))
