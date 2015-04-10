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
 * Schema para el modelo cat
 */
var catSchema = new Schema({
    uuid: String,
    creado: { type: Date, default: Date.now },
    denominacionORazonSocial: String,
    estado: String,
    municipio: String,
    domicilio: String,
    lat: String,
    lon: String,
    responsable: String,
    tipo: [String],
    almacena: Boolean,
    transforma: Boolean,
    capacidadAlmacenamiento: Number,
    capacidadTransformacionInstalada: Number,
    capacidadTransformacionReal: Number
}, { collection: 'cats'});

/**
 * Remueve los datos que no queremos mostrar en el JSON
 */
catSchema.methods.toJSON = function() {
    var cat = this.toObject();
    delete cat._id;
    return cat;
}

var Cat = mongoose.model('Cat', catSchema);

module.exports = {
    Cat: Cat    
};