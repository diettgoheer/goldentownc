(function() {
    'use strict';
    angular
        .module('goldentown2App')
        .factory('Deal', Deal);

    Deal.$inject = ['$resource', 'DateUtils'];

    function Deal ($resource, DateUtils) {
        var resourceUrl =  'api/deals/:id';

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
