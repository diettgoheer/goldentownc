(function() {
    'use strict';
    angular
        .module('goldentown2App')
        .factory('Person', Person);

    Person.$inject = ['$resource', 'DateUtils'];

    function Person ($resource, DateUtils) {
        var resourceUrl =  'api/people/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.birthday = DateUtils.convertDateTimeFromServer(data.birthday);
                        data.deathday = DateUtils.convertDateTimeFromServer(data.deathday);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
