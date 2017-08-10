var express = require('express');
var session = require('express-session');
var path = require('path');
var swig = require('swig');

// var log4js = require('log4js');
// log4js.configure("./log4js.json");
// var logger = log4js.getLogger('logger');
// logger.setLevel('ERROR');

var index = require('./routes/index.js');
var common = require('./routes/common/main.js');
var ueditor = require('./routes/ueditor/main.js');

var config = require('./config.js');
global.projectRoot = __dirname;
global.userSessionList = {};

var app = express();

var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json({type: 'application/json'}));

app.engine('html', swig.renderFile);
if (config.release) {
    app.set('views', path.join(__dirname, 'output/views'));
} else {
    app.set('views', path.join(__dirname, 'src/views'));
}

app.set('view engine', 'html');

// app.use(log4js.connectLogger(logger, {level: 'auto', format: ':method :url'}));

app.use(session({//todo 参数含义
    secret: '12345',
    name: 'chess_member',
    resave: true,//刷新过期时间,
    rolling: true,//刷新过期时间,
    saveUninitialized: true,
    cookie: {
        maxAge: 1000 * 60 * 30
    }
}));

if (config.release) {
    app.use('/static', express.static('output/static'));
} else {
    app.use('/static', express.static('src/static'));
}
app.use('/upload/file', express.static('upload/file', {
    setHeaders: function (res) {
        res.setHeader('Content-Disposition', 'attachment;')
    }
}));
app.use('/upload', express.static('upload'));
app.use('/', index);
app.use('/common', common);
app.use('/ueditor', ueditor);

app.use(function (req, res, next) {
    res.status(404).send('404')
});
app.use(function (req, res, next) {
    res.status(500).send('500')
});

var server = app.listen(config.port, function () {

});