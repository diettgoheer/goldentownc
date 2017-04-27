(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .factory('CemeterySearch', CemeterySearch);

    CemeterySearch.$inject = ['$resource'];

    function CemeterySearch($resource) {
        var resourceUrl =  'api/_search/cemeteries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
