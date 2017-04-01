(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('ProductManagerDeleteController',ProductManagerDeleteController);

    ProductManagerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Product'];

    function ProductManagerDeleteController($uibModalInstance, entity, Product) {
        var vm = this;

        vm.product = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Product.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
