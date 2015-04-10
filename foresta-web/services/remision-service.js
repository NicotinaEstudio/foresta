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
var Remision = require('../models/remision').Remision;
var uuid = require('node-uuid');


exports.crear = function(remision, next) {

    /* TODO 

    Obtener remitente
    Obtener destiantario

    */

    var remisionNva = new Remision({
        uuid: remision.uuid,
        fechaRegistro: remision.fechaRegistro,
        creado: new Date(),
        estatus: "En transito",
        comentarios: "",
        tipo: "Remisión",
        folioProgresivo: 12343,
        folioDeRemision: remision.folioDeRemision,
        remitente: {
            uuid : "11111111-1111-1111-1111-111111111111",
            esActivo : true,
            nombreCompleto : "Jose Duran Solorzano",
            rfn : "RFN-ACSDE",
            fecha : "2014-12-24T06:00:00Z",
            vigencia : "2014-12-27T06:00:00Z",
            volumenAutorizadoAnualidad : 1000000,
            anualidad : "2 de 10",
            generoYOProducto : "Madera en rollo - Pino",
            tipoResolucion : "Titular de aprovechamiento",
            tipoUsuario : "Titular de aprovechamiento",
            origenMateriaPrima : "La Concordia",
            estado : "Chiapas",
            municipio : "La Concordia",
            codigoPostal : 36369,
            curp : "CARP42046429HCLRND02",
            registroSiem : "EDRFR3445F",
            calle : "El refugio",
            numero : 162,
            colonia : "Centro",
            fotografia : "foto-titular.jpg",
            correoElectronico : "ta@nicotinaestudio.mx"
        },
        destinatario: {
            uuid : "11111111-1111-1111-1111-111111111118",
            denominacionORazonSocial : "MADERERÍA LA CEIBITA",
            creado : "2011-12-24T06:00:00Z",
            estado : "Morelos",
            municipio : "Taxco",
            domicilio : "CARRETERA FEDERAL MÉXICO-TAXCO S/N.COL ANGELES AMACUZAC,MORELOS",
            lat : "0",
            lon : "0",
            responsable : "Jorge Sanchez Gonzales",
            tipo : ["Maderería"],
            almacena : true,
            transforma : false,
            capacidadAlmacenamiento : 160,
            capacidadTransformacionInstalada : 0,
            capacidadTransformacionReal : 0
        },
        materiaPrima: {
            numeroYOCantidad: remision.materiaPrima.numeroYOCantidad,
            descripcion: remision.materiaPrima.descripcion,
            volumenYOPesoAmparado: remision.materiaPrima.volumenYOPesoAmparado,
            unidadDeMedida: "M3"
        },
        saldos: {
            observaciones: "",
            saldoDisponible: remision.saldos.saldoDisponible,
            cantidadQueAmpara: remision.saldos.cantidadQueAmpara
        },
        transporte: {
            uuid: remision.transporte.uuid,
            esActivo: remision.transporte.esActivo,
            medioDeTransporte: remision.transporte.medioDeTransporte,
            propietario: remision.transporte.propietario,
            marca: remision.transporte.marca,
            tipo: remision.transporte.tipo,
            modelo: remision.transporte.modelo,
            capacidad: remision.transporte.capacidad,
            placasOMatricula: remision.transporte.placasOMatricula,
            fotografia: remision.transporte.fotografia
        },
        transportista: {
            uuid: remision.transportista.transportista,
            esActivo: remision.transportista.esActivo,
            Nombre: remision.transportista.Nombre,
            apellidoPaterno: remision.transportista.apellidoPaterno,
            apellidoMaterno: remision.transportista.apellidoMaterno,
            estado: remision.transportista.estado,
            municipio: remision.transportista.municipio,
            codigoPostal: remision.transportista.codigoPostal,
            calle: remision.transportista.calle,
            numero: remision.transportista.numero,
            colonia: remision.transportista.colonia,
            fotografia: remision.transportista.fotografia
        }
    });

    remisionNva.save(function(err) {
        if (err)
            return next(err);

        next(null);
    });
};


