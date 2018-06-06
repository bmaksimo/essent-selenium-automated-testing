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
            this.result.reason += ': The Javascript class ' + this.id + ' does not have a run method';
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
            subMenu: [],
            angularComponents: [],
            /*angularAppController: angular.element("dashboard").scope().$parent.dwpAppController*/
        };
        this.state.angularComponents['top-actions'] = this.getAngularComponentState($('top-actions'));
        $('main-menu-link').each(function(i) {
            let attrs = self.getElementAttributes($(this));
            attrs['link-id'] = $(this).find('a').attr('id');
            self.state.mainMenu.push(attrs);
        });
        $('sub-menu-link').each(function(i) {
            let attrs = self.getElementAttributes($(this));
            attrs['link-id'] = $(this).find('a').attr('id');
            self.state.subMenu.push(attrs);
        });

        return this.state;
    }

    getFormData($form) {
        let self = this;
        let formElements = [];

        $form.find('select-form-element').each(function(i) {
            let $select = $(this).find('select');
            let $options = $select.find('option');
            let options = [];
            $options.each(function() {
                options.push(self.getElementAttributes($(this)))
            });
            let formElement = {
                angularComponent: self.getAngularComponentState($(this)),
                type: 'select',
                id: $select.attr('id'),
                value: $select.val(),
                label: $select.find('option:selected').text(),
                options: options,
                readOnly: false
            };
            if ($select.size() === 0) {
                let $readOnlyEl = $(this).find('.non-editable-input .ng-binding');
                formElement.readOnly = true;
                formElement.id = $readOnlyEl.attr('id');
                formElement.label = $readOnlyEl.text();
            }
            formElements.push(formElement);
        });

        $form.find('input-form-element').each(function(i) {
            let $input = $(this).find('input');
            let formElement = {
                angularComponent: self.getAngularComponentState($(this)),
                type: 'input',
                id: $input.attr('id'),
                value: $input.val(),
                readOnly: false
            };
            if ($input.size() === 0) {
                let $readOnlyEl = $(this).find('.non-editable-input .ng-binding');
                formElement.readOnly = true;
                formElement.id = $readOnlyEl.attr('id');
                formElement.value = $readOnlyEl.text();
            }
            formElements.push(formElement);
        });

        $form.find('datepicker-form-element').each(function(i) {
            let componentProps = self.getAngularComponentState($(this));
            $(this).find('input').each(function(i,input){
                let $input = $(input);
                let formElement = {
                    angularComponent: componentProps,
                    type: 'input',
                    id: $input.attr('id'),
                    value: $input.val(),
                    readOnly: false
                };
                formElements.push(formElement);
            });
            if ($(this).find('input').size() === 0) {
                let $readOnlyEl = $(this).find('.non-editable-input .ng-binding');
                let formElement = {
                    angularComponent: componentProps,
                    type: 'input',
                    id: $readOnlyEl.attr('id'),
                    value: $readOnlyEl.text(),
                    readOnly: true
                };
                formElements.push(formElement);
            }
        });

        $form.find('toggle-form-element').each(function(i) {
            let $input = $(this).find('input');
            let value = "off";
            if ($input.hasClass('ng-not-empty')) {
                value = "on"
            }
            let formElement = {
                angularComponent: self.getAngularComponentState($(this)),
                type: 'input',
                id: $input.attr('id'),
                value: value,
                readOnly: false
            };
            /* todo: check for readonly status */
            formElements.push(formElement);
        });

        $form.find('select-with-search-form-element').each(function(i) {
            let formElement = {
                angularComponent: self.getAngularComponentState($(this)),
                type: 'input',
                id: $(this).attr('id'),
                value: [],
                readOnly: false
            };
            $(this).find('.action-list ul li').each((ii, li)=> {
                formElement.value.push($(li).find('span')[0].innerText)
            });
            /* todo: check for readonly status */
            formElements.push(formElement);
        });

        $form.find('address-form-element').each(function(i) {
            let id = $(this).attr('id');
            let formElement = {
                angularComponent: self.getAngularComponentState($(this)),
                type: 'multiple',
                id: id,
                value: [],
                readOnly: false
            };
            $(this).find('fieldset').children().each((ii, subEl)=> {
                let type = $(subEl).prop("tagName").toLowerCase();
                if (type === 'br' || type === 'autocomplete') {
                    return;
                }
                let formSubElement = {
                    type: $(subEl).prop("tagName").toLowerCase(),
                    id: $(subEl).attr('field-id'),
                    componentLabel: $(subEl).attr('placeholder'),
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
            /* todo: check for readonly status */
            formElements.push(formElement);
        });

        return formElements;
    }

    getAngularComponentState($componentElement) {
        if (!$componentElement.size()) {
            return {'ERROR': '$componentElement not found'};
        }
        let component = {
            tag: $componentElement.prop("tagName").toLowerCase(),
        };
        if (component.tag.match('form-element')) {
            component.name = $componentElement.attr('name');
            component.label = $componentElement.parent().parent().parent().parent().find('label').html();
        }
        if (component.tag === 'top-actions') {
            component.miniGuidanceCanBeOpened = !$componentElement.find("[ng-show*='miniGuidanceCanBeOpened']").hasClass('ng-hide');
            component.filtersCanBeOpened = !$componentElement.find("[ng-show*='filtersCanBeOpened']").hasClass('ng-hide');
            component.plusMenuCanBeOpened = !$componentElement.find("[ng-show*='plusMenuCanBeOpened']").hasClass('ng-hide');
            component.primaryButtonIsVisible = !$componentElement.find("[ng-show*='primaryButtonIsVisible']").hasClass('ng-hide');
        }

        return component;
    }

}
