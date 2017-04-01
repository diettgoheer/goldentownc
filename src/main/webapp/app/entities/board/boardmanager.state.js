(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('boardmanager', {
            parent: 'entity',
            url: '/boardmanager',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.board.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/board/boardsmanager.html',
                    controller: 'BoardManagerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('board');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('boardmanager-detail', {
            parent: 'entity',
            url: '/boardmanager/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.board.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/board/boardmanager-detail.html',
                    controller: 'BoardManagerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('board');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Board', function($stateParams, Board) {
                    return Board.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'boardmanager',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('boardmanager-detail.edit', {
            parent: 'boardmanager-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/board/boardmanager-dialog.html',
                    controller: 'BoardManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Board', function(Board) {
                            return Board.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('boardmanager.new', {
            parent: 'boardmanager',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/board/boardmanager-dialog.html',
                    controller: 'BoardManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                type: null,
                                productionDate: null,
                                purchaseTime: null,
                                productionPlace: null,
                                purchasePlace: null,
                                provider: null,
                                process: null,
                                time: null,
                                place: null,
                                surrounding: null,
                                teaPerson: null,
                                drinkingPerson: null,
                                mood: null,
                                teaSet: null,
                                teaPic: null,
                                packagePic: null,
                                storageMethod: null,
                                shape: null,
                                aroma: null,
                                hotAroma: null,
                                teaWash: null,
                                boilingAroma: null,
                                cupAroma: null,
                                firstBrewPic: null,
                                firstBrewingSoup: null,
                                firstBrewAroma: null,
                                firstBrewMood: null,
                                secondBrewPic: null,
                                secondBrewSoup: null,
                                secondBrewAroma: null,
                                secondBrewMood: null,
                                thirdBrewPic: null,
                                thirdBrewSoup: null,
                                thirdBrewAroma: null,
                                thirdBrewMood: null,
                                brewTimes: null,
                                comment: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('boardmanager', null, { reload: 'boardmanager' });
                }, function() {
                    $state.go('boardmanager');
                });
            }]
        })
        .state('boardmanager.edit', {
            parent: 'boardmanager',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/board/boardmanager-dialog.html',
                    controller: 'BoardManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Board', function(Board) {
                            return Board.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('boardmanager', null, { reload: 'boardmanager' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('boardmanager.delete', {
            parent: 'boardmanager',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/board/boardmanager-delete-dialog.html',
                    controller: 'BoardManagerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Board', function(Board) {
                            return Board.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('boardmanager', null, { reload: 'boardmanager' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
