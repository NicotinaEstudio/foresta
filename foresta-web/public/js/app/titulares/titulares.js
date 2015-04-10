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
        .controller('TitularesController', TitularesController);

    TitularesController.$inject = ['api', 'ngDialog', '$scope'];

    function TitularesController(api, ngDialog, $scope) {
        var vm = this;
        api.getTitulares()
            .then(function(data) {
                vm.titulares = data;    
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
    }
}());