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


var express = require('express');
var router = express.Router();
var o2x = require('object-to-xml');

var config = require('../config');
var catService = require('../services/cat-service');
var usuarioService = require('../services/usuario-service');
var remisionService = require('../services/remision-service');

// GET
router.get('/', function(req, res, next) {

});

/** USUARIOS **/

// GET
router.get('/usuarios', function(req, res, next) {

    /*  TODO - Limpiar los parametros */

    var tipo = req.query.tipo;

    switch(tipo) {
        case "ta":
            tipo = "Titular de aprovechamiento";
            break;
        case "in":
            tipo = "Inspector";
            break;
        case "ca":
            tipo = "Representante CAT";
            break;
        default:
            tipo = "";
    }

    usuarioService.todos(tipo, function(err, usuarios) {
        if (err)
            return res.status(500).json({ mensaje:'Error interno del servidor' });

        res.status(200).json(usuarios);
    });
});

// GET
router.get('/usuarios/:usuarioId', function(req, res, next) {

    /*  TODO - Limpiar los parametros */

    usuarioService.detalle(req.params.usuarioId, function(err, usuario) {
        if (err)
            return res.status(500).json({ mensaje:'Error interno del servidor' });

        res.status(200).json(usuario);
    });
});

// PUT
router.put('/usuarios/:usuarioId', function(req, res, next) {

    /*  TODO - Limpiar los parametros */

    if(req.body.transportes){
    
        usuarioService.agregaTransporte(req.params.usuarioId, req.body, function(err, usuario) {
            if (err){
                console.log(err);
                return res.status(500).json({ mensaje:'Error interno del servidor' });
            }

            res.status(200).json({ mensaje:'Actualizado', transportes: req.body.transportes });
        });
    } else if(req.body.transportistas){
        usuarioService.agregaTransportista(req.params.usuarioId, req.body, function(err, usuario) {
            if (err){
                console.log(err);
                return res.status(500).json({ mensaje:'Error interno del servidor' });
            }

            res.status(200).json({ mensaje:'Actualizado', transportistas: req.body.transportistas });
        });
    } else {
        res.status(400).json({ mensaje:'Solicitud incorrecta' });
    }
});

// POST 
router.post('/usuarios/autenticar', function(req, res, next) {

    /*  TODO - Limpiar los parametros */

    if(!req.body.correoElectronico || !req.body.contrasenia)
        res.status(400).json({ mensaje:'Solicitud incorrecta' });

    usuarioService.apiAutenticacion(req.body.correoElectronico, req.body.contrasenia, function(err, usuario) {
        if (err)
            return res.status(500).json({ mensaje: 'Error interno del servidor' });

        if (!usuario)
            return res.status(204).json({ mensaje: 'Sin contenido' });

        // Armamos la respuesa que necesitamos
        var resultado = 
        {
            uuid: usuario.uuid,
            tipo: usuario.tipoUsuario
        }

        res.status(200).json(resultado);
    });
});

/** REMISIONES **/

// GET
router.get('/remisiones', function(req, res, next) {

    remisionService.todos(function(err, remisiones) {
        if (err)
            return res.status(500).json({ mensaje:'Error interno del servidor' });     

        res.status(200).json(remisiones);
    });
});

// POST
router.post('/remisiones', function(req, res, next) {
    
    /*  TODO - Limpiar los parametros */

    remisionService.crear(req.body, function(err, remision) {
        if (err)
            return res.status(500).json({ mensaje:'Error interno del servidor' }); 

        res.status(201).json({ mensaje: 'Creado', remision: req.body });
    });
});

// GET
router.get('/remisiones/:remisionId', function(req, res, next) {

    /*  TODO - Limpiar los parametros */

    remisionService.detalle(req.params.remisionId, function(err, remision) {
        if (err)
            return res.status(500).json({ mensaje:'Error interno del servidor' });    

        res.status(200).json(remision);
    });
});

/** CATS **/

// GET
router.get('/cats', function(req, res, next) {

    catService.todos(function(err, cats) {
        if (err) {
            return res.status(500).json({ mensaje:'Error interno del servidor' });
        }        

        res.status(200).json(cats);
    });
});

/** TWILIO **/

// GET
router.get('/twilio', function(req, res, next) {

    /* TODO - Agregar remisión */
    var msg = "Su remision se ha dado de alta con el folio: ";

    var remSMS = req.param('Body').split("_");

    remisionService.crearPorSMS(remSMS, function(err, remisionUuid){
        if (err) 
            msg = "Error interno del servidor";

        res.set('Content-Type', 'text/xml');

        res.send(o2x({
            '?xml version="1.0" encoding="utf-8"?' : null,
            Response: {
                Message: msg + remisionUuid
            }
        }));
    });
});

module.exports = router;