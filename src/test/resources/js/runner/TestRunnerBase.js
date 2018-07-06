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
            this.run(this.options, this.result);
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

