(ns leiningen.jupyter.params)

(defn jupyer-executable [project]
   (get-in project [:jupyter-options :jupyter-path] "jupyter"))