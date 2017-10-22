(defproject lein-jupyter "0.1.0"
  :description "Leiningen plugin for jupyter notebook."
  :url "http://example.com/FIXME"
  :license {:name " MIT License"}
  :dependencies [[clojupyter "0.1.0"]
                 [cheshire "5.8.0"]
                 [org.apache.commons/commons-exec "1.3"]]
  :resource-paths ["resources"]
  :eval-in-leiningen true)
