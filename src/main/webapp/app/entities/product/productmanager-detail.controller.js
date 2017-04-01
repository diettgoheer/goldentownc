(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('ProductManagerDetailController', ProductManagerDetailController);

    ProductManagerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Product', 'User'];

    function ProductManagerDetailController($scope, $rootScope, $stateParams, previousState, entity, Product, User) {
        var vm = this;

        vm.product = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('goldentown2App:productUpdate', function(event, result) {
            vm.product = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
