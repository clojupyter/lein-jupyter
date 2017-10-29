(ns test-leiningen.jupyter-test
    (:require [clojure.test :refer :all]
              [clojure.java.io :as io]
              [leiningen.jupyter :as jupyter]
              [leiningen.jupyter.kernel :as kernel]))

(deftest run-jupyter
  (testing "get-jupyter-command"
    (let [sub-command "notebook"
          args ["--port=9999" "--no-browser"]
          expt "[jupyter, notebook, --port=9999, --no-browser]"]
      (is (= expt (str (jupyter/get-jupyter-command sub-command args)))))))

(deftest install-kernel
  (testing "the platform specific kernel directory"
    (let [environ {"HOME" "/home/testuser"
                   "APPDATA" "/crazy/windows/directory"}
          expt-linux (io/as-file "/home/testuser/.local/share/jupyter/kernels")
          expt-mac (io/as-file "/home/testuser/Library/Jupyter/kernels")
          expt-windows (io/as-file "/crazy/windows/directory/jupyter/kernels")]
      (is (= expt-linux (kernel/get-platform-specific-kernel-dir :linux environ)))
      (is (= expt-mac (kernel/get-platform-specific-kernel-dir :mac environ)))
      (is (= expt-windows (kernel/get-platform-specific-kernel-dir :windows environ))))))