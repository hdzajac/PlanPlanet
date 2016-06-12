(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .factory('travelFieldsFactory', travelFieldsFactory);

    function travelFieldsFactory() {
        var service = {
            getTravelFields: getTravelFields
        };

        return service;

        function getTravelFields() {
            return [
                {
                    key: 'destination',
                    type: 'input',
                    templateOptions: {
                        type: 'text',
                        label: 'Destination',
                        placeholder: 'Destination',
                        required: true
                    }
                },
                {
                    key: 'dateFrom',
                    type: 'input',
                    templateOptions: {
                        type: 'date',
                        label: 'From',
                        placeholder: 'From',
                        required: true
                    }
                },
                {
                    key: 'dateTo',
                    type: 'input',
                    templateOptions: {
                        type: 'date',
                        label: 'To',
                        placeholder: 'To',
                        required: true
                    }
                }
            ];
        }

    }
})();
