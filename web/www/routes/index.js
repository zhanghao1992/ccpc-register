var express = require('express');
var router = express.Router();
var page = require('../lib/page');
var vService = require('../lib/vService');
var captcha = require('../lib/captcha');
var session = require('../lib/session');

// 首页
router.get('/index', function (req, res, next) {
    page.load(req, res, {path: 'pages/index', data: {cpId: req.query.cpId}});
});

//报名信息填写页面
router.get('/fillInfo', function (req, res, next) {
    req.session.zh = {
        sex: 'male'
    }
    page.load(req, res, {path: 'pages/fillInfo'});
});

// 二维码分享页面
router.get('/QRcode', function (req, res, next) {
    page.load(req, res, {path: 'pages/index', data: {QRcode: true}});
});

// 输入身份证没数据页面
router.get('/noResult', function (req, res, next) {
    page.load(req, res, {path: 'pages/index', data: {noResult: true, cpId: req.query.cpId}});
});

// 个人有数据分享页面
router.get('/shareDtata', function (req, res, next) {
    page.load(req, res, {path: 'pages/index'});
});

// 晋级成功首页
router.get('/signUpSuccess', function (req, res) {
    page.load(req, res, {path: 'pages/signUpSuccess'});
});

// 个人资格查询结果
router.get('/personData', function (req, res) {
    page.load(req, res, {path: 'pages/personData'});
});

// 个人资格查询结果分享页
router.get('/sharePersonData', function (req, res) {
    page.load(req, res, {path: 'pages/index', data: {shareData: true}});
});

// 到报名时间没报名资格
router.get('/haveNoQualification', function (req, res) {
    page.load(req, res, {path: 'pages/haveNoQualification'});
});

router.get('/errorCp', function (req, res, next) {
    res.render('pages/errorCp');
});

//模拟厂商接口页面
router.get('/simulationCp', function (req, res, next) {
    res.render('pages/simulationCp');
});

//手机验证码接口
router.get('/api/sendMobileCode', function (req, res, next) {
    vService.transfer(req, res, {path: '/apply/sendMobileCode'});
});

//查询资格接口
router.post('/api/getQualification', function (req, res, next) {
    // var captchaResult = captcha.check(req, req.body.captcha);
    // if (captchaResult.code != 0) {
    //     res.send(JSON.stringify(captchaResult));
    // } else {
    vService.request(req, res, {path: '/apply/getCurrentUserInfo'}, function (s) {
        if(JSON.parse(s).code == 0){
            req.session.user = JSON.parse(s).response;
            res.send(JSON.stringify({code: 0, response: req.session.user}));
        }else {
            res.send(s);
        }
    });
    // }
});

//点我报名接口
router.post('/api/clickToEntry', function (req, res, next) {
    res.send(JSON.stringify({
        code: 0,
        response: '',
        mes: 'success'
    }));
    // vService.transfer(req, res, {path: '/match-user/focusMatch', url: 'http://172.21.120.241:8080/', auth: {type: 'player'}});
    // vService.transfer(req, res, {path: '/dictionary/getCityDictionaryList'});
});

//返回退出接口
router.get('/api/backOut', function (req, res) {
    session.logout(req);
    res.send(JSON.stringify({code: 0, msg: '退出成功！'}));
});

//个人查询资格分享页接口
router.get('/api/getRelevanceUserId', function (req, res, next) {
    vService.transfer(req, res, {path: '/apply/getRelevanceUserId'});
});

//参赛时间列表
router.get('/api/getMatchApplyDayList', function (req, res) {
    vService.transfer(req, res, {path: '/apply/getMatchApplyDayList'});
});

//参赛地点列表
router.get('/api/getMatchApplySKU', function (req, res) {
    vService.transfer(req, res, {path: '/apply/getMatchApplySKU'});
});

//报名参赛接口
router.get('/api/applyMatch', function (req, res) {
    vService.transfer(req, res, {path: '/apply/applyMatch'});
});

//当前用户报名信息
router.get('/api/getCurrentUserMatchApply', function (req, res) {
    vService.transfer(req, res, {path: '/apply/getCurrentUserMatchApply'});
});

module.exports = router;
