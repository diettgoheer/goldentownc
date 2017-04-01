(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('hostmanager', {
            parent: 'entity',
            url: '/hostmanager',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.host.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/host/hostsmanager.html',
                    controller: 'HostManagerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('host');
                    $translatePartialLoader.addPart('hostType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('hostmanager-detail', {
            parent: 'entity',
            url: '/hostmanager/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.host.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/host/hostmanager-detail.html',
                    controller: 'HostManagerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('host');
                    $translatePartialLoader.addPart('hostType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Host', function($stateParams, Host) {
                    return Host.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'hostmanager',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('hostmanager-detail.edit', {
            parent: 'hostmanager-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/host/hostmanager-dialog.html',
                    controller: 'HostManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Host', function(Host) {
                            return Host.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('hostmanager.new', {
            parent: 'hostmanager',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/host/hostmanager-dialog.html',
                    controller: 'HostManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                birthday: null,
                                telephone: null,
                                temperature: null,
                                credit: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('hostmanager', null, { reload: 'hostmanager' });
                }, function() {
                    $state.go('hostmanager');
                });
            }]
        })
        .state('hostmanager.edit', {
            parent: 'hostmanager',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/host/hostmanager-dialog.html',
                    controller: 'HostManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Host', function(Host) {
                            return Host.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('hostmanager', null, { reload: 'hostmanager' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('hostmanager.delete', {
            parent: 'hostmanager',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/host/hostmanager-delete-dialog.html',
                    controller: 'HostManagerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Host', function(Host) {
                            return Host.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('hostmanager', null, { reload: 'hostmanager' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
