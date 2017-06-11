
(ns spa-example.comp.container
  (:require
    [respo.alias :refer [create-comp create-element div hr]]
    [respo.comp.text :refer [comp-text]]
    [spa-example.actions :refer []]
    [spa-example.comp.product-list :refer [comp-product-list]]
    [spa-example.comp.cart :refer [comp-cart]]))

(defn h1 [props & children]
  (create-element :h1 props children))

(defn h2 [props & children]
  (create-element :h2 props children))

(defn render [store]
  (fn [cursor]
    (div {:class-name "app"}
      (h1 {} (comp-text "Shopping Cart Example" nil))
      (hr {})
      (h2 {} (comp-text "Products" nil))
      (comp-product-list store)
      (hr {})
      (comp-cart store))))

(def comp-container (create-comp :container render))
