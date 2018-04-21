class TestRunner {

    constructor(id, options, callback) {
      this.id = id;
      this.result = {
       status: 'UNDEFINED',
       reason: 'Not executed',
       data: undefined
      }
      this.run(id, options, callback);
    }

    run(id, options, callback) {
        this[id].call(this, id, options, callback);
    }

    getState() {
        var self = this;
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
        var $el = $form;
        var self = this;
        var formElements = [];
        var $fields = $('div[formly-field]');
        $el.find('select-form-element').each(function(i) {
            var $select = $(this).find('select');
            var $options = $select.find('option');
            var label = $(this).parent().parent().parent().parent().find('label').html();
            var options = [];
            $options.each(function() {
                options.push(self.getElementAttributes($(this)))
            })
            formElements.push({
                type: 'select',
                id: $select.attr('id'),
                label: label,
                options: options,
            })
        });
        $el.find('input-form-element').each(function(i) {
            var $input = $(this).find('input');
            var label = $(this).parent().parent().parent().parent().find('label').html();
            formElements.push({
                type: 'input',
                id: $input.attr('id'),
                label: label,
                value: $input.val()
            })
        });
        $el.find('datepicker-form-element').each(function(i) {

        });
        $el.find('toggle-form-element').each(function(i) {

        });
        $el.find('select-with-search-form-element').each(function(i) {

        });
        return formElements;
    }

    getElementAttributes($node) {
        var attrs = {};
        $.each($node[0].attributes, function(index, attribute) {
            attrs[attribute.name] = attribute.value;
        });
        return attrs;
    }
}
