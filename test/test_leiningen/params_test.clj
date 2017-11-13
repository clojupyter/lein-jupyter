(ns test-leiningen.params-test
  (:require [clojure.test :refer :all]
            [leiningen.jupyter.params :as params]))

(defn mock-lein-project
  ([jupyter-options]
   {:description "FIXME: write description"
    :url "http://example.com/FIXME"
    :license {:name "Eclipse Public License"
              :url "http://www.eclipse.org/legal/epl-v10.html"}
    :target-path "target/%s"
    :profiles {:uberjar {:aot :all}}
    :jupyter-options jupyter-options})
  ([]
   (mock-lein-project {})))


(deftest testing-project-parser
  (testing "the jupyter location param"
    (is (= (params/jupyer-executable (mock-lein-project)) "jupyter"))
    (is (= (params/jupyer-executable (mock-lein-project {:jupyter-path "hehe"})) "hehe"))))