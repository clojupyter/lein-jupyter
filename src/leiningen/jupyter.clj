(ns leiningen.jupyter
  (:require [leiningen.core.main]
            [leiningen.jupyter.extension :refer [install-and-enable-extension]]
            [leiningen.jupyter.kernel :refer [install-kernel
                                              run-kernel
                                              kernel-installed?]]
            [leiningen.jupyter.params :as params])

  (:import [org.apache.commons.exec CommandLine
                                    DefaultExecutor
                                    PumpStreamHandler]))

(defn get-jupyter-command [jupyter sub-command jupyter-arguments]
  (let [all-arguments (into [sub-command] jupyter-arguments)
        add-args #(.addArgument %1 %2)]
      (reduce add-args (new CommandLine jupyter) all-arguments)))


(defn start-jupyter-notebook [project environ jupyter-arguments]
  (let [executor (new DefaultExecutor)
        stream-handler (new PumpStreamHandler System/out System/err System/in)
        jupyter (params/jupyer-executable project)
        cmd (get-jupyter-command jupyter "notebook" jupyter-arguments)]
    (.setStreamHandler executor stream-handler)
    (.execute executor cmd environ)))

(defn start-jupyter-lab [project environ jupyter-arguments]
  (let [executor (new DefaultExecutor)
        stream-handler (new PumpStreamHandler System/out System/err System/in)
        jupyter (params/jupyer-executable project)
        cmd (get-jupyter-command jupyter "lab" jupyter-arguments)]
    (.setStreamHandler executor stream-handler)
    (.execute executor cmd environ)))

(defn add-to-system-environment [new-envs]
  (let [env (apply hash-map  (mapcat (fn [[x y]] [x y]) (System/getenv)))]
    (into env new-envs)))

(defn notebook [project lein-wd & args]
  (if (not (kernel-installed?))
    (leiningen.core.main/warn "It seems you have not installed the lein-jupyter kernel.  "
                              "You should run `lein jupyter install-kernel`."))
  (let [new-env {"LEIN_WORKING_DIRECTORY" lein-wd}
        env (add-to-system-environment new-env)]
    (start-jupyter-notebook project env args)))

(defn lab [project lein-wd & args]
  (if (not (kernel-installed?))
    (leiningen.core.main/warn "It seems you have not installed the lein-jupyter kernel.  "
                              "You should run `lein jupyter install-kernel`."))
  (let [new-env {"LEIN_WORKING_DIRECTORY" lein-wd}
        env (add-to-system-environment new-env)]
    (start-jupyter-lab project env args)))


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
      to the current project.  All extra parameters will be passed
      to `jupyter notebook`.  For instance `jupyter notebook --port=9876`
      will start jupyter notebook on port 9876
    install-kernel:
      Install jupyter notebook's clojure kernel.  This needs to be run
      once.  If no argument passed, the kernel will be installed in the
      jupyter user space.  If an argument is passed, the kernel will
      be installed into the specified directory.
    uninstall-kernel:
      Uninstall jupyter notebook's clojure kernel.
  "
  ([project sub-command & args]
   (let [cwd (-> (java.io.File. ".") .getAbsolutePath)]
     (case sub-command
       "install-kernel" (do
                          (apply install-kernel args)
                          (install-and-enable-extension project))
       "uninstall-kernel" (leiningen.core.main/info (str "Not yet implemented.  You can use "
                                                         "'jupyter kernelspec uninstall lein-clojure' "
                                                          "to uninstall the kernel manually."))
       "notebook" (apply notebook project cwd args)  ;; main entry
       "lab" (apply lab project cwd args)
       "kernel" (apply run-kernel project args)   ;; hidden kernel
       (leiningen.core.main/info (:doc (meta #'jupyter))))))
  ([project]
   (leiningen.core.main/info (:doc (meta #'jupyter)))))



(def -main jupyter)
