(defproject nubank/lein-jupyter "0.1.22"
  :description "Leiningen plugin for jupyter notebook."
  :url "https://github.com/nubank/lein-jupyter"
  :license {:name "MIT License"}

  :repositories [["central" {:url "https://repo1.maven.org/maven2/" :snapshots false}]
                 ["clojars" {:url "https://clojars.org/repo/"}]]

  :dependencies [[clojupyter "0.3.2"]
                 [cheshire "5.10.0"]
                 [org.apache.commons/commons-exec "1.3"]

                 ;; cheshire uses jackson 2.10.2 but clojupyter expects an older version
                 [com.fasterxml.jackson.core/jackson-core "2.10.2"]
                 [com.fasterxml.jackson.dataformat/jackson-dataformat-cbor "2.10.2"]
                 [com.fasterxml.jackson.dataformat/jackson-dataformat-smile "2.10.2"]]
  :resource-paths ["resources"]
  :eval-in-leiningen true)
