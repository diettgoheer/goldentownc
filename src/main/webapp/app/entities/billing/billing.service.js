(function() {
    'use strict';
    angular
        .module('goldentown2App')
        .factory('Billing', Billing);

    Billing.$inject = ['$resource', 'DateUtils'];

    function Billing ($resource, DateUtils) {
        var resourceUrl =  'api/billings/:id';

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
