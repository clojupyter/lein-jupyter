(ns leiningen.jupyter.kernel
  (:require [cheshire.core :as cheshire]
            [leiningen.core.main]
            [leiningen.core.eval :as eval]
            [clojure.string :refer [lower-case includes?]]
            [clojure.java.io :as io]))

(defn run-kernel [project argv]
  (let [curr-deps (or (:dependencies project) [])
        new-deps (conj curr-deps ['org.clojars.didiercrunch/clojupyter "0.1.0"])
        prj (assoc project :dependencies new-deps)]
    (eval/eval-in-project prj
                          (conj (list argv) `clojupyter.core/-main)
                          '(require 'clojupyter.core))))


(def python-kernel-script (-> "leinclojure.py" io/resource slurp))

(defn get-os []
  (let [os-name (-> "os.name" System/getProperty clojure.string/lower-case)]
       (cond
         (includes? os-name "mac") :mac
         (includes? os-name "win") :windows
         (includes? os-name "linux") :linux)))

(defn get-kernel-dir
  "Returns the directory where the kernel should be installed given the os"
  [os]
  (let [home (System/getenv "HOME")
        appdata (System/getenv "APPDATA")]
    (case os
      :mac (io/file home "Library/Jupyter/kernels/lein-clojure")
      :linux (io/file home ".local/share/jupyter/kernels/lein-clojure")
      :windows (io/file appdata "jupyter/kernels/lein-clojure"))))

(defn get-kernel-json [kernel-script-filename]
  (cheshire/generate-string {:display_name "Lein-Clojure"
                             :language "clojure"
                             :argv ["python3" (str kernel-script-filename) "{connection_file}"]}))

(defn create-kernel [kernel-dir]
  (let [kernel-json (io/file kernel-dir "kernel.json")
        kernel-script (io/file kernel-dir "leinclojure.py")]
    (io/make-parents kernel-json)
    (io/make-parents kernel-script)
    (spit (str kernel-json) (get-kernel-json kernel-script))
    (spit (str kernel-script) python-kernel-script)))

(defn install-kernel-on-linux []
  (let [kernel-dir (get-kernel-dir :linux)]
    (create-kernel kernel-dir)))

(defn install-kernel-on-mac []
  (let [kernel-dir (get-kernel-dir :mac)]
    (create-kernel kernel-dir)))

(defn install-kernel-on-windows []
  (let [kernel-dir (get-kernel-dir :windows)]
    (create-kernel kernel-dir)))

(def architecture-not-yet-supported "You system is not supported by lein jupyter.
the current supported systems are Linux Mac and Windows (In that order).")

(defn install-kernel
  "Install the lein-clojure kernel using the specifications at
  http://jupyter-client.readthedocs.io/en/latest/kernels.html"
  [& args]
  (case (get-os)
    :mac (install-kernel-on-mac)
    :linux (install-kernel-on-linux)
    :windows (install-kernel-on-windows)
    (leiningen.core.main/warn architecture-not-yet-supported)))

(defn kernel-installed?
  "return true is it is sensible to believe that kernel has properly been installed"
  []
  (let [kernel-dir (get-kernel-dir (get-os))]
    (.exists kernel-dir)))
