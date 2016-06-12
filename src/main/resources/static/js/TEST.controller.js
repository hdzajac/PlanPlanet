/**
 * Created by Roksana on 2016-05-20.
 */
(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .controller('TESTcontr', TESTcontr);

    TESTcontr.$inject = ['$http'];

    function TESTcontr($http) {
        var vm = this;

        $http.get('nowy', {
                headers: headers
            })
            .success(function (data) {
                    vm.data = data;
                }
            )
            .error()

    }
})