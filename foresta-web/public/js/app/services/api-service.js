/**
 * Copyright 2015 Nicotina Estudio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/


(function() {
    'use strict';
    angular.module('app')
    .factory('api', apiFactory);

    apiFactory.$inject = ['$http'];

    function apiFactory($http) {
        return {
            getRemisiones: getRemisiones,
            getUsuarios: getUsuarios,
            getTitulares: getTitulares,
            getCats: getCats
        };

        function getRemisiones() {
            return $http.get('/api/remisiones')
            .then(function(response) {
                return response.data;
            });
        }

        function getUsuarios() {
            return $http.get('/api/usuarios')
            .then(function(response) {
                return response.data;
            });
        }

        function getTitulares() {
            return $http.get('/api/usuarios?tipo=ta')
            .then(function(response) {
                return response.data;
            });
        }

        function getCats() {
            return $http.get('/api/cats')
            .then(function(response) {
                return response.data;
            });
        }
    }
}());