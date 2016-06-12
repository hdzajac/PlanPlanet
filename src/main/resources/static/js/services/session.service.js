/**
 * Created by Roksana on 2016-06-08.
 */
(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .factory('SessionService', SessionService);

    SessionService.$inject = ['$cookies', '$http'];

    function SessionService($cookies, $http) {
        this.initialized = false;
        var service = {
            create: create,
            destroy: destroy,
            current: current
        };
        return service;

        function create(data) {
            $cookies.put('userData', data);
            this.userData = data;
        }

        function destroy() {
            this.create(null);
        }

        function current(successCallback, errorCallback) {
            if (!this.initialized) {
                var storedData = $cookies.get('userData');
                if (storedData != undefined) {
                    this.create(storedData);
                }
                this.initialized = true;
            }
            if ( successCallback !== undefined) {
                successCallback(this.userData);
            }
            return this.userData;
        }

    }
})();
