# lein-jupyter
A Leiningen plugin to integrate with [jupyter notebook](http://jupyter.org/)
and [JupyterLab](https://github.com/jupyterlab/jupyterlab).

![jupiter](https://upload.wikimedia.org/wikipedia/commons/0/0a/Tango_Jupiter.svg)
[![Clojars Project](https://img.shields.io/clojars/v/nubank/lein-jupyter.svg)](https://clojars.org/nubank/lein-jupyter)

## Features

* Per project kernel!
* Parinfer integration!  Check out the new parinfer button in the menu bar
(jupyter notebook only).
* Made with love



## How to use?

### Dependencies

You will need to have [leiningen](https://leiningen.org/) and
[jupyter notebook](http://jupyter.org/) installed globally.  The development
has been done under ubuntu linux with jupyter 4.3.0.

JupyterLab is a [separate install](https://jupyterlab.readthedocs.io/en/stable/getting_started/installation.html)
which will need to be performed to use the `lein jupyter lab` command.

### Installation



1. `lein-jupyter` is a simple leiningen plugin.  Hence you can add the `[lein-jupyter "0.1.18"]`
    vector in your `:plugins` list in your `project.clj`.  A simple project.clj might look
    like

    ```    
    (defproject my-project "0.1.0-SNAPSHOT"
      :description "FIXME: write description"
      :url "http://example.com/FIXME"
      :license {:name "Eclipse Public License"
                :url "http://www.eclipse.org/legal/epl-v10.html"}
      :dependencies [[org.clojure/clojure "1.10.1"]]
      :main ^:skip-aot my-project.core
      :target-path "target/%s"
      :plugins [[lein-jupyter "0.1.18"]]
      :profiles {:uberjar {:aot :all}})
    ```
2.  The first time you use lein-jupyter, you will need to install the kernel.
    `lein jupyter install-kernel` is the command you need to run to install
    the kernel.  You should run the command once but nothing wrong should
    happen if you run it more than once.

#### Customization

All options can be set in the `:jupyter-options` section of your `project.clj`'s
`defproject`.  Bellow is the option list

| Option Name     | Description               | Example                              |
|:---------------:|:--------------------------|:-------------------------------------|
| `:jupyter-path` | the jupyter binary to use | `/home/ubuntu/anaconda3/bin/jupyter` |


### Using lein-jupyter


The main command line entry is `lein jupyter`.  The first time you use lein-jupyter,
you will need to install the jupyter kernel using the `lein jupyter install-kernel`
command.  Then, you can use the `lein jupyter notebook` to run the jupyter notebook,
or `lein jupyter lab` to run JupyterLab.
