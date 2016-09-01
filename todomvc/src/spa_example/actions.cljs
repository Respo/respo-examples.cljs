
(ns spa-example.actions
  (:require
    [clojure.string :refer [blank?]]))

(defn try-add-todo [text mutate!]
  (fn [e dispatch!]
    (if
      (and
        (= (:key-code e) 13)
        (not (blank? text)))
      (do
        (dispatch! :add-todo text)
        (mutate! :text "")))))

(defn add-todo [e dispatch!]
  (dispatch! :add-todo nil))

(defn done-edit [id text mutate!]
  (fn [e dispatch!]
    (if (blank? text)
      (dispatch! :delete-todo id)
      (do
        (dispatch! :edit-todo [id text])
        (mutate! :editing false)))))

(defn input-keyup [id text mutate!]
  (fn [e dispatch!]
    (cond
      (= (:key-code e) 13)
      (if (blank? text)
        (dispatch! :delete-todo id)
        (do
          (dispatch! :edit-todo [id text])
          (mutate! :editing false)))
      (= (:key-code e) 27)
      (mutate! :editing false)
      :else nil)))

(defn delete-todo [id]
  (fn [e dispatch!]
    (dispatch! :delete-todo id)))

(defn toggle-todo [od]
  (fn [e dispatch!]
    (dispatch! :toggle-todo od)))

(defn toggle-all [e dispatch!]
  (dispatch! :toggle-all nil))

(defn clear-completed [e dispatch!]
  (dispatch! :clear-completed nil))
