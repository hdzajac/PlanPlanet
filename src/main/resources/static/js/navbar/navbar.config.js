(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .config(stateConfig);

    stateConfig.$inject = ['$routeProvider', '$httpProvider'];

    function stateConfig($routeProvider, $httpProvider) {

        $routeProvider.when('/', {
            templateUrl: 'home.html',
            controller: 'home',
            controllerAs: 'vm'
        }).when('/login', {
            templateUrl: 'login.html',
            controller: 'navigation',
            controllerAs: 'vm'
        }).when('/planDetails/:planId', {
            templateUrl: 'planDetails.html',
            controller: 'PlanDetails',
            controllerAs: 'vm'
        }).when('/plans', {
            templateUrl: 'generated_plans.html',
            controller: 'Plans',
            controllerAs: 'vm'
        }).when('/step2', {
            templateUrl: 'travel_form_preferences.html',
            controller: 'home',
            controllerAs: 'vm'
        }).when('/nowy', {
            templateUrl: 'TEST.html',
            controller: 'TESTcontr',
            controllerAs: 'vm'
        }).when('/profil', {
            templateUrl: 'userProfile.html',
            controller: 'profileCtrl',
            controllerAs: 'vm'
        }).when('/register', {
            templateUrl: 'newUserForm.html',
            controller: 'RegisterController',
            controllerAs: 'vm'
        }).otherwise('/');

        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    }
})();