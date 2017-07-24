var express = require('express');
var router = express.Router();
var page = require('../lib/page');
var vService = require('../lib/vService');
router.get('/form', function (req, res, next) {
    req.session.zh = {
        sex: 'male'
    }
    page.load(req, res, {path: 'pages/form', data: {user: {name: 'zhanghao', age: 25}}});
});

// 首页
router.get('/index', function (req, res, next) {
    req.session.zh = {
        sex: 'male'
    }
    page.load(req, res, {path: 'pages/index', data: {user: {name: 'zhanghao', age: 25}}});
});

//报名信息填写页面
router.get('/fillInfo', function (req, res, next) {
    req.session.zh = {
        sex: 'male'
    }
    page.load(req, res, {path: 'pages/fillInfo', data: {user: {name: 'zhanghao', age: 25}}});
});


// 二维码分享页面
router.get('/QRcode', function (req, res, next) {
    page.load(req, res, {path: 'pages/QRcode', data: {xxx: 'xxxxx'}});
});


// 晋级成功首页
router.get('/signUpSuccess', function (req, res) {
    page.load(req, res, {path: 'pages/signUpSuccess'});
});


module.exports = router;
