
(ns spa-example.actions
  (:require
    [clojure.string :refer [blank?]]))

(defn try-add-todo [text]
  (fn [e dispatch! mutate!]
    (if
      (and
        (= (:key-code e) 13)
        (not (blank? text)))
      (do
        (dispatch! :add-todo text)
        (mutate! :text "")))))

(defn add-todo [e dispatch! mutate!]
  (dispatch! :add-todo nil))

(defn done-edit [index text]
  (fn [e dispatch! mutate!]
    (if (blank? text)
      (dispatch! :delete-todo index)
      (do
        (dispatch! :edit-todo [index text])
        (mutate! :editing false)))))

(defn input-keyup [index text]
  (fn [e dispatch! mutate!]
    (cond
      (= (:key-code e) 13)
      (if (blank? text)
        (dispatch! :delete-todo index)
        (do
          (dispatch! :edit-todo [index text])
          (mutate! :editing false)))
      (= (:key-code e) 27)
      (mutate! :editing false)
      :else nil)))

(defn delete-todo [index]
  (fn [e dispatch! mutate!]
    (dispatch! :delete-todo index)))

(defn toggle-todo [index]
  (fn [e dispatch! mutate!]
    (dispatch! :toggle-todo index)))

(defn toggle-all [e dispatch! mutate!]
  (dispatch! :toggle-all nil))

(defn clear-completed [e dispatch! mutate!]
  (dispatch! :clear-completed nil))
