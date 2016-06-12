/**
 * Created by Roksana on 2016-06-12.
 */
(function () {
    'use strict';

    angular
        .module("PlanPlanet")
        .directive("match", match);

    function match() {
        return {
            restrict: "A",
            require: "ngModel",
            link: function (scope, element, attrs, ctrl) {
                function matchValidator(value) {
                    scope.$watch(attrs.match, function (newValue, oldValue) {
                        var isValid = value === scope.$eval(attrs.match);
                        ctrl.$setValidity('match', isValid);

                    });
                    return value;
                }
                ctrl.$parsers.push(matchValidator);
            }
        };
    }
})();