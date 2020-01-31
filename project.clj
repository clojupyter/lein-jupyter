(defproject lein-jupyter "0.1.17"
  :description "Leiningen plugin for jupyter notebook."
  :url "https://github.com/clojupyter/lein-jupyter"
  :license {:name " MIT License"}
  :dependencies [[clojupyter "0.3.1"]  ;; this dependency needs to be
                                                                ;; updated in leiningen.jupyter.kernel
                                                                ;; manually.
                 [cheshire "5.8.0"]
                 [org.apache.commons/commons-exec "1.3"]]
  :resource-paths ["resources"]
  :eval-in-leiningen true)
