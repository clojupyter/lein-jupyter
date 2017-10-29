(ns test-leiningen.jupyter-test
    (:require [clojure.test :refer :all]
              [leiningen.jupyter :as jupyter]))

(deftest run-jupyter
         (testing "get-jupyter-command"
           (let [sub-command "notebook"
                 args ["--port=9999" "--no-browser"]
                 expt "[jupyter, notebook, --port=9999, --no-browser]"]
             (is (= expt (str (jupyter/get-jupyter-command sub-command args)))))))
