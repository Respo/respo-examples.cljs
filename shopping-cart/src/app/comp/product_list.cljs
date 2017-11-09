
(ns app.comp.product-list
  (:require-macros [respo.macros :refer [defcomp list-> <> div button br span ul li]])
  (:require
    [respo.core :refer [create-comp]]
    [app.actions :refer [add-to-cart]]))

(defcomp comp-product-list [store]
  (list-> :ul {}
    (->> (:all store)
      (map (fn [product]
        [(:id product)
         (li {}
           (<> span (str (:title product) " - " (:price product)) nil)
           (br {})
           (button {:disabled (<= (:inventory product) 0)
                    :on {:click (add-to-cart product)}}
              (<> span "Add to cart" nil)))])))))
