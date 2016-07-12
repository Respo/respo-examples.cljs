
(ns spa-example.component.container
  (:require
    [clojure.string :refer [capitalize]]
    [respo.alias :refer [create-comp create-element div button section header input footer span a]]
    [respo.component.text :refer [comp-text]]
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

(def filters {"all" (fn [x] true)
              "active" #(not (:done %1))
              "completed" #(:done %1)})

(defn init-state [store] {:text "" :filter (first (keys filters))})

(defn update-state [state op op-data]
  (case op
    :text (assoc state :text op-data)
    :filter (assoc state :filter op-data)
    state))

(defn render [store]
  (fn [state mutate!]
    (section {:attrs {:class-name "todoapp"}}
      (header {:attrs {:class-name "header"}}
        (h1 {} (comp-text "todos" nil))
        (input {:attrs {:autofocus true :class-name "new-todo"
                        :autocomplete "off"
                        :placeholder "What needs to be done?"
                        :value (:text state)}
                :event {:keydown (try-add-todo (:text state))
                        :input (fn [e dispatch! mutate!] (mutate! :text (:value e)))}}))
      (if (not (empty? store))
        (section {:attrs {:class-name "main"}}
          (input {:attrs {:class-name "toggle-all" :type "checkbox"
                          :checked (every? #(:done %1) store)}
                  :event {:change toggle-all}})
          (ul {:attrs {:class-name "todo-list"}}
            (->> (filterv (get filters (:filter state)) store)
              (map-indexed (fn [i todo]
                [i (comp-todo todo i)]))))))
      (if (not (empty? store))
        (let [remaining
                (count (filterv (fn [x] (not (:done x))) store))]
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
                        :event {:click (fn [e dispatch! mutate!]
                          (mutate! :filter filter-name))}}
                      (comp-text (capitalize filter-name) nil)))]))))
            (if (> (count store) remaining)
              (button {:attrs {:class-name "clear-completed"}
                       :event {:click clear-completed}}
                (comp-text "Clear complited" nil)))))))))

(def comp-container (create-comp :container init-state update-state render))
