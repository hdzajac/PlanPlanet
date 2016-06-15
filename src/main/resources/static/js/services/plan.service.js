(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .service('planService', PlanService);

    PlanService.$inject = ['$http', '$q'];

    function clone(o) {
        return JSON.parse(JSON.stringify(o));
    }

    function equal(o1, o2) {
        var l = JSON.stringify(o1);
        var r = JSON.stringify(o2);

        console.log('left ' + l);
        console.log('right ' + r);

        return JSON.stringify(o1) === JSON.stringify(o2);
    }

    function PlanService($http, $q) {
        var self = this;

        this.travel = {};
        this.preferences = {};
        this.preferences2 = {};
        this.dirty = true;
        this.cachedPlan = {};
        this.savePlan = savePlan;

        this.getSuggestions = function (dayIndex, attractionIndex) {
            var deferred = $q.defer();

            var requestUrl = '/api/propositions?planId=' + self.cachedPlan.id + '&dayId=' + (dayIndex - 1) + '&attractionId=' + attractionIndex;
            $http.get(requestUrl)
                .success(function(data) {
                    deferred.resolve(data);
                }).error(function(msg, code) {
                deferred.reject(msg);
                console.log(msg, code);
            });

            return deferred.promise;
        };

        this.setTravel = function(travel) {
            if (!equal(travel, this.travel)) {
                this.travel = travel;
                this.dirty = true;
            }
        };

        this.getPreferences1 = function() {
            return clone(this.preferences);
        };

        this.getPreferences2 = function() {
            return clone(this.preferences2);
        };

        this.setPreferences = function(p1, p2) {
            if (!equal(p1, this.preferences)) {
                this.preferences = clone(p1);
                this.dirty = true;
            }
            if (!equal(p2, this.preferences2)) {
                this.preferences2 = clone(p2);
                this.dirty = true;
            }
        };

        this.fetchPlan = function() {
            var types = [this.preferences2.type1, this.preferences2.type2, this.preferences2.type3].filter(Boolean);

            var request = {
                destination: this.travel.destination,
                dateFrom: this.travel.dateFrom,
                dateTo: this.travel.dateTo,
                preferences: {
                    keywords: (this.preferences2.keywords || "").split(/\s+/).filter(function (s) { return !s.isEmpty }),
                    types: types,
                    minPrice: 'FREE',
                    maxPrice: this.preferences.maxPrice || 'VERY_EXPENSIVE',
                    maxDistanceKm: this.preferences.maxDistanceKm || 20,
                    sightseeingTimeADay: this.preferences.sightseeingTimeADay || 5
                }
            };


            var deferred = $q.defer();
            $http.post('/api/buildplan', request)
                .success(function(data) {
                    deferred.resolve(data);
                }).error(function(msg, code) {
                    deferred.reject(msg);
                    console.log(msg, code);
                });

            return deferred.promise;
        };

        this.getPlan = function () {
            var deferred = $q.defer();

            if (this.dirty) {
                var futurePlan = this.fetchPlan();
                futurePlan.then(function (plan) {
                    self.cachedPlan = plan;
                    self.dirty = false;
                    deferred.resolve(plan);
                    console.log(JSON.stringify(self));
                });
            } else {
                deferred.resolve(this.cachedPlan);
            }

            return deferred.promise;
        }

        this.insertAttraction = function(attraction, dayIndex, attractionIndex) {
            this.cachedPlan.days[dayIndex - 1].attractions.splice(attractionIndex, 0, attraction);
        }

        this.deleteAttraction = function(dayIndex, attractionIndex) {
            this.cachedPlan.days[dayIndex - 1].attractions.splice(attractionIndex, 1);
        }
        
        function savePlan(plan) {
            console.log(plan);
            this.cachedPlan.plan.city = this.travel.destination;
            this.cachedPlan.plan.author = plan.author;
            $http.post("/api/saveplan", this.cachedPlan)
                .then(function (response) {
                    if (response){
                        console.log("saved");
                    }
                })
        }
    }
})();