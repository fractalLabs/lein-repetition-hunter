(defproject lein-repetition-hunter "0.1.0-SNAPSHOT"
  :description "Find repetitions in code"
  :url "https://github.com/fractalLabs/lein-repetition-hunter"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/tools.namespace "0.2.11"]
                 [repetition-hunter "1.0.0"]]
  :eval-in-leiningen true
  :repl-options {:init-ns leiningen.repetition-hunter})
