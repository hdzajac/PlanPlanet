(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .controller('profileCtrl', profileCtrl);

    profileCtrl.$inject = ['$http', 'AuthenticationService'];

    function profileCtrl($http, AuthenticationService) {

        var vm = this;
        vm.plans = {};
        vm.getPlans = getPlans;
        vm.login = AuthenticationService.currentUser().login;

        vm.tab = 1;

        getPlans();

        vm.setTab = function(newValue){
            vm.tab = newValue;
        };

        vm.isSet = function(tabName){
            return vm.tab === tabName;
        };

        function getPlans() {
            $http.get("/getPlans/"+vm.login)
                .then(function (response) {
                    if (response.data.created){
                        console.log(response);
                        vm.plans = response.data.plans;
                        console.log(vm.plans);
                    }
                })
        }

    }

})();