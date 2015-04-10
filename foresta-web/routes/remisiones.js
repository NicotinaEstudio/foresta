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
var restringido = require('../auth/restringido');
var remisionService = require('../services/remision-service');

// GET
router.get('/insertar', function(req, res, next) {
    remisionService.insertar(function(err, remision) {
        if (err) {
            return res.status(500).json({ mensaje:'Error interno del servidor' });
        }        
        res.json({ mensaje:'Insertado' });
    });
});

module.exports = router;