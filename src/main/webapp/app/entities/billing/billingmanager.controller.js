(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('BillingManagerController', BillingManagerController);

    BillingManagerController.$inject = ['$scope', '$state', 'Billing', 'BillingSearch'];

    function BillingManagerController ($scope, $state, Billing, BillingSearch) {
        var vm = this;

        vm.billings = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Billing.query(function(result) {
                vm.billings = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            BillingSearch.query({query: vm.searchQuery}, function(result) {
                vm.billings = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
