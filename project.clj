(defproject lein-jupyter "0.1.16"
  :description "Leiningen plugin for jupyter notebook."
  :url "https://github.com/clojupyter/lein-jupyter"
  :license {:name " MIT License"}
  :dependencies [[org.clojars.didiercrunch/clojupyter "0.1.5"]  ;; this dependency needs to be
                                                                ;; updated in leiningen.jupyter.kernel
                                                                ;; manually.
                 [cheshire "5.8.0"]
                 [org.apache.commons/commons-exec "1.3"]]
  :resource-paths ["resources"]
  :eval-in-leiningen true)
