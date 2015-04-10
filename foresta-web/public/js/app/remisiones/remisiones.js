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
        .controller('RemisionesController', RemisionesController);

    RemisionesController.$inject = ['api', 'ngDialog', '$scope'];

    function RemisionesController(api, ngDialog, $scope) {
        var vm = this;
        $scope.estatus = "todas";
        $scope.tipo = "Remisión";
        api.getRemisiones()
            .then(function(data) {
                vm.remisiones = data;
                vm.remisiones_temp = data;
                vm.totalRemisiones = 0;
                vm.totalReembarques = 0;
                vm.totalEntregadas = 0;
                vm.totalNoEntregadas = 0;
                vm.totalCanceladas = 0;

                // Contadores
                vm.remisiones.forEach(function(remision){
                    if(remision.tipo == "Remisión")
                        vm.totalRemisiones ++;
                    else
                        vm.totalReembarques ++;

                    if(remision.estatus == "Entregado")
                        vm.totalEntregadas ++;

                    if(remision.estatus == "NoEntregado")
                        vm.totalNoEntregadas ++;

                    if(remision.estatus == "Cancelado")
                        vm.totalCanceladas ++;
                })
            },
            function(reason) {
                console.log(reason);
        });

        vm.viewItem = function(item) {
            vm.activeItem = item;
            vm.activeItem.options = [];
            
            ngDialog.open({
                template: 'item.html',
                className: 'ngdialog-theme-default',
                scope: $scope
            });
        };

        $scope.filtrar= function(){
            var remisiones_filtradas = []; 
            vm.remisiones_temp.forEach(function(remision){
                if(remision.tipo == $scope.tipo || remision.uuid == $scope.query ){
                    if($scope.estatus !== "todas"){
                        if(remision.estatus==$scope.estatus){
                            remisiones_filtradas.push(remision);  
                        }
                    }else{
                        remisiones_filtradas.push(remision);
                    }
                }
            })

            vm.remisiones = remisiones_filtradas;
        }

    }
}());