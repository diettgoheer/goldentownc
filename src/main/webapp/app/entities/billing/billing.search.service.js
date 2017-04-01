(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .factory('BillingSearch', BillingSearch);

    BillingSearch.$inject = ['$resource'];

    function BillingSearch($resource) {
        var resourceUrl =  'api/_search/billings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
