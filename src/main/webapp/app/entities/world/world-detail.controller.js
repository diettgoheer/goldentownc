(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('WorldDetailController', WorldDetailController);

    WorldDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'World'];

    function WorldDetailController($scope, $rootScope, $stateParams, previousState, entity, World) {
        var vm = this;

        vm.world = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('goldentown2App:worldUpdate', function(event, result) {
            vm.world = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
