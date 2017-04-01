(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('AgentManagerDetailController', AgentManagerDetailController);

    AgentManagerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Agent', 'Product', 'User'];

    function AgentManagerDetailController($scope, $rootScope, $stateParams, previousState, entity, Agent, Product, User) {
        var vm = this;

        vm.agent = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('goldentown2App:agentUpdate', function(event, result) {
            vm.agent = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
