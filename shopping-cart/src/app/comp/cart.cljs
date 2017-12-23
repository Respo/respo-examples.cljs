
(ns app.comp.cart
  (:require
    [respo.macros :refer [defcomp list-> <> div button h2 p span ul li]]
    [respo.comp.inspect :refer [comp-inspect]]
    [respo.core :refer [create-element]]
    [app.actions :refer [checkout]]))

(defcomp comp-cart [store]
  (let [products
        (->> (:added store)
          (mapv (fn [item]
            (let [product (first (filter #(= (:id %1) (:id item))
                            (:all store)))]
              (assoc product :quantity (:quantity item))))))
        total (apply + (map #(* (:price %1) (:quantity %1)) products))
        checkout-status (:last-checkout store)]
    (div {:class-name "cart"}
      (h2 {} (<> "Your cart"))
      (if (empty? products)
        (p {}
          (create-element :i {}
            (<> "Please add some products to cart"))))
      (list-> :ul {}
        (->> products (map (fn [product]
            [(:id product) (li {}
              (<> (str (:title product) " - " (:price product) " x " (:quantity product))))]))))
      (p {}
        (<> (str "Total: " total)))
      (p {}
        (button {:disabled (empty? products)
                 :on-click (checkout products)}
          (<> "Checkout")))
      (if (some? checkout-status)
        (p {}
          (<> (str "Checkout " checkout-status)))))))
