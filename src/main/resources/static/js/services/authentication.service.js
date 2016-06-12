/**
 * Created by Roksana on 2016-06-08.
 */
(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .factory('AuthenticationService', AuthenticationService);

    AuthenticationService.$inject = ['$rootScope', '$http', 'SessionService'];

    function AuthenticationService($rootScope, $http, SessionService) {
        var service = {
            currentUser: currentUser,
            login: login,
            logout: logout,
            isAuthenticated: isAuthenticated
        };
        return service;

        function currentUser(successCallback, errorCallback) {
            return SessionService.current(successCallback, errorCallback);
        }

        function login(credentials, callback) {
            console.log(credentials);
            $http.post('user_login', credentials).then(function (result) {
                if (result.data.loggedIn) {
                    $rootScope.authenticated = true;
                    console.log("in authenticate " + result.data.user.login);
                    SessionService.create(result.data.user)
                } else {
                    $rootScope.authenticated = false;
                    console.log("in authenticate data in null")
                }
                callback(SessionService.current() != null);

            });
        }

        function logout() {
            $http.post('logout', {}).finally(function () {
                console.log("logout");
                $rootScope.authenticated = false;
                SessionService.destroy();
                callback(SessionService.currentUser());
            });
        }

        function isAuthenticated() {
            return !!this.currentUser();
        }

    }
})();
