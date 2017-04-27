(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('world', {
            parent: 'entity',
            url: '/world',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.world.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/world/worlds.html',
                    controller: 'WorldController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('world');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('world-detail', {
            parent: 'entity',
            url: '/world/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.world.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/world/world-detail.html',
                    controller: 'WorldDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('world');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'World', function($stateParams, World) {
                    return World.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'world',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('world-detail.edit', {
            parent: 'world-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/world/world-dialog.html',
                    controller: 'WorldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['World', function(World) {
                            return World.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('world.new', {
            parent: 'world',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/world/world-dialog.html',
                    controller: 'WorldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                time: null,
                                personCount: null,
                                birthCount: null,
                                deathCount: null,
                                maxGeneration: null,
                                averageValue: null,
                                averageAge: null,
                                maxValue: null,
                                maxAge: null,
                                worldValue: null,
                                worldAge: null,
                                midAge: null,
                                baseValue: null,
                                growRate: null,
                                legacyRate: null,
                                breedRate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('world', null, { reload: 'world' });
                }, function() {
                    $state.go('world');
                });
            }]
        })
        .state('world.edit', {
            parent: 'world',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/world/world-dialog.html',
                    controller: 'WorldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['World', function(World) {
                            return World.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('world', null, { reload: 'world' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('world.delete', {
            parent: 'world',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/world/world-delete-dialog.html',
                    controller: 'WorldDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['World', function(World) {
                            return World.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('world', null, { reload: 'world' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
