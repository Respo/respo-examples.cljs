
(ns app.comp.container
  (:require
    [respo.core :refer [defcomp <> div hr h1 h2 span]]
    [app.actions :refer []]
    [app.comp.product-list :refer [comp-product-list]]
    [app.comp.cart :refer [comp-cart]]))

(defcomp comp-container [store]
  (div {:class-name "app"}
    (<> h1 "Shopping Cart Example" nil)
    (hr {})
    (<> h2 "Products" nil)
    (comp-product-list store)
    (hr {})
    (comp-cart store)))
