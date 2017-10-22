(ns leiningen.jupyter
  (:require [leiningen.core.main]
            [leiningen.jupyter.kernel :refer [install-kernel run-kernel]])

  (:import [org.apache.commons.exec CommandLine
                                    DefaultExecutor
                                    PumpStreamHandler]))

(defn start-jupyter-notebook [environ]
  (let [executor (new DefaultExecutor)
        stream-handler (new PumpStreamHandler System/out System/err System/in)
        cmd (doto (new CommandLine "jupyter")
                  (.addArgument "notebook"))]
    (.setStreamHandler executor stream-handler)
    (.execute executor cmd environ)))

(defn add-to-system-environment [new-envs]
  (let [env (apply hash-map  (mapcat (fn [[x y]] [x y]) (System/getenv)))]
    (into env new-envs)))

(defn notebook [lein-wd & args]
  (let [new-env {"LEIN_WORKING_DIRECTORY" lein-wd}
        env (add-to-system-environment new-env)]
    (start-jupyter-notebook env)))


(defn jupyter
  "Leiningen's jupyter integration.

  To use this leiningen pluging, you need to have jupyter notebook
  installed.  See http://jupyter.org/ for instructions.

  Once you have jupyter notebook installed, you will need to run the
  `lein jupyter install-kernel` command once.  This will install the
  `lein-clojure` kernel to your jupyter installation.

  Afterward, `lein jupyter notebook` will launch a jupyter notebook
  in the same fashion then `jupyter notebook`.  The `lein-clojure`
  kernel in the notebook will then be hooked in the current project.

  Under the hood, lein-jupyter uses the excellent clojupyter clojure
  kernel.  Consequently, you can access any of its functionalities.

  Commands:
    notebook:
      Starts jupyter notebook and links jupyter notebook's kernel
      to the current project.
    install-kernel:
      Install jupyter notebook's clojure kernel.  This needs to be run
      once.
    uninstall-kernel:
      Uninstall jupyter notebook's clojure kernel.
  "
  ([project sub-command & args]
   (let [cwd (-> (java.io.File. ".") .getAbsolutePath)]
     (case sub-command
       "install-kernel" (apply install-kernel args)  ;; install the kernel
       "uninstall-kernel" (leiningen.core.main/info (str "Not yet implemented.  You can use "
                                                         "'jupyter kernelspec uninstall lein-clojure' "
                                                          "to uninstall the kernel manually."))
       "notebook" (apply notebook cwd args)  ;; main entry
       "kernel" (apply run-kernel project args)   ;; hidden kernel
       (leiningen.core.main/info (:doc (meta #'jupyter))))))
  ([project]
   (leiningen.core.main/info (:doc (meta #'jupyter)))))



(def -main jupyter)