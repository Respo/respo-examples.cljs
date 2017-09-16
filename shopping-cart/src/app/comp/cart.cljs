
(ns app.comp.cart
  (:require-macros [respo.macros :refer [defcomp <> div button h2 p span ul li]])
  (:require
    [respo.core :refer [create-comp create-element]]
    [respo.comp.inspect :refer [comp-inspect]]
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
      (h2 {} (<> span "Your cart" nil))
      (if (empty? products)
        (p {}
          (create-element :i {}
            (<> span "Please add some products to cart" nil))))
      (ul {}
        (->> products (map (fn [product]
            [(:id product) (li {}
              (<> span (str (:title product) " - " (:price product) " x " (:quantity product)) nil))]))))
      (p {}
        (<> span (str "Total: " total) nil))
      (p {}
        (button {:disabled (empty? products)
                 :on {:click (checkout products)}}
          (<> span "Checkout" nil)))
      (if (some? checkout-status)
        (p {}
          (<> span (str "Checkout " checkout-status) nil))))))
