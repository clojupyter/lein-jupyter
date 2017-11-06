define([
        'base/js/namespace',
        'base/js/promises',
        'base/js/events',
        './parinfer',
        './parinfer-codemirror',
    ], function(Jupyter, promises, events, parinfer, parinferCodeMirror) {
    promises.app_initialized.then(function(appname) {
        if (appname !== 'NotebookApp') {
            return
        }
        if(Jupyter.notebook.kernel.name !== 'lein-clojure'){
            return;
        }

        var parinferButton = {label: 'parinfer | off',
                              id: 'lein-jupyter-parinfer-btn',
                              icon: 'fa-terminal',
                              parinferOn: false};

        parinferButton.turnOnParInfer = function(evt){
            var cells = Jupyter.notebook.get_cells();
            for(var i = 0; i < cells.length; i++){
                var cell = cells[i];
                if (cell.cell_type === 'code') {
                    if(cell.code_mirror.__parinfer__){
                        parinferCodeMirror.enable(cell.code_mirror);
                    }else{
                        parinferCodeMirror.init(cell.code_mirror);
                    }
                }
            }
        };

        parinferButton.turnOffParInfer = function(){
            var cells = Jupyter.notebook.get_cells();
            for(var i = 0; i < cells.length; i++){
                var cell = cells[i];
                if (cell.cell_type === 'code') {
                    parinferCodeMirror.disable(cell.code_mirror);
                }
            }
        };

        parinferButton.changeButtonText = function(event, newText){
            var $e = $('#' + parinferButton.id);
            $e.find('span').text(newText);
        }

        parinferButton.callback = function(evt){
            parinferButton.parinferOn = !parinferButton.parinferOn;
            if(parinferButton.parinferOn){
                parinferButton.turnOnParInfer();
                parinferButton.changeButtonText(evt, 'parinfer | on');
                console.log("parinfer activated");
            }else{
                parinferButton.turnOffParInfer();
                parinferButton.changeButtonText(evt, 'parinfer | off');
                console.log("parinfer desactivated");
            }
            return true;
        };

        events.on('create.Cell', function(evt, nbcell){
            var cell = nbcell.cell;
            if (cell.cell_type === 'code') {
                parinferCodeMirror.init(cell.code_mirror);
                if(!parinferButton.parinferOn){
                    parinferCodeMirror.disable(cell.code_mirror);
                }
            }
        });
        Jupyter.toolbar.add_buttons_group([parinferButton]);

    });
});
