(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .controller('PlanDetails', PlanDetails);


    PlanDetails.$inject = ['$routeParams', 'planService', '$location', '$scope', '$uibModal', 'AuthenticationService'];
    var ProposedAttractionsController = function ($scope, $uibModalInstance, $uibModal, item) {
        console.log('controller got item ' + JSON.stringify(item));
        $scope.suggestions = item.suggestions;
        $scope.choice = null;

        $scope.setChoice = function (index) {
            $scope.choice = $scope.suggestions[index];
        };

        $scope.ok = function () {
            $uibModalInstance.close($scope.choice);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    };

    function PlanDetails($routeParams, planService, $location, $scope, $uibModal, AuthenticationService) {
        var vm = this;
        vm.query = {index: 1};
        vm.details = [];
        vm.params = planService.travel;
        vm.go = go;
        vm.showAttractions = showAttractions;
        vm.showDetails = showDetails;
        vm.save = save;

        vm.generatedPlan = planService.cachedPlan;
        vm.markers = [];
        vm.polylines = [];
        vm.removeAttraction = removeAttraction;
        vm.showModal = showModal;


        var geocoder =  new google.maps.Geocoder();

        geocoder.geocode({'address': planService.travel.destination}, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                var lat = results[0].geometry.location.lat();
                var lng = results[0].geometry.location.lng();
                console.log("lat: " + lat + " lng: " + lng);
                vm.map = {center: {latitude: lat, longitude: lng}, zoom: 13};
            }
        });

        $scope.$watch('vm.map', geocoder.geocode({'address': planService.travel.destination}, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    var lat = results[0].geometry.location.lat();
                    var lng = results[0].geometry.location.lng();
                    console.log("lat: " + lat + " lng: " + lng);
                    vm.map = {center: {latitude: lat, longitude: lng}, zoom: 13};
                }
            })
        );


        console.log($routeParams.planId);
        console.log("PLAN_DETAILS");

        showAttractions($routeParams.planId);
        function go(path) {
            $location.path(path);
        }

        function showAttractions(day) {
            vm.query = {index: day};
            vm.details = [];
            console.log("outside");
            console.log("day: " + day);
            var myday = vm.generatedPlan.days.filter(function( obj ) {
                console.log(obj);
                return obj.index == day;
            })[0];
            console.log("przed atrakcjami");
            console.log(myday.index);
            console.log(myday.attractions);
            var atr = {};
            vm.polylines.push({
                    id:1,
                    path:[],
                    stroke: {
                        color: '#6060FB',
                        weight: 3
                    },
                    visible: true,
                    icons: [{
                    icon: {
                        path: google.maps.SymbolPath.BACKWARD_OPEN_ARROW
                    },
                    offset: '25px',
                    repeat: '50px'
                }]}
            );
            for (var i = 0; i < myday.attractions.length; i++){
                atr =  myday.attractions[i];
                vm.markers.push({id : atr.name,
                                        latitude: atr.location.latitude,
                                        longitude: atr.location.longitude
                });
                vm.polylines[0].path.push({
                                    latitude: atr.location.latitude,
                                    longitude: atr.location.longitude});

            }
            console.log(vm.polylines);
            console.log(vm.markers);
            console.log(vm.query);
        }

        function showDetails($index) {
            vm.details[$index] = vm.details[$index] != true;
        }

        function save() {
            if (AuthenticationService.isAuthenticated()) {
                vm.generatedPlan.author = AuthenticationService.currentUser().login;
                planService.savePlan(vm.generatedPlan);
            } else {
                go("/login");
            }
        }

        function showModal(suggestions, dayIndex, attractionIndex) {
            vm.opts = {
                backdrop: true,
                backdropClick: true,
                dialogFade: false,
                keyboard: true,
                templateUrl: 'proposed_attractions.html',
                controller: ProposedAttractionsController,
                resolve: {
                    item: function () {
                        return {
                            suggestions: suggestions
                        }
                    }
                }
            };

            var modalInstance = $uibModal.open(vm.opts);

            modalInstance.result.then(function (result) {
                planService.deleteAttraction(dayIndex, attractionIndex);
                if (result !== null) {
                    planService.insertAttraction(result, dayIndex, attractionIndex);
                }
            }, function () {
                console.log("Modal Closed");
            });
        }

        function removeAttraction(dayIndex, attractionIndex) {
            planService.getSuggestions(dayIndex, attractionIndex).then(function (suggestions) {
                console.log('got suggestions ' + JSON.stringify(suggestions));
                vm.showModal(suggestions, dayIndex, attractionIndex);
            });
        }
    }
})();