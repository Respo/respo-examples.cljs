
(ns app.actions
  (:require
    [app.shop-api :as api]))

(defn add-to-cart [product]
  (fn [e dispatch!]
    (if (pos? (:inventory product))
      (dispatch! :add-to-cart (:id product)))))

(defn checkout [products]
  (fn [e dispatch!]
    (api/buy-products products
      (fn [] (dispatch! :checkout-success))
      (fn [] (dispatch! :checkout-failure)))))

(defn get-all-products [dispatch!]
  (api/get-all-products (fn [products]
    (dispatch! :receive-products products))))
