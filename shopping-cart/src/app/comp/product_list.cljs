
(ns app.comp.product-list
  (:require
    [respo.core :refer [defcomp list-> <> div button br span ul li]]
    [app.actions :refer [add-to-cart]]))

(defcomp comp-product-list [store]
  (list-> :ul {}
    (->> (:all store)
      (map (fn [product]
        [(:id product)
         (li {}
           (<> (str (:title product) " - " (:price product)))
           (br {})
           (button {:disabled (<= (:inventory product) 0)
                    :on-click (add-to-cart product)}
              (<> "Add to cart")))])))))
