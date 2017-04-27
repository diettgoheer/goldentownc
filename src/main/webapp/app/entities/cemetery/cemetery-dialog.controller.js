(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('CemeteryDialogController', CemeteryDialogController);

    CemeteryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cemetery'];

    function CemeteryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cemetery) {
        var vm = this;

        vm.cemetery = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cemeteries = Cemetery.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cemetery.id !== null) {
                Cemetery.update(vm.cemetery, onSaveSuccess, onSaveError);
            } else {
                Cemetery.save(vm.cemetery, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('goldentown2App:cemeteryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.birthday = false;
        vm.datePickerOpenStatus.deathday = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
