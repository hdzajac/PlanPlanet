(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .controller('TESTcontr', TESTcontr);

    TESTcontr.$inject = ['$http'];

    function TESTcontr($http) {

        var vm = this;

        vm.map = {
            center: {
                latitude: 56.162939,
                longitude: 10.203921
            },
            zoom: 8
        };

        $http.get('nowy')
            .success(function (data) {
                    vm.data = data;
                }
            )

        console.log("TEST");

    }

})();