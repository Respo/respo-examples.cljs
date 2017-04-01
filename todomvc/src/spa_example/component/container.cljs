
(ns spa-example.component.container
  (:require
    [clojure.string :refer [capitalize]]
    [respo.alias :refer [create-comp create-element div button section header input footer span a]]
    [respo.cursor :refer [with-cursor]]
    [respo.comp.text :refer [comp-text]]
    [spa-example.actions :refer [try-add-todo toggle-all clear-completed]]
    [spa-example.component.todo :refer [comp-todo]]))

(defn h1 [props & children]
  (create-element :h1 props children))

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

(defn render [store]
  (fn [cursor]
    (let [states (:states store)
          tasks (:tasks store)
          state (or (:data states) initial-state)]
      (section {:attrs {:class-name "todoapp"}}
        (header {:attrs {:class-name "header"}}
          (h1 {} (comp-text "todos" nil))
          (input {:attrs {:autofocus true :class-name "new-todo"
                          :autocomplete "off"
                          :placeholder "What needs to be done?"
                          :value (:text state)}
                  :event {:keydown (try-add-todo cursor state)
                          :input (fn [e dispatch!]
                                    (dispatch! :states [cursor (assoc state   :text (:value e))]))}}))
        (if (not (empty? tasks))
          (section {:attrs {:class-name "main"}}
            (input {:attrs {:class-name "toggle-all" :type "checkbox"
                            :checked (every? #(:done %1) tasks)}
                    :event {:change toggle-all}})
            (ul {:attrs {:class-name "todo-list"}}
              (->> (filterv (get filters (:filter state)) tasks)
                (mapv (fn [todo]
                  [(:id todo) (with-cursor (:id todo) (comp-todo (get states (:id todo)) todo))]))))))
        (if (not (empty? tasks))
          (let [remaining
                  (count (filterv (fn [x] (not (:done x))) tasks))]
            (footer {:attrs {:class-name "footer"}}
              (span {:attrs {:class-name "todo-count"}}
                (strong {}
                  (comp-text remaining))
                (comp-text (str " " (pluralize "item" remaining) " left") nil))
              (ul {:attrs {:class-name "filters"}}
                (->> (keys filters)
                  (map (fn [filter-name]
                    [filter-name (li {}
                      (a {:attrs {:class-name
                                    (if (= filter-name (:filter state)) "selected")}
                          :event {:click (fn [e dispatch!]
                            (dispatch! :states [cursor (assoc state :filter filter-name)]))}}
                        (comp-text (capitalize (str filter-name)) nil)))]))))
              (if (> (count tasks) remaining)
                (button {:attrs {:class-name "clear-completed"}
                         :event {:click clear-completed}}
                  (comp-text "Clear complited" nil))))))))))

(def comp-container (create-comp :container render))
