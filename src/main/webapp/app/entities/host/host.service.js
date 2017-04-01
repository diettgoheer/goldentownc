(function() {
    'use strict';
    angular
        .module('goldentown2App')
        .factory('Host', Host);

    Host.$inject = ['$resource', 'DateUtils'];

    function Host ($resource, DateUtils) {
        var resourceUrl =  'api/hosts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.birthday = DateUtils.convertDateTimeFromServer(data.birthday);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
