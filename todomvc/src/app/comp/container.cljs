
(ns app.comp.container
  (:require-macros
    [respo.macros :refer [defcomp <> div button section header input footer span a h1 ul li]])
  (:require
    [clojure.string :refer [capitalize]]
    [respo.core :refer [create-element create-comp]]
    [respo.cursor :refer [with-cursor]]
    [app.actions :refer [try-add-todo toggle-all clear-completed]]
    [app.comp.todo :refer [comp-todo]]))

(defn pluralize [text n]
  (if (> n 1) (str text "s") text))

(def filters {:all (fn [x] true)
              :active #(not (:done %1))
              :completed #(:done %1)})

(def initial-state {:text "" :filter :all})

(defcomp comp-container [store]
  (let [states (:states store)
        tasks (:tasks store)
        state (or (:data states) initial-state)]
    (section  {:class-name "todoapp"}
      (header {:class-name "header"}
        (h1 {} (<> span "todos" nil))
        (input {:autofocus true :class-name "new-todo"
                :autocomplete "off"
                :placeholder "What needs to be done?"
                :value (:text state)
                :event {:keydown (try-add-todo cursor state)
                        :input (fn [e dispatch!]
                                  (dispatch! :states [cursor (assoc state   :text (:value e))]))}}))
      (if (not (empty? tasks))
        (section {:class-name "main"}
          (input {:class-name "toggle-all" :type "checkbox"
                  :checked (every? #(:done %1) tasks)
                  :event {:change toggle-all}})
          (ul {:class-name "todo-list"}
            (->> (filterv (get filters (:filter state)) tasks)
              (mapv (fn [todo]
                [(:id todo) (with-cursor (:id todo) (comp-todo (get states (:id todo)) todo))]))))))
      (if (not (empty? tasks))
        (let [remaining
                (count (filterv (fn [x] (not (:done x))) tasks))]
          (footer {:class-name "footer"}
            (span {:class-name "todo-count"}
              (create-element :strong {}
                (<> span remaining nil))
              (<> span (str " " (pluralize "item" remaining) " left") nil))
            (ul {:class-name "filters"}
              (->> (keys filters)
                (map (fn [filter-name]
                  [filter-name (li {}
                    (a {:class-name (if (= filter-name (:filter state)) "selected")
                        :event {:click (fn [e dispatch!]
                          (dispatch! :states [cursor (assoc state :filter filter-name)]))}}
                      (<> span (capitalize (str filter-name)) nil)))]))))
            (if (> (count tasks) remaining)
              (button {:class-name "clear-completed"
                       :event {:click clear-completed}}
                (<> span "Clear complited" nil)))))))))
