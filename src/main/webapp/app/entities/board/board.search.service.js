(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .factory('BoardSearch', BoardSearch);

    BoardSearch.$inject = ['$resource'];

    function BoardSearch($resource) {
        var resourceUrl =  'api/_search/boards/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
