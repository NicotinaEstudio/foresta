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


var mongoose = require('mongoose');
var Schema = mongoose.Schema;

/**
 * Schema para el modelo remisión
 */
var transporteSchema = new Schema({
    fechaRegistro: { type: Date, default: Date.now },
    creado: { type: Date, default: Date.now },
    id: Number,
    uuid: String,
    esActivo: Boolean,
    medioDeTransporte: String,
    propietario: String,
    marca: String,
    tipo: String,
    modelo: String,
    capacidad: String,
    placasOMatricula: String,
    fotografia: String
}, { collection: 'transportes'});

var Transporte = mongoose.model('Transporte', transporteSchema);

module.exports = {
    Transporte: Transporte    
};