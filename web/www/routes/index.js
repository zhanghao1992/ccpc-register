var express = require('express');
var router = express.Router();
var page = require('../lib/page');
var vService = require('../lib/vService');
var cache = require('memory-cache');

router.get('/setCache', function (req, res, next) {
    req.myCache.set("myKey", obj, 5, function (err, success) {
        if (!err && success) {
            console.log(success);
            res.send(JSON.stringify({code: 0, response: 0}))
        }
    });

});

router.get('/login', function (req, res, next) {
    page.load(req, res, {path: 'pages/login', data: {xxx: 'xxxxx'}});

    console.log(cache.get('foo'))
});

router.get('/index', function (req, res, next) {
    req.session.zh = {
        sex: 'male'
    }
    cache.put('foo', 'bar', 5000, function () {
        cache.put('foo', 'newbar');
    });
    page.load(req, res, {path: 'pages/index', data: {user: {name: 'zhanghao', age: 25}}});
});

module.exports = router;
