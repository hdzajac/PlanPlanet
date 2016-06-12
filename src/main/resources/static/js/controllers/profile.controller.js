(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .controller('profileCtrl', profileCtrl);

    function profileCtrl() {

        var vm = this;

        vm.tab = 1;

        vm.setTab = function(newValue){
            vm.tab = newValue;
        };

        vm.isSet = function(tabName){
            return vm.tab === tabName;
        };

    }

})();