(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .factory('HostSearch', HostSearch);

    HostSearch.$inject = ['$resource'];

    function HostSearch($resource) {
        var resourceUrl =  'api/_search/hosts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
