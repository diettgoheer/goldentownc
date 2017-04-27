(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cemetery', {
            parent: 'entity',
            url: '/cemetery',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.cemetery.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cemetery/cemeteries.html',
                    controller: 'CemeteryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cemetery');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cemetery-detail', {
            parent: 'entity',
            url: '/cemetery/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.cemetery.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cemetery/cemetery-detail.html',
                    controller: 'CemeteryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cemetery');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cemetery', function($stateParams, Cemetery) {
                    return Cemetery.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cemetery',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cemetery-detail.edit', {
            parent: 'cemetery-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cemetery/cemetery-dialog.html',
                    controller: 'CemeteryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cemetery', function(Cemetery) {
                            return Cemetery.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cemetery.new', {
            parent: 'cemetery',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cemetery/cemetery-dialog.html',
                    controller: 'CemeteryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                person: null,
                                age: null,
                                generation: null,
                                value: null,
                                lastValue: null,
                                birthday: null,
                                deathday: null,
                                isDead: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cemetery', null, { reload: 'cemetery' });
                }, function() {
                    $state.go('cemetery');
                });
            }]
        })
        .state('cemetery.edit', {
            parent: 'cemetery',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cemetery/cemetery-dialog.html',
                    controller: 'CemeteryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cemetery', function(Cemetery) {
                            return Cemetery.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cemetery', null, { reload: 'cemetery' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cemetery.delete', {
            parent: 'cemetery',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cemetery/cemetery-delete-dialog.html',
                    controller: 'CemeteryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cemetery', function(Cemetery) {
                            return Cemetery.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cemetery', null, { reload: 'cemetery' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
