(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .controller('BoardManagerDetailController', BoardManagerDetailController);

    BoardManagerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Board'];

    function BoardManagerDetailController($scope, $rootScope, $stateParams, previousState, entity, Board) {
        var vm = this;

        vm.board = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('goldentown2App:boardUpdate', function(event, result) {
            vm.board = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
