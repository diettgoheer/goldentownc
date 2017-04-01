(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('HostManagerDeleteController',HostManagerDeleteController);

    HostManagerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Host'];

    function HostManagerDeleteController($uibModalInstance, entity, Host) {
        var vm = this;

        vm.host = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Host.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
