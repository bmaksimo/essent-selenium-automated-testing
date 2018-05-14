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

    getJquerySelector($element) {
        let selector = $element
            .parents()
            .map(function() { return this.tagName; })
            .get()
            .reverse()
            .concat([this.nodeName])
            .join(">");
        let id = $element.attr("id");
        if (id) {
            selector += "#"+ id;
        }
        let classNames = $element.attr("class");
        if (classNames) {
            selector += "." + $.trim(classNames).replace(/\s/gi, ".");
        }
        return selector;
    }

}

/*
  todo: move to separate js file
*/

class TestRunnerDwp extends TestRunnerBase {

    constructor(options, callback, timeout = 100) {
        super(options, callback, timeout)
    }

    getState() {
        const self = this;
        let state = {
            angularComponents: this.getAngularJsComponents(true),
            currentRoute: location.hash,
            mainMenu: [],
            subMenu: [],
        };
        $('main-menu-link').each(function(i) {
            let attrs = self.getElementAttributes($(this));
            attrs['link-id'] = $(this).find('a').attr('id');
            state.mainMenu.push(attrs);
        });
        $('sub-menu-link').each(function(i) {
            let attrs = self.getElementAttributes($(this));
            attrs['link-id'] = $(this).find('a').attr('id');
            state.subMenu.push(attrs);
        });
        return state;
    }

    getAngularComponent($componentElement, jsonEncode = false) {
        if (!$componentElement.size()) {
            return {'ERROR': '$componentElement not found'};
        }
        let component = {
            $element: $componentElement,
            $scope: $componentElement.scope(),
            scope: {},
            data: {},
            tag: $componentElement.prop("tagName").toLowerCase(),
            selector: this.getJquerySelector($componentElement)
        };

        if (component.tag.match('form-element')) {
            component.data.id = component.$scope.id;
            component.data.name = component.$scope.name;
            component.data.label = $componentElement.parent().parent().parent().parent().find('label').html();
            /*console.log(component);*/
        }
        if (component.tag === 'top-actions') {
            component.data.miniGuidanceCanBeOpened = !$componentElement.find("[ng-show*='miniGuidanceCanBeOpened']").hasClass('ng-hide');
            component.data.filtersCanBeOpened = !$componentElement.find("[ng-show*='filtersCanBeOpened']").hasClass('ng-hide');
            component.data.plusMenuCanBeOpened = !$componentElement.find("[ng-show*='plusMenuCanBeOpened']").hasClass('ng-hide');
            component.data.primaryButtonIsVisible = !$componentElement.find("[ng-show*='primaryButtonIsVisible']").hasClass('ng-hide');
            /*console.log(component);*/
        }

        if(jsonEncode === true) {
            return this.jsonEncodeComponent(component);
        } else {
            component.scope = component.$scope;
            return component;
        }

    }

    getAngularJsComponents(jsonEncode = false) {
        let self = this;
        let components = [];
        $('*').each((i,el) => {
            let $scope = $(el).scope();
            if($scope) {
                let component = self.getAngularComponent($(el), jsonEncode);
                if(!$.isEmptyObject(component.scope || !$.isEmptyObject(component.data))) {
                    components.push(self.getAngularComponent($(el), jsonEncode))
                }
            }
        });
        return components;
    }

    jsonEncodeComponent(component) {
        let componentSerialized = {};
        Object.keys(component).forEach((k) => {
            if (!k.startsWith('$')) {
                componentSerialized[k] = component[k];
            }
        });
        Object.keys(component.$scope).forEach((k) => {
            if (!k.startsWith('$')
                && !k.endsWith('Controller')
                && k !== 'fc'
                && k !== 'form'
                && k !== 'fields'
                && k !== 'fieldGroup'
                && k !== 'loginForm'
                && k !== 'options'
                && k !== 'translateNamespace'
                && k !== 'theFormlyForm'
                && !k.match('formly')) {
                /*
                console.log('--------');
                console.log(componentSerialized.tag, ': ' + k + '=');
                */
                componentSerialized.scope[k] = JSON.parse(JSON.stringify(component.$scope[k]));
            }
        });
        return componentSerialized;
    }

    setFormData($form, newModel) {
        let $scope = this.getAngularComponent($form).$scope;
        let model = $scope.basicFormlyFormController.model;
        $scope.$apply(() => {
            angular.extend(model, newModel);
        });
    }

    getFormData($form, jsonEncode = false) {
        let self = this;
        let controller = self.getAngularComponent($form).$scope.basicFormlyFormController;
        let data = {
            formComponent: self.getAngularComponent($form, true),
            formElements: [],
            formController: {},
            formValid: controller.form.$valid,
            selector: this.getJquerySelector($form)
        };
        if (jsonEncode) {
            let controllerCopy = angular.fromJson(angular.toJson(controller));
            let fields = controllerCopy.fields;
            Object.keys(controllerCopy).forEach((k, i) => {
                if (!k.startsWith('$') && k !== 'form' && k !== 'fields' && k !== 'guidanceObserversAccessor') {
                    data.formController[k] = controller[k];
                }
            });
            fields.forEach((field) => {
                field.form = undefined;
                field.options = undefined;
            });
            data.formController.fields = fields;
            data.formController =  angular.fromJson(angular.toJson(data.formController));
        }else {
            data.formController = controller
        }

        let formElements = data.formElements;

        $form.find('select-form-element').each(function(i) {
            let $select = $(this).find('select');
            let $options = $select.find('option');
            let options = [];
            $options.each(function() {
                options.push(self.getElementAttributes($(this)))
            });
            let formElement = {
                angularComponent: self.getAngularComponent($(this), true),
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
                angularComponent: self.getAngularComponent($(this), true),
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
            let componentProps = self.getAngularComponent($(this), true);
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
                angularComponent: self.getAngularComponent($(this), true),
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
                angularComponent: self.getAngularComponent($(this), true),
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
                angularComponent: self.getAngularComponent($(this), true),
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

        return data;
    }

}
