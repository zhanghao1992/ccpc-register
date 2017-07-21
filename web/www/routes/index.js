var express = require('express');
var router = express.Router();
var page = require('../lib/page');
var vService = require('../lib/vService');

// 首页
router.get('/index', function (req, res, next) {
    req.session.zh = {
        sex: 'male'
    }
    page.load(req, res, {path: 'pages/index', data: {user: {name: 'zhanghao', age: 25}}});
});


// 二维码分享页面
router.get('/QRcode', function (req, res, next) {
    page.load(req, res, {path: 'pages/QRcode', data: {xxx: 'xxxxx'}});
});


// 晋级成功首页
router.get('/singUpSuccess', function (req, res) {
    page.load(req, res, {path: 'pages/singUpSuccess'});
});


module.exports = router;
