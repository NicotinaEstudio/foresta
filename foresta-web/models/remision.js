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
var remisionSchema = new Schema({
    uuid: String,
    fechaRegistro: { type: Date, default: Date.now },
    creado: { type: Date, default: Date.now },
    estatus: String, // En transito, Recibida, No Recibida, Cancelada
    comentarios: String,
    tipo: String, // Remisión, Reembarque
    folioProgresivo: Number,
    folioDeRemision: Number,
    remitente: {
        uuid: String,
        origenMateriaPrima: String,
        rfn: String,
        tipoResolucion: String,
        fecha: { type: Date, default: Date.now },
        vigencia: { type: Date, default: Date.now },
        volumenAutorizadoAnualidad: Number,
        anualidad: String,
        generoYOProducto: String,
        nombreCompleto: String,
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
        correoElectronico: String
    },
    destinatario: {
        uuid: String,
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
    },
    materiaPrima: {
        numeroYOCantidad: Number,
        descripcion: String,
        volumenYOPesoAmparado: Number,
        unidadDeMedida: String
    },
    saldos: {
        observaciones: String,
        saldoDisponible: Number,
        cantidadQueAmpara: Number
    },
    transporte: {
        uuid: String,
        medioDeTransporte: String,
        propietario: String,
        marca: String,
        tipo: String,
        modelo: String,
        capacidad: String,
        placasOMatricula: String,
        fotografia: String,
    },
    transportista: {
        uuid: String,
        Nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        estado: String,
        municipio: String,
        codigoPostal: String,
        calle: String,
        numero: Number,
        colonia: String,
        fotografia: String
    }
}, { collection: 'remisiones'});

/**
 * Remueve los datos que no queremos mostrar en el JSON
 */
remisionSchema.methods.toJSON = function() {
    var remision = this.toObject();
    delete remision._id;
    return remision;
}

var Remision = mongoose.model('Remision', remisionSchema);

module.exports = {
    Remision: Remision    
};