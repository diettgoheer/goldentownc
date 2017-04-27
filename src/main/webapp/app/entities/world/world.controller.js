(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('WorldController', WorldController);

    WorldController.$inject = ['$scope', '$state', 'World', 'WorldSearch'];

    function WorldController ($scope, $state, World, WorldSearch) {
        var vm = this;

        vm.worlds = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            World.query(function(result) {
                vm.worlds = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            WorldSearch.query({query: vm.searchQuery}, function(result) {
                vm.worlds = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
