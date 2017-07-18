var express = require('express');
var router = express.Router();
var page = require('../lib/page');
var vService = require('../lib/vService');
// 列表页
router.get('/', function (req, res, next) {
    res.render('pages/index.html');
});

// router.get('/getRefereeList', function (req, res) {
//     vService.transfer(req, res, {path: '/Referee/getRefereeInfoList', url: 'http://172.21.120.174:18088'});
//
// });
router.get('/login', function (req, res, next) {
    res.render('pages/login.html');
});



module.exports = router;
