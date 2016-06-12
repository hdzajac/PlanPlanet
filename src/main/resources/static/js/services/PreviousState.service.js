/**
 * Created by Roksana on 2016-06-11.
 */
(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .factory('PreviousState', PreviousState);

    PreviousState.$inject = ['$rootScope', '$location'];
    function PreviousState($rootScope, $location) {

        var service = {
            goToLastState: goToLastState
        };


        var lastRoute = "/";
        $rootScope.$on('$locationChangeStart', function (evt, absNewUrl, absOldUrl) {

            var hashIndex = absOldUrl.indexOf('#');
            var oldRoute = absOldUrl.substr(hashIndex + 2);
            if (oldRoute !== "register") {
                lastRoute = oldRoute;
            }
        });
        return service;


        function goToLastState() {
            return $location.path(lastRoute);
        }
    }
})();