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

    angular
        .module('app')
        .config(config);

    config.$inject = ['$routeProvider'];

    function config($routeProvider) {
        $routeProvider
            .when('/inspectores', {
                templateUrl: '/js/app/inspectores/inspectores.html',
                controller: 'InspectoresController',
                controllerAs: 'vm'
            })
            .when('/titulares', {
                templateUrl: '/js/app/titulares/titulares.html',
                controller: 'TitularesController',
                controllerAs: 'vm'
            })
            .when('/remisiones', {
                templateUrl: '/js/app/remisiones/remisiones.html',
                controller: 'RemisionesController',
                controllerAs: 'vm'
            })
            .when('/materia', {
                templateUrl: '/js/app/materia/materia.html',
                controller: 'MateriaController',
                controllerAs: 'vm'
            })
            .when('/cats', {
                templateUrl: '/js/app/cats/cats.html',
                controller: 'CatsController',
                controllerAs: 'vm'
            });
  }
}());