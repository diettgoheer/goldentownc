(function() {
    'use strict';
    angular
        .module('goldentown2App')
        .factory('World', World);

    World.$inject = ['$resource', 'DateUtils'];

    function World ($resource, DateUtils) {
        var resourceUrl =  'api/worlds/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.time = DateUtils.convertDateTimeFromServer(data.time);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
