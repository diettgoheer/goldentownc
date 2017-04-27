(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('CemeteryController', CemeteryController);

    CemeteryController.$inject = ['$scope', '$state', 'Cemetery', 'CemeterySearch'];

    function CemeteryController ($scope, $state, Cemetery, CemeterySearch) {
        var vm = this;

        vm.cemeteries = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Cemetery.query(function(result) {
                vm.cemeteries = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CemeterySearch.query({query: vm.searchQuery}, function(result) {
                vm.cemeteries = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
