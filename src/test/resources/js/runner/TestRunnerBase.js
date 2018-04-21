class TestRunnerBase {

    constructor(options, callback, timeout = 100) {
        this.id = this.constructor.name;
        this.options = options;
        this.callback = callback;
        this.result = {
            status: 'UNDEFINED',
            reason: 'Not executed',
            data: undefined
        };
        if(!this.run) {
            this.result.status = 'FAILED';
            this.result.reason += ': The Javascript class ' + this.id + ' must have a run method';
            this.resolveCallback();
            return;
        }
        setTimeout(() => {
            this.run();
        }, timeout);
    }

    resolveCallback(result) {
        if (this.callback) {
            this.callback(result);
        }
        console.log('result: ' + this.constructor.name);
        console.log(result);
        console.log(' ');
    }

    getElementAttributes($node) {
        let attrs = {};
        $.each($node[0].attributes, function(index, attribute) {
            attrs[attribute.name] = attribute.value;
        });
        return attrs;
    }


    /*
    todo: move all DWP specific functions to separate class
    */

    getState() {
        const self = this;
        this.state = {
            currentRoute: location.hash,
            mainMenu: [],
            subMenu: []
        };
        angular.element('main-menu-link').each(function(i) {
            let attrs = self.getElementAttributes($(this));
            attrs['link-id'] = $(this).find('a').attr('id');
            self.state.mainMenu.push(attrs);
        });
        angular.element('sub-menu-link').each(function(i) {
            let attrs = self.getElementAttributes($(this));
            attrs['link-id'] = $(this).find('a').attr('id');
            self.state.subMenu.push(attrs);
        });
    }

    getFormData($form) {
        let $el = $form;
        let self = this;
        let formElements = [];
        let $fields = $('div[formly-field]');
        $el.find('select-form-element').each(function(i) {
            let $select = $(this).find('select');
            let $options = $select.find('option');
            let label = $(this).parent().parent().parent().parent().find('label').html();
            let options = [];
            $options.each(function() {
                options.push(self.getElementAttributes($(this)))
            });
            formElements.push({
                type: 'select',
                id: $select.attr('id'),
                label: label,
                options: options,
            })
        });
        $el.find('input-form-element').each(function(i) {
            let $input = $(this).find('input');
            let label = $(this).parent().parent().parent().parent().find('label').html();
            formElements.push({
                type: 'input',
                id: $input.attr('id'),
                label: label,
                value: $input.val()
            })
        });
        $el.find('datepicker-form-element input').each(function(i) {
            let $input = $(this);
            /* fixme: get label value */
            let label = $(this).parent().parent().parent().parent().find('label').html();
            formElements.push({
                type: 'input',
                id: $input.attr('id'),
                label: label,
                value: $input.val()
            })
         });
         $el.find('toggle-form-element').each(function(i) {
            let $input = $(this).find('input');
            let label = $(this).parent().parent().parent().parent().find('label').html();
            formElements.push({
                type: 'input',
                id: $input.attr('id'),
                label: label,
                value: $input.val()
            })
        });

        $el.find('select-with-search-form-element').each(function(i) {

        });
        return formElements;
    }

    /**
     *
     * @param xpath - valid Xpath expression
     * @returns {Array}
     */
    evaluateXpath (xpath) {
        let iterator = document.evaluate(xpath, document, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
        let elements = [];
        try {
            let thisNode = iterator.iterateNext();
            while (thisNode) {
                elements.push(thisNode);
                thisNode = iterator.iterateNext();
            }
            return elements;
        }
        catch (e) {
            console.log('Error: Document tree modified during iteration ' + e);
            return [];
        }
    };

}
