
(ns app.comp.product-list
  (:require-macros [respo.macros :refer [defcomp]])
  (:require
    [respo.alias :refer [create-element div button br]]
    [respo.comp.text :refer [comp-text]]
    [app.actions :refer [add-to-cart]]))

(defn ul [props & children]
  (create-element :ul props children))

(defn li [props & children]
  (create-element :li props children))

(defcomp comp-product-list [store]
  (ul {}
    (->> (:all store)
      (map (fn [product]
        [(:id product)
         (li {}
           (comp-text (str (:title product) " - " (:price product)) nil)
           (br {})
           (button {:disabled (<= (:inventory product) 0)
                    :event {:click (add-to-cart product)}}
              (comp-text "Add to cart" nil)))])))))
