(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .controller('home', HomeController);

    HomeController.$inject = ['travelFieldsFactory', 'preferencesFieldsFactory', '$location', 'planService'];

    function HomeController(travelFieldsFactory, preferencesFactory, $location, planService) {
        var vm = this;
        vm.next = next;
        vm.submit = submit;
        vm.go = go;

        vm.map = { center: { latitude: 45, longitude: -73 }, zoom: 16 };
        vm.travel = planService.travel;
        vm.travelFields = travelFieldsFactory.getTravelFields();
        vm.preferences = planService.getPreferences1();
        vm.preferencesFields = preferencesFactory.getPreferences();
        vm.preferences2 = planService.getPreferences2();
        vm.preferencesFields2 = preferencesFactory.getPreferences2();

        function next() {
            planService.setTravel(vm.travel);
            go('/step2')
        }

        function submit() {
            console.log('submiting preference');
            planService.setPreferences(vm.preferences, vm.preferences2);
            go('/plans')
        }

        function go(path) {
            $location.path(path);
        }
    }
})();