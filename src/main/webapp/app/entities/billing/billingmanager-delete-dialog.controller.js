(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('BillingManagerDeleteController',BillingManagerDeleteController);

    BillingManagerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Billing'];

    function BillingManagerDeleteController($uibModalInstance, entity, Billing) {
        var vm = this;

        vm.billing = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Billing.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
