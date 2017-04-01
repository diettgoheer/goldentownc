(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('BoardManagerDialogController', BoardManagerDialogController);

    BoardManagerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Board'];

    function BoardManagerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Board) {
        var vm = this;

        vm.board = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.board.id !== null) {
                Board.update(vm.board, onSaveSuccess, onSaveError);
            } else {
                Board.save(vm.board, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('goldentown2App:boardUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.productionDate = false;
        vm.datePickerOpenStatus.purchaseTime = false;
        vm.datePickerOpenStatus.time = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
