(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('CemeteryDetailController', CemeteryDetailController);

    CemeteryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cemetery'];

    function CemeteryDetailController($scope, $rootScope, $stateParams, previousState, entity, Cemetery) {
        var vm = this;

        vm.cemetery = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('goldentown2App:cemeteryUpdate', function(event, result) {
            vm.cemetery = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
