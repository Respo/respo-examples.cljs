
(ns app.comp.product-list
  (:require
    [respo.alias :refer [create-comp create-element div button]]
    [respo.comp.text :refer [comp-text]]
    [app.actions :refer [add-to-cart]]))

(defn ul [props & children]
  (create-element :ul props children))

(defn li [props & children]
  (create-element :li props children))

(defn br [props & children]
  (create-element :br props children))

(defn render [store]
  (fn [cursor]
    (ul {}
      (->> (:all store)
        (map (fn [product]
          [(:id product)
           (li {}
             (comp-text (str (:title product) " - " (:price product)) nil)
             (br {})
             (button {:disabled (<= (:inventory product) 0)
                      :event {:click (add-to-cart product)}}
                (comp-text "Add to cart" nil)))]))))))

(def comp-product-list (create-comp :product-list render))
