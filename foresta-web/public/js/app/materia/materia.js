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


(function(){
    'use strict';
    angular
    .module('app')
    .controller('MateriaController', MateriaController);

    MateriaController.$inject = ['api', '$scope'];

    function MateriaController(api, $scope) {

        // Dona Recursos Maderables
        $scope.dLabelsRM = ["Pino", "Cedro", "Oyamel", "Pino", "Cedro", "Oyamel", "Pino", "Cedro"];
        $scope.dDataRM = [300, 300, 100, 300, 300, 100, 300, 300];

        // Dona Recursos No Maderables
        $scope.dLabelsRNM = ["Pino", "Cedro", "Oyamel", "Pino", "Cedro", "Oyamel", "Pino"];
        $scope.dDataRNM = [300, 500, 100, 300, 300, 100, 300];

        $scope.labels = ['Enero', 'Febrero', 'Marzo', 'Abril'];
        $scope.series = ['Maderables', 'No maderables'];

        $scope.data = [
            [20000, 18000, 15000, 14000],
            [25000, 21000, 17000, 11000]
        ];


        var vm = this;
        api.getUsuarios()
        .then(function(data) {
            vm.inspectores = data;    
        },
        function(reason) {
            console.log(reason);
        });
    }
}());