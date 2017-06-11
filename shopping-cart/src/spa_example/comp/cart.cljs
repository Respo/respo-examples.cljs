
(ns spa-example.comp.cart
  (:require
    [respo.alias :refer [create-comp create-element div button]]
    [respo.comp.text :refer [comp-text]]
    [respo.comp.debug :refer [comp-debug]]
    [spa-example.actions :refer [checkout]]))

(defn h2 [props & children]
  (create-element :h2 props children))

(defn ul [props & children]
  (create-element :ul props children))

(defn li [props & children]
  (create-element :li props children))

(defn p [props & children]
  (create-element :p props children))

(defn i [props & children]
  (create-element :i props children))

(defn render [store]
  (fn [cursor]
    (let [products
          (->> (:added store)
            (mapv (fn [item]
              (let [product (first (filter #(= (:id %1) (:id item))
                              (:all store)))]
                (assoc product :quantity (:quantity item))))))
          total (apply + (map #(* (:price %1) (:quantity %1)) products))
          checkout-status (:last-checkout store)]
      (div {:class-name "cart"}
        (h2 {} (comp-text "Your cart" nil))
        (if (empty? products)
          (p {}
            (i {}
              (comp-text "Please add some products to cart" nil))))
        (ul {}
          (->> products (mapv (fn [product]
              [(:id product) (li {}
                (comp-text (str (:title product) " - " (:price product) " x " (:quantity product)) nil))]))))
        (p {}
          (comp-text (str "Total: " total) nil))
        (p {}
          (button {:disabled (empty? products)
                   :event {:click (checkout products)}}
            (comp-text "Checkout")))
        (if (some? checkout-status)
          (p {}
            (comp-text (str "Checkout " checkout-status))))))))

(def comp-cart (create-comp :cart render))
