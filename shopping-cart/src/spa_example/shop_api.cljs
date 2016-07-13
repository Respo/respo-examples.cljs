
(ns spa-example.shop-api)

(def products [
  {:id 1, :title "iPad 4 Mini", :price 500.01, :inventory 2}
  {:id 2, :title "H&M T-Shirt White", :price 10.99, :inventory 10}
  {:id 3, :title "Charli XCX - Sucker CD", :price 19.99, :inventory 5}])

(defn get-all-products [cb]
  (js/setTimeout (fn [] (cb products)) 100))

(defn buy-products [products, cb, error-cb]
  (js/setTimeout
    (fn []
      (if (> (.random js/Math) 0.5) (cb) (error-cb)))
    100))
