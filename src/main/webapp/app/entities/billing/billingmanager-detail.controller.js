(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('BillingManagerDetailController', BillingManagerDetailController);

    BillingManagerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Billing', 'Deal', 'User', 'Product'];

    function BillingManagerDetailController($scope, $rootScope, $stateParams, previousState, entity, Billing, Deal, User, Product) {
        var vm = this;

        vm.billing = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('goldentown2App:billingUpdate', function(event, result) {
            vm.billing = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
