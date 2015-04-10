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


 var config = require('../config');
 var Cat = require('../models/cat').Cat;
 var uuid = require('node-uuid');

/**
 * Obtiene todas las cates
 */
 exports.todos = function(next) {
 	Cat.find(function(err, cats) {
 		next(err, cats);    
 	});
 };

/**
 * Obtiene el detalle de una cat
 * @param {string} - UUID de la remisión
 */
 exports.detalle = function(catId, next) {
 	Cat.findOne({uuid: catId}, function(err, cat) {
 		next(err, cat);    
 	});
 };

/**
 * Crea un nuevo CAT
 */
 exports.insertar = function(next) {
 	var cat = new Cat({
 		uuid: "11111111-1111-1111-1111-111111111115",
 		denominacionORazonSocial: "MADERERÍA LA CEIBITA",
 		creado: new Date('Dec 24, 2014'),
 		estado: "Michoacán",
 		municipio: "Morelia",
 		domicilio: "CARRETERA FEDERAL MÉXICO-TAXCO S/N.COL ANGELES AMACUZAC,MORELOS",
 		lat: "0",
 		lon: "0",
 		responsable: "Juan Perez Soto",
 		tipo: ["Maderería"],
 		almacena: true,
    	transforma: false,
    	capacidadAlmacenamiento: 160,
    	capacidadTransformacionInstalada: 0,
    	capacidadTransformacionReal: 0
 	});

 	cat.save(function(err) {
 		if (err) {
 			return next(err);
 		}            
 		next(null);
 	});
 };