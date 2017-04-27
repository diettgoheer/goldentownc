(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('CemeteryDeleteController',CemeteryDeleteController);

    CemeteryDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cemetery'];

    function CemeteryDeleteController($uibModalInstance, entity, Cemetery) {
        var vm = this;

        vm.cemetery = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cemetery.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
