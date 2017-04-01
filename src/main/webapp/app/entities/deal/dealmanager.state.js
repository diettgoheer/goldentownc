(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dealmanager', {
            parent: 'entity',
            url: '/dealmanager',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.deal.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/deal/dealsmanager.html',
                    controller: 'DealManagerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('deal');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('dealmanager-detail', {
            parent: 'entity',
            url: '/dealmanager/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.deal.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/deal/dealmanager-detail.html',
                    controller: 'DealManagerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('deal');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Deal', function($stateParams, Deal) {
                    return Deal.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dealmanager',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dealmanager-detail.edit', {
            parent: 'dealmanager-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/deal/dealmanager-dialog.html',
                    controller: 'DealManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Deal', function(Deal) {
                            return Deal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dealmanager.new', {
            parent: 'dealmanager',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/deal/dealmanager-dialog.html',
                    controller: 'DealManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                time: null,
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dealmanager', null, { reload: 'dealmanager' });
                }, function() {
                    $state.go('dealmanager');
                });
            }]
        })
        .state('dealmanager.edit', {
            parent: 'dealmanager',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/deal/dealmanager-dialog.html',
                    controller: 'DealManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Deal', function(Deal) {
                            return Deal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dealmanager', null, { reload: 'dealmanager' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dealmanager.delete', {
            parent: 'dealmanager',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/deal/dealmanager-delete-dialog.html',
                    controller: 'DealManagerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Deal', function(Deal) {
                            return Deal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dealmanager', null, { reload: 'dealmanager' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
