(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('billingmanager', {
            parent: 'entity',
            url: '/billingmanager',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.billing.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/billing/billingsmanager.html',
                    controller: 'BillingManagerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('billing');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('billingmanager-detail', {
            parent: 'entity',
            url: '/billingmanager/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.billing.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/billing/billingmanager-detail.html',
                    controller: 'BillingManagerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('billing');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Billing', function($stateParams, Billing) {
                    return Billing.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'billingmanager',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('billingmanager-detail.edit', {
            parent: 'billingmanager-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/billing/billingmanager-dialog.html',
                    controller: 'BillingManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Billing', function(Billing) {
                            return Billing.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('billingmanager.new', {
            parent: 'billingmanager',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/billing/billingmanager-dialog.html',
                    controller: 'BillingManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                price: null,
                                time: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('billingmanager', null, { reload: 'billingmanager' });
                }, function() {
                    $state.go('billingmanager');
                });
            }]
        })
        .state('billingmanager.edit', {
            parent: 'billingmanager',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/billing/billingmanager-dialog.html',
                    controller: 'BillingManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Billing', function(Billing) {
                            return Billing.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('billingmanager', null, { reload: 'billingmanager' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('billingmanager.delete', {
            parent: 'billingmanager',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/billing/billingmanager-delete-dialog.html',
                    controller: 'BillingManagerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Billing', function(Billing) {
                            return Billing.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('billingmanager', null, { reload: 'billingmanager' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
