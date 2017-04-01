(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('BoardManagerDeleteController',BoardManagerDeleteController);

    BoardManagerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Board'];

    function BoardManagerDeleteController($uibModalInstance, entity, Board) {
        var vm = this;

        vm.board = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Board.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
