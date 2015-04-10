/**
 * Copyright 2015 Nicotina Estudio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/


var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var usuarioService = require('../services/usuario-service');
var Transporte = require('../models/transporte').Transporte;
var Transportista = require('../models/transportista').Transportista;

/**
 * Schema para el modelo usuario
 */
var usuarioSchema = new Schema({
    uuid: String,
    esActivo: Boolean,
    nombre: String,
    apellidoPaterno: String,
    apellidoMaterno: String,
    tipoUsuario: String, // Titular de aprovechamiento, Inspector, Representante del CAT
    estado: String,
    municipio: String,
    codigoPostal: Number,
    curp: String,
    registroSiem: String,
    calle: String,
    numero: Number,
    colonia: String,
    fotografia: String,
    correoElectronico: String,
    contrasenia: String,
    fechaRegistro: { type: Date, default: Date.now },
    creado: { type: Date, default: Date.now },
    foliosAutorizados: String,
    materiaPrima: [String],
    volumenYOPesoamparado: Number,
    saldo: Number,
    transportes: [Transporte.schema],
    transportistas: [Transportista.schema] 
}, { collection: 'usuarios'});

/**
 * Validación de correo electrónico
 */
usuarioSchema.path('correoElectronico').validate(function(value, next) {
    usuarioService.buscar(value, function(err, usuario) {
        if (err) {
            console.log(err);
            return next(false);
        }
        next(!usuario);
    });
}, 'Este correo electrónico se encuentra en uso.');

/**
 * Virtual para obtener el nombre completo del usuario 
 */
usuarioSchema.virtual('nombre.completo').get(function(){
    return this.nombre + ' ' + this.apellidoPaterno + ' ' + this.apellidoMaterno;
});

/**
 * Remueve los datos que no queremos mostrar en el JSON
 */
 usuarioSchema.methods.toJSON = function() {
    var usuario = this.toObject();
    delete usuario._id;
    delete usuario.contrasenia;
    return usuario;
}

var Usuario = mongoose.model('Usuario', usuarioSchema);

module.exports = {
    Usuario: Usuario
};