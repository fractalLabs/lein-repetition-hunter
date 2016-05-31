(ns leiningen.repetition-hunter
  "Find repetitions in code"
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer :all]
            [clojure.tools.namespace.find :refer [find-namespaces]]
            [leiningen.core.eval :refer :all]
            [leiningen.core.main :refer :all]
            [repetition.hunter :refer :all]))

(defn str-all-ns []
  (map str (all-ns)))

(defn project-namespaces
  [project]
  (let [re-name (re-pattern (:name project))]
    (filter #(re-find re-name %) (str-all-ns))))

(defn ^:no-project-needed repetition-hunter
  "Find repetitions in code"
  [project & args]
  (if (empty? project)
    (warn "lein repetition-hunter needs to run inside a project")
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
                       req))))
