(defproject hellonico/lein-jupyter "0.1.17"
  :description "Leiningen plugin for jupyter notebook."
  :url "https://github.com/clojupyter/lein-jupyter"
  :license {:name " MIT License"}
  :dependencies [[clojupyter "0.3.1"]  ;; this dependency needs to be
                                                                ;; updated in leiningen.jupyter.kernel
                                                                ;; manually.
                 [cheshire "5.8.0"]
                 [org.apache.commons/commons-exec "1.3"]]
  :resource-paths ["resources"]
   :release-tasks 
    [["vcs" "assert-committed"]
     ["change" "version" "leiningen.release/bump-version" "release"]
     ["vcs" "commit"]
     ["vcs" "tag" "--no-sign"]
     ; ["deploy" "vendredi"]
     ["deploy" "clojars"]
     ["change" "version" "leiningen.release/bump-version"]
     ["vcs" "commit"]
     ["vcs" "push"]]
  :eval-in-leiningen true)
