(defproject nubank/lein-jupyter "0.1.21"
  :description "Leiningen plugin for jupyter notebook."
  :url "https://github.com/nubank/lein-jupyter"
  :license {:name "MIT License"}

  :repositories [["central" {:url "https://repo1.maven.org/maven2/" :snapshots false}]
                 ["clojars" {:url "https://clojars.org/repo/"}]]

  :dependencies [[clojupyter "0.3.2"]
                 ;; Use the same version as used in clojupyter, or this can result in strange errors
                 [cheshire "5.8.1"]
                 [org.apache.commons/commons-exec "1.3"]]

  :resource-paths ["resources"]
  :eval-in-leiningen true)
