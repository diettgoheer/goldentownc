(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('HostManagerDetailController', HostManagerDetailController);

    HostManagerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Host', 'User'];

    function HostManagerDetailController($scope, $rootScope, $stateParams, previousState, entity, Host, User) {
        var vm = this;

        vm.host = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('goldentown2App:hostUpdate', function(event, result) {
            vm.host = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
