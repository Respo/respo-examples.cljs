
(ns spa-example.updater.core)

(defn updater [store op op-data]
  (case op
    :add-todo
    (conj store {:text op-data :done false})
    :delete-todo
    (cond
      (= (count store) 0) store
      (= op-data 0) (subvec store 1)
      (= op-data (dec (count store))) (into [] (butlast store))
      :else (into [] (concat (subvec store 0 op-data) (subvec store (inc op-data)))))
    :toggle-todo
    (update-in store [op-data :done] not)
    :edit-todo
    (let [[pos text] op-data]
      (update store pos #(assoc %1 :text text)))
    :toggle-all
    (->> store (mapv #(update %1 :done not)))
    :clear-completed
    (->> store (filterv #(not (:done %1))))
    store))
