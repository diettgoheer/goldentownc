(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('ProductManagerController', ProductManagerController);

    ProductManagerController.$inject = ['$scope', '$state', 'Product', 'ProductSearch'];

    function ProductManagerController ($scope, $state, Product, ProductSearch) {
        var vm = this;

        vm.products = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Product.query(function(result) {
                vm.products = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProductSearch.query({query: vm.searchQuery}, function(result) {
                vm.products = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
