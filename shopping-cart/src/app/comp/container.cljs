
(ns app.comp.container
  (:require-macros [respo.macros :refer [defcomp]])
  (:require
    [respo.alias :refer [create-element div hr h1 h2]]
    [respo.comp.text :refer [comp-text]]
    [app.actions :refer []]
    [app.comp.product-list :refer [comp-product-list]]
    [app.comp.cart :refer [comp-cart]]))

(defcomp comp-container [store]
  (div {:class-name "app"}
    (h1 {} (comp-text "Shopping Cart Example" nil))
    (hr {})
    (h2 {} (comp-text "Products" nil))
    (comp-product-list store)
    (hr {})
    (comp-cart store)))
