(ns leiningen.jupyter.extension
  (:import [java.nio.file Files]
           [java.nio.file.attribute FileAttribute])
  (:require [clojure.java.io :as io]
            [clojure.java.shell :refer [sh]]
            [leiningen.jupyter.params :as params]))


(def resources
  (map io/file ["lein-jupyter-parinfer/README.md"
                "lein-jupyter-parinfer/index.js"
                "lein-jupyter-parinfer/parinfer-codemirror.js"
                "lein-jupyter-parinfer/parinfer.js"]))

(defn get-resource-by-name [n]
  (slurp (io/resource (str n))))

(defn move-resource [dest content]
  (io/make-parents dest)
  (spit dest content))


(defn copy-resource-dir-in-tmp-dir [name]
  (let [root-tmp-dir (.toFile (Files/createTempDirectory "lein-jupyter" (into-array FileAttribute [])))
        tmp-dir (io/file root-tmp-dir name)]
    (doall (map #(move-resource (io/file tmp-dir (.getName %))  (get-resource-by-name %)) resources))
    tmp-dir))

(defn enable-extension
  "enable the lein-jupyter-parinfer extension the user space"
  [jupyter]
  (let [enable-out (sh jupyter "nbextension" "enable" "lein-jupyter-parinfer/index" "--user")]
    (if (not= 0 (:exit enable-out))
      (leiningen.core.main/warn "Did not succeed to enable lein-jupyter-parinfer extension"
                                (:err enable-out))
      true)))

(defn install-extension
  "Instal the lein-jupyter-parinfer extension the user space"
  [jupyter]
  (let [extension-dir (copy-resource-dir-in-tmp-dir "lein-jupyter-parinfer")
        install-out (sh jupyter "nbextension" "install" (.getCanonicalPath extension-dir) "--user")]
    (if (not= 0 (:exit install-out))
      (leiningen.core.main/warn "Did not succeed to install lein-jupyter-parinfer extension"
                                (:err install-out))
      true)))

(defn install-and-enable-extension [project]
  (let [jupyter (params/jupyer-executable project)]
    (and (install-extension jupyter)
         (enable-extension jupyter))))
