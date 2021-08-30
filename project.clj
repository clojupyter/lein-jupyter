(defproject nubank/lein-jupyter "0.1.23"
  :description "Leiningen plugin for jupyter notebook."
  :url "https://github.com/nubank/lein-jupyter"
  :license {:name "MIT License"}

  :repositories [["central" {:url "https://repo1.maven.org/maven2/" :snapshots false}]
                 ["clojars" {:url "https://clojars.org/repo/"}]]

  ;; TODO: Go back to clojupyter upstream after this PR is merged and a new release
  ;; is made: https://github.com/clojupyter/clojupyter/pull/135
  :dependencies [[dev.nubank/clojupyter "0.3.2-fix1"]
                 [org.apache.commons/commons-exec "1.3"]]
  :resource-paths ["resources"]
  :eval-in-leiningen true)
