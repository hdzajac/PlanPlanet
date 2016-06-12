(function () {
    'use strict';

    angular
        .module('PlanPlanet')
        .factory('preferencesFieldsFactory', preferencesFieldsFactory);

    function preferencesFieldsFactory() {
        return {
            getPreferences: getPreferences,
            getPreferences2: getPreferences2
        };

        function getPreferences() {
            return [
                {
                    key: 'maxDistanceKm',
                    type: 'input',
                    templateOptions: {
                        label: 'Max distance between attractions',
                        type: 'number',
                        placeholder: 'distance in km'
                    }
                }, {
                    key: 'sightseeingTimeADay',
                    type: 'input',
                    templateOptions: {
                        label: 'Sightseeing hours per day',
                        type: 'number'
                    }
                }, {
                    key: 'maxPrice',
                    type: 'select',
                    templateOptions: {
                        label: 'Max price',
                        options: [
                            {
                                name: 'Free',
                                value: 'FREE'
                            },
                            {
                                name: 'Inexpensive',
                                value: 'INEXPENSIVE'
                            },
                            {
                                name: 'Moderate',
                                value: 'MODERATE'
                            },
                            {
                                name: 'Expensive',
                                value: 'EXPENSIVE'
                            },
                            {
                                name: 'Very expensive',
                                value: 'VERY_EXPENSIVE'
                            }
                        ]
                    }
                }
            ];
        }

        function getPreferences2() {
            var types = [
                {
                    value: 'AMUSEMENT_PARK',
                    name: 'Amusement Park'
                },
                {
                    value: 'AQUARIUM',
                    name: 'Aquarium'
                },
                {
                    value: 'ART_GALLERY',
                    name: 'Art Gallery'
                },
                {
                    value: 'BAR',
                    name: 'Bar'
                },
                {
                    value: 'BOWLING',
                    name: 'Bowling'
                },
                {
                    value: 'CAFE',
                    name: 'Cafe'
                },
                {
                    value: 'CASINO',
                    name: 'Casino'
                },
                {
                    value: 'CHURCH',
                    name: 'Church'
                },
                {
                    value: 'HINDU_TEMPLE',
                    name: 'Hindu Temple'
                },
                {
                    value: 'MOSQUE',
                    name: 'Mosque'
                },
                {
                    value: 'CINEMA',
                    name: 'Cinema'
                },
                {
                    value: 'MUSEUM',
                    name: 'Museum'
                },
                {
                    value: 'CLUB',
                    name: 'Club'
                },
                {
                    value: 'PARK',
                    name: 'Park'
                },
                {
                    value: 'RESTAURANT',
                    name: 'Restaurant'
                },
                {
                    value: 'SPA',
                    name: 'Spa'
                },
                {
                    value: 'SYNAGOGUE',
                    name: 'Synagogue'
                },
                {
                    value: 'ZOO',
                    name: 'Zoo'
                }
            ];

            return [
                {
                    key: 'keywords',
                    type: 'input',
                    templateOptions: {
                        type: 'text',
                        label: 'Keywords',
                        placeholder: 'separated with space'
                    }
                }, {
                    key: 'type1',
                    type: 'select',
                    templateOptions: {
                        label: 'Type',
                        options: types
                    }
                }, {
                    key: 'type2',
                    type: 'select',
                    templateOptions: {
                        label: 'Type',
                        options: types
                    }
                }, {
                    key: 'type3',
                    type: 'select',
                    templateOptions: {
                        label: 'Type',
                        options: types
                    }
                }
            ];
        }

    }

})();