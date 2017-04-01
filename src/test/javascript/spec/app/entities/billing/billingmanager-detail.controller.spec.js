'use strict';

describe('Controller Tests', function() {

    describe('Billing Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBilling, MockDeal, MockUser, MockProduct;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBilling = jasmine.createSpy('MockBilling');
            MockDeal = jasmine.createSpy('MockDeal');
            MockUser = jasmine.createSpy('MockUser');
            MockProduct = jasmine.createSpy('MockProduct');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Billing': MockBilling,
                'Deal': MockDeal,
                'User': MockUser,
                'Product': MockProduct
            };
            createController = function() {
                $injector.get('$controller')("BillingManagerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'goldentown2App:billingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
