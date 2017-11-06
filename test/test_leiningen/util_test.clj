(ns test-leiningen.util-test
  (:require [clojure.test :refer :all]
            [leiningen.jupyter.util :as util]
            [clojure.java.io :as io]))

(deftest extract-resource-test
    (testing "test list what is in resource"
      (let [resource "lein-jupyter-parinfer"
            ret (doall (map str (util/list-resources-in resource)))
            expt '("lein-jupyter-parinfer/README.md"
                   "lein-jupyter-parinfer/index.js"
                   "lein-jupyter-parinfer/parinfer-codemirror.js"
                   "lein-jupyter-parinfer/parinfer.js")]
        (is (= (sort ret) expt))))
    (testing "the copy of the resources"
      (let [resource "lein-jupyter-parinfer"
            ret (util/copy-resource-dir-in-tmp-dir resource)]
        (is (= (count (.listFiles ret)) 4)))))