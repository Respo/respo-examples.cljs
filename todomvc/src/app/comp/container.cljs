
(ns app.comp.container
  (:require
    [clojure.string :refer [capitalize]]
    [respo.core :refer [defcomp cursor-> list-> <> div button section header input footer span a h1 ul li create-element]]
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
        (h1 {} (<> "todos"))
        (input {:autofocus true :class-name "new-todo"
                :autocomplete "off"
                :placeholder "What needs to be done?"
                :value (:text state)
                :on-keydown (try-add-todo *cursor* state)
                :on-input (fn [e dispatch!]
                              (dispatch! :states [*cursor* (assoc state   :text (:value e))]))}))
      (if (not (empty? tasks))
        (section {:class-name "main"}
          (input {:class-name "toggle-all" :type "checkbox"
                  :checked (every? #(:done %1) tasks)
                  :on-change toggle-all})
          (list-> :ul {:class-name "todo-list"}
            (->> (filterv (get filters (:filter state)) tasks)
              (mapv (fn [todo]
                [(:id todo) (cursor-> (:id todo) comp-todo states todo)]))))))
      (if (not (empty? tasks))
        (let [remaining
                (count (filterv (fn [x] (not (:done x))) tasks))]
          (footer {:class-name "footer"}
            (span {:class-name "todo-count"}
              (create-element :strong {}
                (<> remaining))
              (<> (str " " (pluralize "item" remaining) " left")))
            (list-> :ul {:class-name "filters"}
              (->> (keys filters)
                (map (fn [filter-name]
                  [filter-name (li {}
                    (a {:class-name (if (= filter-name (:filter state)) "selected")
                        :on-click
                          (fn [e dispatch!]
                            (dispatch! :states [*cursor* (assoc state :filter filter-name)]))}
                      (<> (capitalize (str filter-name)))))]))))
            (if (> (count tasks) remaining)
              (button {:class-name "clear-completed"
                       :on-click clear-completed}
                (<> "Clear complited")))))))))
