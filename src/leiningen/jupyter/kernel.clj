(ns leiningen.jupyter.kernel
  (:require [cheshire.core :as cheshire]
            [leiningen.core.main]
            [leiningen.core.eval :as eval]
            [clojure.java.shell :refer [sh]]
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

(defn get-platform-specific-kernel-dir
  "Returns the directory where the kernel should be installed given the os"
  [os environ]
  (let [home (get environ "HOME")
        appdata (get environ "APPDATA")]
    (case os
      :mac (io/file home "Library/Jupyter/kernels/")
      :linux (io/file home ".local/share/jupyter/kernels")
      :windows (io/file appdata "jupyter/kernels"))))

(defn get-kernel-json [kernel-script-filename]
  (cheshire/generate-string {:display_name "Lein-Clojure"
                             :language "clojure"
                             :codemirror_mode "clojure"
                             :mimetype "text/x-clojure"
                             :argv ["python3" (str kernel-script-filename) "{connection_file}"]}))

(defn create-kernel [kernel-dir]
  (let [kernel-json (io/file kernel-dir "lein-clojure" "kernel.json")
        kernel-script (io/file kernel-dir "lein-clojure" "leinclojure.py")]
    (io/make-parents kernel-json)
    (spit (str kernel-json) (get-kernel-json kernel-script))
    (spit (str kernel-script) python-kernel-script)))


(def architecture-not-yet-supported "You system is not supported by lein jupyter.
the current supported systems are Linux Mac and Windows (In that order).")

(defn enable-extension
  "enable the lein-jupyter-parinfer extension the user space"
    []
    (let [enable-out (sh "jupyter" "nbextension" "enable" "lein-jupyter-parinfer/index" "--user")]
      (if (not= 0 (:exit enable-out))
        (leiningen.core.main/warn "Did not succeed to enable lein-jupyter-parinfer extension"
                                  (:err enable-out))
        true)))

(defn install-extension
  "Instal the lein-jupyter-parinfer extension the user space"
    []
    (let [extension-dir ( -> "lein-jupyter-parinfer" io/resource io/file)
          install-out (sh "jupyter" "nbextension" "install" (.getCanonicalPath extension-dir) "--user")]
      (if (not= 0 (:exit install-out))
        (leiningen.core.main/warn "Did not succeed to install lein-jupyter-parinfer extension"
          (:err install-out))
        true)))

(defn install-and-enable-extension []
  (and (install-extension)
       (enable-extension)))



(defn install-kernel
  "Install the lein-clojure kernel.  If no argument passed, it
  will install the kernel in the userspace as specified at
  http://jupyter-client.readthedocs.io/en/latest/kernels.html.
  If an argument is passed, it will install the kernel in that
  directory.

  The kernel will be installed at <location>/lein-clojure."
  ([location]
   (-> location io/as-file create-kernel)
   (leiningen.core.main/info "kernel successfully installed at " location))
  ([]
   (let [os (get-os)]
     (if (nil? os)
       (leiningen.core.main/warn architecture-not-yet-supported)
       (-> os (get-platform-specific-kernel-dir (System/getenv)) install-kernel)))))

(defn kernel-installed?
  "return true is it is sensible to believe that kernel has properly been installed"
  []
  (let [kernel-dir (get-platform-specific-kernel-dir (get-os) (System/getenv))]
    (.exists kernel-dir)))
