var express = require('express');
var router = express.Router();
var page = require('../../lib/page');
var captcha = require('../../lib/captcha');
var vService = require('../../lib/vService');
var multer = require("multer");
var upload = multer();
router.get('/testcaptcha', function (req, res) {
    res.send(JSON.stringify(captcha.check(req, req.query.v)));
});
router.get('/captcha', function (req, res) {
//    console.log('xxx1');
    vService.request(req, res, {path: '/captcha/create'}, function (json) {
        json = JSON.parse(json);
//        console.log('xxx');
//        console.log(json);
//        console.log(new Date().getTime());
        if (json.code == 0) {
            req.session.captcha = {value: json.response.token, createTime: new Date().getTime()};
            res.end(new Buffer(json.response.base64String, 'base64').toString('binary'), 'binary');
        } else {
            res.send('');//todo
        }
//        res.send('<img src="data:image/png;base64,'+json.response.base64String+'">');
    });

});
router.get('/city', function (req, res) {
    vService.transfer(req, res, {path: '/dictionary/getCityDictionaryList'});

});
router.get('/getFile', function (req, res) {
    vService.request(req, res, {path: '/file/getFile', url: 'http://172.21.120.174:18088'}, function (json) {
        json = JSON.parse(json);
        if (json.code == 0) {
//            res.writeHead('200', {'Content-Type': json.response.contentType, 'Content-Disposition': 'attachment; filename=' + json.response.fileName});

            res.writeHead('200', {'Content-Type': json.response.contentType, 'Content-Disposition': 'inline;filename=' + encodeURIComponent(json.response.fileName)});
            res.end(new Buffer(json.response.base64String, 'base64').toString('binary'), 'binary');
//            res.send(new Buffer(json.base64String, 'base64'));
        } else {
            res.send('404');//todo
        }
    });
});
router.get('/getFileInfo', function (req, res) {
    vService.request(req, res, {path: '/common-file/getFile'}, function (json) {
        json = JSON.parse(json);
        if (json.code == 0) {
            res.send(JSON.stringify({code: 0, response: {fileName: json.response.fileName, fileMD5: json.response.fileMD5}}));
//            res.send(JSON.stringify(json));
        } else {
            res.send('404');//todo
        }
    });
});
router.post('/upload', upload.single('file'), function (req, res) {
    vService.request(req, res, {path: '/common-file/uploadBase64', url: 'http://172.21.120.241:8080/', addParams: {
        contentType: req.file.mimetype,
        base64String: req.file.buffer.toString('base64'),
        fileName: req.file.originalname,
        suffix: req.file.originalname.substring(req.file.originalname.lastIndexOf(".") + 1),
        size: req.file.size
    }}, function (json) {
        json = JSON.parse(json);
        if (json.code == 0) {
            res.send(JSON.stringify({code: 0, response: {fileName: json.response.fileName, fileMD5: json.response.fileMD5}}));
        } else {
            res.send(JSON.stringify(json));
        }

    });

});

module.exports = router;