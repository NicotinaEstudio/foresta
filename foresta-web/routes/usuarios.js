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
var passport = require('passport');
var restringido = require('../auth/restringido');
var config = require('../config');
var usuarioService = require('../services/usuario-service');

// GET
router.get('/', restringido, function(req, res, next) {
    var vm = {
        title: 'Usuario',
        nombre: req.user ? req.user.nombre : null,
        layout: 'layout-admin'
    };
    res.render('usuarios/index', vm);
});

// GET
router.get('/insertar', function(req, res, next) {
    usuarioService.insertar(function(err, usuario) {
        if (err) {
            return res.status(500).json({ mensaje:'Error interno del servidor' });
        }        
        res.json({ mensaje:'Insertado' });
    });
});

// POST
router.post('/autenticacion', function(req, res, next) {

    /*  TODO - Limpiar los parametros */
    
    req.session.orderId = 123456;
    if (req.body.recordarme) {
        req.session.cookie.maxAge = config.cookieMaxAge;
    }
    next();
}, passport.authenticate('local', {failureRedirect:'/', failureFlash: 'Credenciales inválidas', successRedirect:'/usuarios'}));

// GET
router.get('/cerrarSesion', restringido, function(req, res, next) {
    req.logout();
    req.session.destroy();
    res.redirect('/');
});

module.exports = router;