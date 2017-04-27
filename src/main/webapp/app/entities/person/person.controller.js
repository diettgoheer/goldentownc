(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('PersonController', PersonController);

    PersonController.$inject = ['$scope', '$state', 'Person', 'PersonSearch'];

    function PersonController ($scope, $state, Person, PersonSearch) {
        var vm = this;

        vm.people = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Person.query(function(result) {
                vm.people = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PersonSearch.query({query: vm.searchQuery}, function(result) {
                vm.people = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
