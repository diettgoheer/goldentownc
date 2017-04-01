(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('DealManagerDialogController', DealManagerDialogController);

    DealManagerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Deal', 'User', 'Agent', 'Product'];

    function DealManagerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Deal, User, Agent, Product) {
        var vm = this;

        vm.deal = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.agents = Agent.query();
        vm.products = Product.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.deal.id !== null) {
                Deal.update(vm.deal, onSaveSuccess, onSaveError);
            } else {
                Deal.save(vm.deal, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('goldentown2App:dealUpdate', result);
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
