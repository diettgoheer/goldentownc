(function() {
    'use strict';
    angular
        .module('goldentown2App')
        .factory('Board', Board);

    Board.$inject = ['$resource', 'DateUtils'];

    function Board ($resource, DateUtils) {
        var resourceUrl =  'api/boards/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.productionDate = DateUtils.convertDateTimeFromServer(data.productionDate);
                        data.purchaseTime = DateUtils.convertDateTimeFromServer(data.purchaseTime);
                        data.time = DateUtils.convertDateTimeFromServer(data.time);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
