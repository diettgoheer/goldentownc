(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('WorldDialogController', WorldDialogController);

    WorldDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'World'];

    function WorldDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, World) {
        var vm = this;

        vm.world = entity;
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
            if (vm.world.id !== null) {
                World.update(vm.world, onSaveSuccess, onSaveError);
            } else {
                World.save(vm.world, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('goldentown2App:worldUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.time = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
