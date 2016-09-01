
(set-env!
 :asset-paths #{"assets"}
 :source-paths #{}
 :resource-paths #{"src"}

 :dev-dependencies '[]
 :dependencies '[[org.clojure/clojurescript "1.9.216"     :scope "test"]
                 [org.clojure/clojure       "1.8.0"       :scope "test"]
                 [adzerk/boot-cljs          "1.7.228-1"   :scope "test"]
                 [adzerk/boot-reload        "0.4.11"      :scope "test"]
                 [binaryage/devtools        "0.7.2"]
                 [mvc-works/hsl             "0.1.2"]
                 [respo                     "0.3.9"]])

(require '[adzerk.boot-cljs   :refer [cljs]]
         '[adzerk.boot-reload :refer [reload]])

(deftask dev []
  (comp
    (watch)
    (reload :on-jsload 'spa-example.core/on-jsload
            :cljs-asset-path ".")
    (cljs)
    (target)))

(deftask build-advanced []
  (set-env!
    :asset-paths #{"assets"}
    :source-paths #{"src"})
  (comp
    (cljs :optimizations :advanced)
    (target)))

(deftask rsync []
  (with-pre-wrap fileset
    (sh "rsync" "-r" "target/" "tiye:repo/mvc-works/respo-spa-example" "--exclude" "main.out" "--delete")
    fileset))
