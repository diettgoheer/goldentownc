(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('AgentManagerDialogController', AgentManagerDialogController);

    AgentManagerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Agent', 'Product', 'User'];

    function AgentManagerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Agent, Product, User) {
        var vm = this;

        vm.agent = entity;
        vm.clear = clear;
        vm.save = save;
        vm.products = Product.query();
        vm.users = User.query();
        vm.agents = Agent.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.agent.id !== null) {
                Agent.update(vm.agent, onSaveSuccess, onSaveError);
            } else {
                Agent.save(vm.agent, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('goldentown2App:agentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
