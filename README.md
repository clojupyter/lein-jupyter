# lein-jupyter

A Leiningen plugin to integrate with [jupyter notebook](http://jupyter.org/)

## How to use?

### Dependencies

You will need to have [leiningen](https://leiningen.org/) and
[jupyter notebook](http://jupyter.org/) installed globally.  The development
has been done under ubuntu linux with jupyter 4.3.0.


### Prose

Jars are not yet on clojars.  So at first you need to build clojupyter
and lein-jupyter locally.  Then you can incluse lein-jupyter as a
leiningen plugin on any of your project.

The main command line entry is `lein jupyter`.  The first time you use
lein-jupyter, you will need to install the jupyter kernel using the
`lein jupyter install-kernel` command.  Then, you can use the
`lein jupyter notebook` to run the jupyter notebook.


### Algorithm

1.  `git clone https://github.com/didiercrunch/clojupyter.git`
2.  `cd clojupyter`
3.  `lein install`
4.  `cd -`
5.  `git clone https://github.com/didiercrunch/lein-jupyter.git`
6.  `cd lein-jupyter`
7.  `lein install`
8.  `cd -`
9.  `lein new app test-lein-jupyter`
10.  `cd test-lein-jupyter`
11.  Add `:plugins [[lein-jupyter "0.1.0"]]` to the `project.clj` 
12.  `lein jupyter install-kernel`
13.  `lein jupyter notebook`
14.  Use the *lein-clojure* kernel.
