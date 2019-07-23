(defproject nubank/lein-jupyter "0.1.17"
  :description "Leiningen plugin for jupyter notebook."
  :url "https://github.com/nubank/lein-jupyter"
  :license {:name "MIT License"}

  :repositories [["central"  {:url "https://repo1.maven.org/maven2/" :snapshots false}]
                 ["clojars"  {:url "https://clojars.org/repo/"}]]

  :dependencies [[nubank/clojupyter "0.2.2"] ;; this dependency needs to be
                                             ;; updated in leiningen.jupyter.kernel
                                             ;; manually.
                 [cheshire "5.8.1"]
                 [org.apache.commons/commons-exec "1.3"]]
  :resource-paths ["resources"]
  :eval-in-leiningen true)
