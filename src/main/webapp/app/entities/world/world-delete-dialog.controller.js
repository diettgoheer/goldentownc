(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('WorldDeleteController',WorldDeleteController);

    WorldDeleteController.$inject = ['$uibModalInstance', 'entity', 'World'];

    function WorldDeleteController($uibModalInstance, entity, World) {
        var vm = this;

        vm.world = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            World.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
