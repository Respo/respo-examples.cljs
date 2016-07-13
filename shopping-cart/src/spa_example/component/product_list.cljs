
(ns spa-example.component.product-list
  (:require
    [respo.alias :refer [create-comp create-element div button]]
    [respo.component.text :refer [comp-text]]
    [spa-example.actions :refer [add-to-cart]]))

(defn ul [props & children]
  (create-element :ul props children))

(defn li [props & children]
  (create-element :li props children))

(defn br [props & children]
  (create-element :li props children))

(defn render [store]
  (fn [state mutate!]
    (ul {}
      (->> (:all store)
        (map (fn [product]
          [(:id product)
           (li {}
             (comp-text (str (:title product) " - " (:price product)) nil)
             (br {})
             (button {:attrs {:disabled (<= (:inventory product) 0)}
                      :event {:click (add-to-cart product)}}))]))))))

(def comp-product-list (create-comp :product-list render))
