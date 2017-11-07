(ns test-leiningen.util-test
  (:require [clojure.test :refer :all]
            [leiningen.jupyter.extension :as util]
            [clojure.java.io :as io]))

(deftest extract-resource-test
    (testing "the copy of the resources"
      (let [resource "lein-jupyter-parinfer"
            ret (util/copy-resource-dir-in-tmp-dir resource)]
        (is (= (.getName ret) "lein-jupyter-parinfer"))
        (is (= (count (.listFiles ret)) 4)))))
