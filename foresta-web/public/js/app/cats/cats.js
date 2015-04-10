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
        .controller('CatsController', CatsController);

    CatsController.$inject = ['api'];

    function CatsController(api) {
        var vm = this;
        api.getCats()
            .then(function(data) {
            vm.cats = data;  
            vm.totalCATS = 0;
            vm.totalAlmacenamiento = 0;
            vm.totalTransformacion = 0;
            vm.totalCapAlmacenamiento = 0;
            vm.totalCapTransformacion = 0;

            // Contadores
            vm.cats.forEach(function(cat){
                vm.totalCATS ++;

                if(cat.almacena)
                    vm.totalAlmacenamiento ++;

                if(cat.transforma)
                    vm.totalTransformacion ++;

                vm.totalCapAlmacenamiento += cat.capacidadAlmacenamiento;
                vm.totalCapTransformacion += cat.capacidadTransformacionInstalada;
            })  
        },
        function(reason) {
            console.log(reason);
        });
    }
}());