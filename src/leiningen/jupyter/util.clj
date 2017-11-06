(ns leiningen.jupyter.util
  (:import [java.nio.file Files]
           [java.nio.file.attribute FileAttribute])
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(defn get-resource-as-stream [name]
  (let [loader  (.getContextClassLoader (Thread/currentThread))]
    (.getResourceAsStream loader name)))

(defn list-resources-in [name]
  (let [base (io/file name)
        files (-> name
                  get-resource-as-stream
                  slurp
                  (string/split #"\n"))]
    (map #(io/file base %) files)))

(defn get-resource-by-name [n]
  (slurp (io/resource (str n))))


(defn copy-resource-dir-in-tmp-dir [name]
  (let [resources (list-resources-in name)
        tmp-dir (.toFile (Files/createTempDirectory "lein-jupyter" (into-array FileAttribute [])))]
    (doall (map #(spit (io/file tmp-dir (.getName %))  (get-resource-by-name %)) resources))
    tmp-dir))
