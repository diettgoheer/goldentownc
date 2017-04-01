(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .factory('DealSearch', DealSearch);

    DealSearch.$inject = ['$resource'];

    function DealSearch($resource) {
        var resourceUrl =  'api/_search/deals/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
