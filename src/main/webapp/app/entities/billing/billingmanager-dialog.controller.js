(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('BillingManagerDialogController', BillingManagerDialogController);

    BillingManagerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Billing', 'Deal', 'User', 'Product'];

    function BillingManagerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Billing, Deal, User, Product) {
        var vm = this;

        vm.billing = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.deals = Deal.query();
        vm.users = User.query();
        vm.products = Product.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.billing.id !== null) {
                Billing.update(vm.billing, onSaveSuccess, onSaveError);
            } else {
                Billing.save(vm.billing, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('goldentown2App:billingUpdate', result);
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
