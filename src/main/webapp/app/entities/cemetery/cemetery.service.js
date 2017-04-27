(function() {
    'use strict';
    angular
        .module('goldentown2App')
        .factory('Cemetery', Cemetery);

    Cemetery.$inject = ['$resource', 'DateUtils'];

    function Cemetery ($resource, DateUtils) {
        var resourceUrl =  'api/cemeteries/:id';

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
