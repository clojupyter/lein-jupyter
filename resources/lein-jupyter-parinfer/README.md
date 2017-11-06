# parinfer extension for jupyter notebook

This is a quick and dirty extension for jupyter notebook.  The
extension itself is enterily contained into `index.js` but depends
on `parinder-codemirror.js` and `parinfer.js`.  The only changed
done on the dependencies is that `parinder-codemirror.js` depends
here on `./parinfer` and not on `parinfer`.  The parinfer codemirror
extension used here is `d28cec6d4203f47a99b2b57` and can be found at
https://github.com/shaunlebron/parinfer-codemirror.

### installing
To install a extension, you will need the
`jupyter nbextension install lein-jupyter-parinfer --user` command
to install the extension.  Then, you will need to enable the extension
using the `jupyter nbextension enable lein-jupyter-parinfer/index --user`
command.

### hacking
The easiest way to hack your way around is to uninstall the extension and
then to run it as a custom script.  To do so, move the code in the
`$HOME/.jupyter/custom` directory and rename `index.js` to `custom.js`.


### acknowledgment

* parinfer
* parinfer code mirror
* jupyter notebook

They are all amazing projects.  Nothing under.