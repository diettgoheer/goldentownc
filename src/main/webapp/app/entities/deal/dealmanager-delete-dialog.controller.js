(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('DealManagerDeleteController',DealManagerDeleteController);

    DealManagerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Deal'];

    function DealManagerDeleteController($uibModalInstance, entity, Deal) {
        var vm = this;

        vm.deal = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Deal.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
