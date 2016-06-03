(ns leiningen.repetition-hunter
  "Find repetitions in code"
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer :all]
            [clojure.tools.namespace.find :refer [find-namespaces]]
            [leiningen.core.eval :refer :all]
            [leiningen.core.main :refer :all]
            [repetition.hunter :refer :all]))

(defn paths [project]
  (filter some? (concat
                 (:source-paths project)
                 [(:source-path project)]
                 (mapcat :source-paths (get-in project [:cljsbuild :builds]))
                 (mapcat :source-paths (get-in project [:cljx :builds])))))

(defn namespaces [project]
  (find-namespaces (map io/file (paths project))))

(defn ^:no-project-needed repetition-hunter
  "Find repetitions in code"
  [project & args]
  (if (empty? project)
    (warn "lein repetition-hunter needs to run inside a project")
    (let [ns-xs (if args (map read-string args)
                    (namespaces project))
          req `(do (use 'repetition.hunter)
                   (doseq [n# '~ns-xs]
                     (require n#)))]
      (debug "Analyzing: " ns-xs)
      (eval-in-project project
                       `(hunt '~ns-xs)
                       req))))
