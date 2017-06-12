
(ns app.component.container
  (:require-macros [respo.macros :refer [defcomp]])
  (:require
    [clojure.string :refer [capitalize]]
    [respo.alias :refer [create-element div button section header input footer span a h1]]
    [respo.cursor :refer [with-cursor]]
    [respo.comp.text :refer [comp-text]]
    [app.actions :refer [try-add-todo toggle-all clear-completed]]
    [app.component.todo :refer [comp-todo]]))

(defn ul [props & children]
  (create-element :ul props children))

(defn li [props & children]
  (create-element :li props children))

(defn strong [props & children]
  (create-element :strong props children))

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
        (h1 {} (comp-text "todos" nil))
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
              (strong {}
                (comp-text remaining))
              (comp-text (str " " (pluralize "item" remaining) " left") nil))
            (ul {:class-name "filters"}
              (->> (keys filters)
                (map (fn [filter-name]
                  [filter-name (li {}
                    (a {:class-name (if (= filter-name (:filter state)) "selected")
                        :event {:click (fn [e dispatch!]
                          (dispatch! :states [cursor (assoc state :filter filter-name)]))}}
                      (comp-text (capitalize (str filter-name)) nil)))]))))
            (if (> (count tasks) remaining)
              (button {:class-name "clear-completed"
                       :event {:click clear-completed}}
                (comp-text "Clear complited" nil)))))))))