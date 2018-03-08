(defproject lein-jupyter "0.1.15"
  :description "Leiningen plugin for jupyter notebook."
  :url "https://github.com/provisdom/lein-jupyter"
  :license {:name " MIT License"}
  :dependencies [[org.clojars.didiercrunch/clojupyter "0.1.3"]  ;; this dependency needs to be
                                                                ;; updated in leiningen.jupyter.kernel
                                                                ;; manually.
                 [cheshire "5.8.0"]
                 [org.apache.commons/commons-exec "1.3"]]
  :resource-paths ["resources"]
  :eval-in-leiningen true)
