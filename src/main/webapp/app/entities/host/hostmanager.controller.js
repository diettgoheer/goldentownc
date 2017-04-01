(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('HostManagerController', HostManagerController);

    HostManagerController.$inject = ['$scope', '$state', 'Host', 'HostSearch'];

    function HostManagerController ($scope, $state, Host, HostSearch) {
        var vm = this;

        vm.hosts = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Host.query(function(result) {
                vm.hosts = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            HostSearch.query({query: vm.searchQuery}, function(result) {
                vm.hosts = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
