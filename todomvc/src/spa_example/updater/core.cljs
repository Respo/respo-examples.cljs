
(ns spa-example.updater.core)

(defn updater [store op op-data op-id]
  (case op
    :states
      (update store :states
        (fn [states]
          (let [[cursor new-state] op-data]
            (assoc-in states (conj cursor :data) new-state))))
    :add-todo
      (update store :tasks
        (fn [tasks]
          (conj tasks {:text op-data :done false :id op-id})))
    :delete-todo
      (update store :tasks
        (fn [tasks]
          (->> tasks (filterv (fn [item]
            (not= op-data (:id item)))))))
    :toggle-todo
      (update store :tasks
        (fn [tasks]
          (->> tasks (mapv (fn [item]
            (if (= (:id item) op-data)
              (update item :done not)
              item))))))
    :edit-todo
      (update store :tasks
        (fn [tasks]
          (let [[id text] op-data]
            (->> tasks (mapv (fn [item]
              (if (= id (:id item))
                (assoc item :text text)
                item)))))))
    :toggle-all
      (update store :tasks
        (fn [tasks]
          (->> tasks (mapv #(update %1 :done not)))))
    :clear-completed
      (update store :tasks
        (fn [tasks]
          (->> tasks (filterv #(not (:done %1))))))
    store))
