
(ns app.updater.core)

(defn updater [store op op-data]
  (case op
    :receive-products (assoc store :all op-data)
    :add-to-cart
    (let [existed? (some #(= (:id %1) op-data) (:added store))
          new-store (-> store
                      (update :all (fn [products]
                        (->> products (mapv (fn [product]
                          (if (= (:id product) op-data)
                            (update product :inventory dec)
                            product)))))))]
      (if existed?
        (-> new-store
          (update :added (fn [added]
            (mapv (fn [record]
              (if (= (:id record) op-data)
                (update record :quantity inc)
                record))
            added))))
        (-> new-store
          (update :added #(conj %1 {:id op-data :quantity 1})))))
    :checkout-request (-> store
                        (assoc :added [])
                        (assoc :last-checkout nil))
    :checkout-success (-> store
                        (assoc :last-checkout :success))
    :checkout-failure (-> store
                        (assoc :added op-data)
                        (assoc :last-checkout :failed))
    store))
