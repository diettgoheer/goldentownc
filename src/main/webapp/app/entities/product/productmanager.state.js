(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('productmanager', {
            parent: 'entity',
            url: '/productmanager',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.product.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product/productsmanager.html',
                    controller: 'ProductManagerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('product');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('productmanager-detail', {
            parent: 'entity',
            url: '/productmanager/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.product.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product/productmanager-detail.html',
                    controller: 'ProductManagerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('product');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Product', function($stateParams, Product) {
                    return Product.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'productmanager',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('productmanager-detail.edit', {
            parent: 'productmanager-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product/productmanager-dialog.html',
                    controller: 'ProductManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Product', function(Product) {
                            return Product.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('productmanager.new', {
            parent: 'productmanager',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product/productmanager-dialog.html',
                    controller: 'ProductManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                readme: null,
                                tag: null,
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('productmanager', null, { reload: 'productmanager' });
                }, function() {
                    $state.go('productmanager');
                });
            }]
        })
        .state('productmanager.edit', {
            parent: 'productmanager',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product/productmanager-dialog.html',
                    controller: 'ProductManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Product', function(Product) {
                            return Product.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('productmanager', null, { reload: 'productmanager' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('productmanager.delete', {
            parent: 'productmanager',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product/productmanager-delete-dialog.html',
                    controller: 'ProductManagerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Product', function(Product) {
                            return Product.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('productmanager', null, { reload: 'productmanager' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