exports.crearPorSMS = function(remSMS, next) {

    /* TODO 

    Obtener Remitente
    Obtener Transporte
    Obtener Transportista
    Obtener Destinatario

    */

    var remisionNva = new Remision(
    {
        uuid : remSMS[0],
        estatus : "Enviado",
        comentarios : "SMS",
        tipo : "Remisión",
        folioProgresivo : 88483,
        folioDeRemision : remSMS[2],
        transporte : {
            uuid : "11111111-1111-1111-1111-111111111115",
            medioDeTransporte : "Terrestre",
            fotografia : "foto-camion.jpg",
            placasOMatricula : "HEDMD-873",
            capacidad : "43 m3",
            modelo : "Actros 2660L",
            tipo : "Camion",
            marca : "Mercedes-Benz",
            propietario : "Carlos Solis Soto"
        },
        transportista : {
            uuid : "11111111-1111-1111-1111-111111111114",
            fotografia : "foto-tansportista.jpg",
            colonia : "Centro",
            numero : 3849,
            calle : "Francisco I Madero",
            codigoPostal : "36369",
            municipio : "La Concordia",
            estado : "Chiapas",
            apellidoMaterno : "Solache",
            apellidoPaterno : "Ruiz",
            Nombre : "Josue"
        },
        saldos : {
            observaciones : "",
            saldoDisponible : remSMS[8],
            cantidadQueAmpara : remSMS[9]
        },
        materiaPrima : {
            numeroYOCantidad : remSMS[11],
            descripcion : remSMS[12],
            volumenYOPesoAmparado : 432800,
            unidadDeMedida : "M3"
        },
        destinatario : {
            uuid : "11111111-1111-1111-1111-111111111118",
            denominacionORazonSocial : "MADERERÍA LA CEIBITA",
            creado : "2011-12-24T06:00:00Z",
            estado : "Morelos",
            municipio : "Taxco",
            domicilio : "CARRETERA FEDERAL MÉXICO-TAXCO S/N.COL ANGELES AMACUZAC,MORELOS",
            lat : "0",
            lon : "0",
            responsable : "Jorge Sanchez Gonzales",
            tipo : ["Maderería"],
            almacena : true,
            transforma : false,
            capacidadAlmacenamiento : 160,
            capacidadTransformacionInstalada : 0,
            capacidadTransformacionReal : 0
        },
        remitente : {
            uuid : "11111111-1111-1111-1111-111111111111",
            esActivo : true,
            nombreCompleto : "Jose Duran Solorzano",
            rfn : "RFN-ACSDE",
            fecha : "2014-12-24T06:00:00Z",
            vigencia : "2014-12-27T06:00:00Z",
            volumenAutorizadoAnualidad : 1000000,
            anualidad : "2 de 10",
            generoYOProducto : "Madera en rollo - Pino",
            tipoResolucion : "Titular de aprovechamiento",
            tipoUsuario : "Titular de aprovechamiento",
            origenMateriaPrima : "La Concordia",
            estado : "Chiapas",
            municipio : "La Concordia",
            codigoPostal : 36369,
            curp : "CARP42046429HCLRND02",
            registroSiem : "EDRFR3445F",
            calle : "El refugio",
            numero : 162,
            colonia : "Centro",
            fotografia : "foto-titular.jpg",
            correoElectronico : "ta@nicotinaestudio.mx"
        },
        creado : new Date(),
        fechaRegistro : "2015-03-25T18:25:36.469Z"
    });
    
    remisionNva.save(function(err, savedRemision) {
        if (!err) 
            return next(null, savedRemision.uuid);
        
        next(err);
    });
};

/**
* Obtiene todas las remisiones
*/
exports.todos = function(next) {
    Remision.find(function(err, remisiones) {
        next(err, remisiones);    
    });
};

/**
* Obtiene el detalle de una remision
* @param {string} - UUID de la remisión
*/
exports.detalle = function(remisionId, next) {
    Remision.findOne({uuid: remisionId}, function(err, remision) {
        next(err, remision);    
    });
};

/**
* Crea una nueva remisión
*/
exports.insertar = function(next) {
    var remision = new Remision({
        uuid: "11111111-1111-1111-1111-111111111114",
        fechaRegistro: new Date(),
        creado: new Date(),
        estatus: "Enviada",
        comentarios: "El chofer no trae identificación",
        tipo: "Remisión",
        folioProgresivo: 12343,
        folioDeRemision: 88,
        remitente: {
            uuid: "11111111-1111-1111-1111-111111111111",
            origenMateriaPrima: "Palos Altos",
            rfn: "RFMNJFRJI9F",
            tipoResolucion: "Titular de aprovechamiento",
            fecha: new Date('Dec 24, 2011'),
            vigencia: new Date('Dec 24, 2011'),
            volumenAutorizadoAnualidad: 30000,
            anualidad: "4 de 10",
            generoYOProducto: "Madera en rollo - Pino",
            nombreCompleto: "Juan Sanchez Solomer",
            tipoUsuario: "Titular de aprovechamiento",
            estado: "Michoacán",
            municipio: "Morelia",
            codigoPostal: 58000,
            curp: "CURPFNRNRRF8484",
            registroSiem: "SIEMFJCJ48493",
            calle: "Pinosuares",
            numero: 4958,
            colonia: "Lomas de san josé",
            fotografia: "img-ta.jpg",
            correoElectronico: "izarcc@hotmail.com"
        },
        destinatario: {
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
        },
        materiaPrima: {
            numeroYOCantidad: 400,
            descripcion: "Madera en rollo - Pino",
            volumenYOPesoAmparado: 100000,
            unidadDeMedida: "M3"
        },
        saldos: {
            observaciones: "",
            saldoDisponible: 5000,
            cantidadQueAmpara: 100000
        },
        transporte: {
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
        },
        transportista: {
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
        }
    });

    remision.save(function(err) {
        if (err)
            return next(err);

        next(null);
    });
};