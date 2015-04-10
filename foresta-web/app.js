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


var path = require('path');

var express = require('express');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var connectMongo = require('connect-mongo');
var passport = require('passport'); // Simple, unobtrusive authentication
var expressSession = require('express-session');
var flash = require('connect-flash');

var MongoStore = connectMongo(expressSession);

var passportConfig = require('./auth/passport-config');
passportConfig();

var config = require('./config.js');
var restringido = require('./auth/restringido');
var routes = require('./routes/index');
var api = require('./routes/api');
var cats = require('./routes/cats');
var usuarios = require('./routes/usuarios');
var remisiones = require('./routes/remisiones');

mongoose.connect(config.mongoUri);

var app = express();

app.set('produccion', process.env.NODE_ENV == 'produccion');

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'hbs');

app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use(expressSession(
{
    secret: 'getting hungry',
    saveUninitialized: false,
    resave: false,
    store: new MongoStore({
        mongooseConnection: mongoose.connection    
    })
}
));
app.use(flash());
app.use(passport.initialize());
app.use(passport.session());

app.use('/', routes);
app.use('/api', api);
app.use('/cats', cats);
app.use('/usuarios', usuarios);
app.use('/remisiones', remisiones);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function(err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

// produccion error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});

module.exports = app;