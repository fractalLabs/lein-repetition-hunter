(ns leiningen.repetition-hunter
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer :all]
            [clojure.tools.namespace.find :refer [find-namespaces]]
            [leiningen.core.eval :refer :all]
            [repetition.hunter :refer :all]))

(defn str-all-ns []
  (map str (all-ns)))

(defn project-namespaces
  [project]
  (let [re-name (re-pattern (:name project))]
    (filter #(re-find re-name %) (str-all-ns))))

(defn ^:no-project-needed repetition-hunter
  [project & args]
  (let [paths (filter some? (concat
                              (:source-paths project)
                              [(:source-path project)]
                              (mapcat :source-paths (get-in project [:cljsbuild :builds]))
                              (mapcat :source-paths (get-in project [:cljx :builds]))))
        ns-xs (find-namespaces (map io/file paths))
        req `(do (use 'repetition.hunter)
                 (doseq [n# '~ns-xs]
                   (require n#)))]
    (eval-in-project project
                     `(hunt '~ns-xs)
                     req)))
