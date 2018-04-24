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
            this.resolveCallback(this.result);
            return;
        }
        setTimeout(() => {
            this.run();
        }, timeout);
    }

    resolveCallback(result) {
        console.log(this.constructor.name + ' :: run');
        console.log('options: ');
        console.log(this.options);
        console.log('result: ');
        console.log(result);
        console.log(' ');
        if (this.callback) {
            this.callback(result);
        }
    }

    getElementAttributes($node) {
        let attrs = {};
        $.each($node[0].attributes, function(index, attribute) {
            attrs[attribute.name] = attribute.value;
        });
        return attrs;
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


    /*
    todo: move all DWP specific methods to separate class
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
        /*
        todo: get data for all read-only components
        */
        $el.find('select-form-element').each(function(i) {
            let $select = $(this).find('select');
            let $options = $select.find('option');
            let label = $(this).parent().parent().parent().parent().find('label').html();
            let options = [];
            $options.each(function() {
                options.push(self.getElementAttributes($(this)))
            });
            /* fixme: angular doesn't set selected property on change */
            formElements.push({
                type: 'select',
                id: $select.attr('id'),
                label: label,
                value: $select.val(),
                options: options,
                componentType: 'select-form-element',
            })
        });
        $el.find('input-form-element').each(function(i) {
            let $input = $(this).find('input');
            let label = $(this).parent().parent().parent().parent().find('label').html();
            formElements.push({
                type: 'input',
                id: $input.attr('id'),
                label: label,
                value: $input.val(),
                componentType: 'input-form-element',
            })
        });
        $el.find('datepicker-form-element').each(function(i) {
            let label = $(this).parent().parent().parent().parent().find('label').html();
            $(this).find('input').each(function(i){
                let $input = $(this);
                formElements.push({
                    type: 'input',
                    id: $input.attr('id'),
                    label: label,
                    value: $input.val(),
                    componentType: 'datepicker-form-element',
                })

            });
        });
        $el.find('toggle-form-element').each(function(i) {
            let $input = $(this).find('input');
            let label = $(this).parent().parent().parent().parent().find('label').html();
            let value = "off";
            if ($input.hasClass('ng-not-empty')) {
                value = "on"
            }
            formElements.push({
                type: 'input',
                id: $input.attr('id'),
                label: label,
                value: value,
                componentType: 'toggle-form-element',
            })
        });
        $el.find('select-with-search-form-element').each(function(i) {
            let label = $(this).parent().parent().parent().parent().find('label').html();
            let id = $(this).attr('id');
            let formElement = {
                type: 'input',
                id: id,
                label: label,
                value: [],
                componentType: 'select-with-search-form-element'
            };
            $(this).find('.action-list ul li').each((ii, li)=> {
                formElement.value.push($(li).find('span')[0].innerText)
            });
            formElements.push(formElement);
        });
        $el.find('address-form-element').each(function(i) {
            let label = $(this).parent().parent().parent().parent().find('label').html();
            let id = $(this).attr('id');
            let formElement = {
                type: 'multiple',
                id: id,
                label: label,
                value: [],
                componentType: 'address-form-element'
            };
            $(this).find('fieldset').children().each((ii, subEl)=> {
                let type = $(subEl).prop("tagName").toLowerCase();
                if (type === 'br' || type === 'autocomplete') {
                    return;
                }
                let formSubElement = {
                    type: $(subEl).prop("tagName").toLowerCase(),
                    id: $(subEl).attr('field-id'),
                    label: $(subEl).attr('placeholder'),
                    value: $(subEl).find('input').val(),
                    componentType: 'address-form-element-sub'
                };
                if (type === 'select') {
                    formSubElement.id = $(subEl).attr('id');
                    formSubElement.value = $(subEl).val();
                    let $options = $(subEl).find('option');
                    formSubElement.options = [];
                    $options.each(function(iii,optionEl) {
                        formSubElement.options.push(self.getElementAttributes($(optionEl)))
                    });
                }
                formElement.value.push(formSubElement)
            });
            formElements.push(formElement);
        });
        return formElements;
    }

}
