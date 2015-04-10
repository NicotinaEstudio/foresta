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


var bcrypt = require('bcrypt');
var Usuario = require('../models/usuario').Usuario;
var Transporte = require('../models/transporte').Transporte;
var Transportista = require('../models/transportista').Transportista;
var uuid = require('node-uuid');
var bcrypt = require('bcrypt');

/**
 * Obtiene todos los usuarios
 */
exports.todos = function(tipo, next) {
    if(tipo != ""){
        Usuario.find({ tipoUsuario: tipo }, function(err, usuarios) {
            next(err, usuarios);    
        });
    } else {
        Usuario.find(function(err, usuarios) {
            next(err, usuarios);    
        });
    }
};

/**
* Crea un nuevo usuario
*/
exports.insertar = function(next) {

    var transporte = new Transporte({
        fechaRegistro: new Date(),
        creado: new Date(),
        id: 1,
        uuid: "11111111-1111-1111-1111-111111111112",
        esActivo: true,
        medioDeTransporte: "Camion",
        propietario: "José Antonio Hernández Campos",
        marca: "Honda",
        tipo: "Torton",
        modelo: "MX-6",
        capacidad: "200 Toneladas",
        placasOMatricula: "HEDMD-873",
        fotografia: "transporte.jpg"
    });

    var transportista = new Transportista({
        fechaRegistro: new Date(),
        creado: new Date(),
        id: 1,
        uuid: "11111111-1111-1111-1111-111111111113",
        esActivo: true,
        Nombre: "Pedro",
        apellidoPaterno: "Paramo",
        apellidoMaterno: "Solis",
        estado: "Michoacán",
        municipio: "Morelia",
        codigoPostal: "58000",
        calle: "Francisco I Maderp",
        numero: 3849,
        colonia: "Centro",
        fotografia: "foto-tansportista.jpg"
    });

    var usuario = new Usuario({
        uuid: "11111111-1111-1111-1111-111111111111",
        esActivo: true,
        nombre: "Carlos Isaac",
        apellidoPaterno: "Hernandez",
        apellidoMaterno: "",
        tipoUsuario: "Titular de aprovechamiento", 
        estado: "Michoacan",
        municipio: "Morelia",
        codigoPostal: 58000,
        curp: "CURP",
        registroSiem: "EDRFR3445F",
        calle: "Del trabajo",
        numero: 162,
        colonia: "Centro",
        fotografia: "imgafr.jpg",
        correoElectronico: "chernandez@nicotinaestudio.mx",
        contrasenia: "$2a$10$OCLTHO7bU3VYfEflWdxyoOUhzt3O3WJaYr2IEPQeKzH6gGDrhzlaW",
        fechaRegistro: new Date(),
        creado: new Date(),
        foliosAutorizados: "77/150",
        materiaPrima: ["Pino ocarpa"],
        volumenYOPesoamparado: 100000,
        saldo: 5000
    });

    usuario.transportes.push(transporte);

    usuario.transportistas.push(transportista);

    usuario.save(function(err) {
        if (err) {
            console.log(err);
            return next(err);
        }            
        next(null);
    });
};

/**
* Obtiene el detalle de un usuario
* @param {string} - UUID del usuario
*/
exports.detalle = function(usuarioId, next) {
    Usuario.findOne({uuid: usuarioId}, function(err, usuario) {
        next(err, usuario);    
    }); 
};

/**
* Agregar transporte
* @param {string} - UUID del usuario
* @param {object} - Objeto usuario
*/
exports.agregaTransporte = function(usuarioId, usuario, next) {
    Usuario.findOne({ uuid: usuarioId}, function(err, usuarioOrig) {
        Usuario.findByIdAndUpdate( usuarioOrig._id, { $push: { "transportes": usuario.transportes }}, {safe: true, upsert: true}, function(err, usuario) {
            console.log(err);
            next(err, usuario);  
        });    
    });
};

/**
* Agregar transportista
* @param {string} - UUID del usuario
* @param {object} - Objeto usuario
*/
exports.agregaTransportista = function(usuarioId, usuario, next) {
    Usuario.findOne({ uuid: usuarioId}, function(err, usuarioOrig) {
        Usuario.findByIdAndUpdate( usuarioOrig._id, { $push: { "transportistas": usuario.transportistas }}, {safe: true, upsert: true}, function(err, usuario) {
            console.log(err);
            next(err, usuario);  
        });    
    });
};

/**
* Encuentra un usuario
* @param {String} - Correo electrónico
*/
exports.buscar = function(correoElectronico, next) {
    Usuario.findOne({correoElectronico: correoElectronico.toLowerCase()}, function(err, usuario) {
        next(err, usuario);    
    });
};

/**
* Autenticación básica de un usuario en la API
* @param {String} - Correo electrónico del usuario
* @param {String} - Contraseña del usuario
*/
exports.apiAutenticacion = function(correoElectronico, contrasenia, next) {

    Usuario.findOne({ correoElectronico: correoElectronico }, function(err, usuario) {
        if (err) 
            return next(err);
        
        if (!usuario) 
            return next(null, null);
        
        bcrypt.compare(contrasenia, usuario.contrasenia, function(err, same) {
            if (err)
                return next(err);
            
            if (!same)
                return next(null, null);
            
            next(null, usuario); 
        });
    }); 
};

/**
 * Encuentra un usuario
 * @param {String} - Correo electtrónico
 */
exports.encuentraUsuario = function(correoElectronico, next) {
    Usuario.findOne({correoElectronico: correoElectronico.toLowerCase()}, function(err, usuario) {
        next(err, usuario);    
    });
};