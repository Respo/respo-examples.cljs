
(ns app.actions)

(defn increment [e dispatch!]
  (dispatch! :inc nil))

(defn decrement [e dispatch!]
  (dispatch! :dec nil))

(defn increment-if-odd [old-value]
  (fn [e dispatch!]
    (if (odd? old-value)
      (dispatch! :inc nil))))

(defn increment-async [e dispatch!]
  (js/setTimeout (fn []
    (dispatch! :inc nil))))
