(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .controller('Plans', Plans);

    Plans.$inject = ['$location', 'planService'];

    function Plans($location, planService) {
        var vm = this;
        vm.go = go;


        planService.getPlan().then(function (plan) {
            vm.generatedPlan = plan;
        });
        
        function go(path) {
            $location.path(path);
        }
    }
})();