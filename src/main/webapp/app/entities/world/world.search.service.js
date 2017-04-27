(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .factory('WorldSearch', WorldSearch);

    WorldSearch.$inject = ['$resource'];

    function WorldSearch($resource) {
        var resourceUrl =  'api/_search/worlds/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
