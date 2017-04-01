(function() {
    'use strict';

    angular
        .module('goldentown2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('agentmanager', {
            parent: 'entity',
            url: '/agentmanager',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.agent.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/agent/agentsmanager.html',
                    controller: 'AgentManagerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('agent');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('agentmanager-detail', {
            parent: 'entity',
            url: '/agentmanager/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'goldentown2App.agent.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/agent/agentmanager-detail.html',
                    controller: 'AgentManagerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('agent');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Agent', function($stateParams, Agent) {
                    return Agent.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'agentmanager',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('agentmanager-detail.edit', {
            parent: 'agentmanager-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agent/agentmanager-dialog.html',
                    controller: 'AgentManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Agent', function(Agent) {
                            return Agent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('agentmanager.new', {
            parent: 'agentmanager',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agent/agentmanager-dialog.html',
                    controller: 'AgentManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('agentmanager', null, { reload: 'agentmanager' });
                }, function() {
                    $state.go('agentmanager');
                });
            }]
        })
        .state('agentmanager.edit', {
            parent: 'agentmanager',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agent/agentmanager-dialog.html',
                    controller: 'AgentManagerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Agent', function(Agent) {
                            return Agent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('agentmanager', null, { reload: 'agentmanager' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('agentmanager.delete', {
            parent: 'agentmanager',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agent/agentmanager-delete-dialog.html',
                    controller: 'AgentManagerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Agent', function(Agent) {
                            return Agent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('agentmanager', null, { reload: 'agentmanager' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
