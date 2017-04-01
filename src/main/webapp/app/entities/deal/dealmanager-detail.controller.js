(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('DealManagerDetailController', DealManagerDetailController);

    DealManagerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Deal', 'User', 'Agent', 'Product'];

    function DealManagerDetailController($scope, $rootScope, $stateParams, previousState, entity, Deal, User, Agent, Product) {
        var vm = this;

        vm.deal = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('goldentown2App:dealUpdate', function(event, result) {
            vm.deal = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
