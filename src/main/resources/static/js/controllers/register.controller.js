/**
 * Created by Roksana on 2016-06-11.
 */
(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['$location', '$http'];
    function RegisterController($location, $http) {
        var vm = this;
        vm.register = register;

        function register() {
            vm.dataLoading = true;
            $http.post("/api/user/create", vm.user)
                .then(function (response) {
                    if (response.success) {
                        vm.dataLoading = false;
                        $location.path('/login');
                    } else {
                        vm.dataLoading = false;
                    }
                });
        }

    }

})();