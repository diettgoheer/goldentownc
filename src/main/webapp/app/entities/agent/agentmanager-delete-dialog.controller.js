(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('AgentManagerDeleteController',AgentManagerDeleteController);

    AgentManagerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Agent'];

    function AgentManagerDeleteController($uibModalInstance, entity, Agent) {
        var vm = this;

        vm.agent = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Agent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
