//var http = require('http');
//var qs = require('querystring');
var session = require('./session');
var request = require('request');
module.exports = {
    debug: false,
    request: function (req, res, option, callback) {
        var me = this;
        if (option.auth == undefined) {
            me.requestGo(req, res, option, callback);
        } else {
            session.check(req);
            if (req.session.user) {
                if (option.auth.type == 'org' || option.auth.type == 'player') {
                    if (req.session.user.type == option.auth.type) {
                        me.requestGo(req, res, option, callback);
                    } else {
                        res.writeHead(200, {'Content-Type': 'application/json'});
                        res.end(JSON.stringify({code: -1}));
                    }
                } else if (option.auth.type == 'association') {
                    if (req.session.user.limits[option.auth.limit]) {
                        me.requestGo(req, res, option, callback);
                    } else {
                        res.writeHead(200, {'Content-Type': 'application/json'});
                        res.end(JSON.stringify({code: -1}));
                    }

//                    var i;
//                    var find = false;
//                    for (i in req.session.user.limits) {
//                        if (req.session.user.limits[i] == option.auth.limit) {
//                            find = true;
//                        }
//                    }
//                    if (find) {
//                        me.requestGo(req, res, option, callback);
//                    } else {
//                        res.writeHead(200, {'Content-Type': 'application/json'});
//                        res.end(JSON.stringify({code: -1}));
//                    }

                }

            } else {
                res.end(JSON.stringify({code: -1}));
            }
        }
    },
    getUserId: function (req) {
        if (req.session.user && req.session.user.id) {
            return req.session.user.id;
        } else {
            return '';
        }
    },
    getUrl: function (req) {
        //console.log(req)
        //if (req.baseUrl.user && req.session.user.type && (req.session.user.type == 'org' || req.session.user.type == 'association')) {
        //    return 'http://172.21.120.207:8080';
        //} else {
        //    return 'http://172.21.120.241:8080';
        //}
       /* if (req.baseUrl.indexOf('/guotiao') == 0) {
            return 'http://172.21.120.211:7070';
        } else if (req.baseUrl.indexOf('/xiangqi') == 0){
            return 'http://172.21.120.241:8082';
        }*/
        return 'http://172.21.122.138:7071';
    },
    requestGo: function (req, res, option, callback) {
        //使用request模块发送请求
        var me = this;
        if (req.method == 'GET') {

            if (option.addParams) {
                var i;
                for (i in option.addParams) {
                    req.query[i] = option.addParams[i];
                }
            }
            // console.log('vjsonGet');
            // console.log(req.query);
            // console.log(typeof req.query.status);
            // console.log('vjsonGet');
            request.post({
                headers: {
                    'Authorization': me.getUserId(req)
                },
                url: ( (me.debug && option.url) ? option.url : me.getUrl(req)) +
                    (option.paths ? option.paths['p_' + req.session.user.level] : option.path), form: req.query}, function (err, httpResponse, body) {

                if (err || httpResponse.statusCode != 200) {
                    res.send(JSON.stringify({code:-1}));
//                    res.end();
                } else {
                    callback(body);
                }

            });

        } else {
            if (req.is('application/json')) {

                if (option.addParams) {
                    var i;
                    for (i in option.addParams) {
                        req.body[i] = option.addParams[i];
                    }
                }

                // console.log('vjson');
                // console.log(req.query);
                // console.log(req.body);
                // console.log('vjson');
                // console.log(me.getUserId(req));
                request.post({
                        headers: {
                            'Authorization': me.getUserId(req)
                        },
                        url: ( (me.debug && option.url) ? option.url : me.getUrl(req)) +
                            (option.paths ? option.paths['p_' + req.session.user.level] : option.path),
                        form: {postjson: JSON.stringify(req.body)}},
                    function (err, httpResponse, body) {
                        if (err || httpResponse.statusCode != 200) {
                            res.send(JSON.stringify({code:-1}));
//                            res.end();
                        } else {
                            callback(body);
                        }

                    });

//                request({
//                        method: 'POST',
//                        url: ( option.url ? option.url : 'http://172.21.120.241:8080') + (option.paths ? option.paths['p_' + req.session.user.level] : option.path),
//                        json: req.body
//                    },
//                    function (err, httpResponse, body) {
//                        console.log(httpResponse.statusCode);
//                        if (err || httpResponse.statusCode != 200) {
//                            res.end();
//                        } else {
////                            console.log('BODY: ');
////                            httpResponse.on('data', function (chunk) {
////                                    console.log('BODY: ' + chunk);
////                                });
//                            callback(body);
//                        }
//
//                    });

            } else {
                if (option.addParams) {
                    var i;
                    for (i in option.addParams) {
                        req.body[i] = option.addParams[i];
                    }
                }
                // console.log('vpost');
                // console.log(req.session.user);
                // console.log('vpost');
                request.post({
                        headers: {
                            'Authorization': me.getUserId(req)
                        },
                        url: ( (me.debug && option.url) ? option.url : me.getUrl(req)) +
                            (option.paths ? option.paths['p_' + req.session.user.level] : option.path),
                        form: req.body},
                    function (err, httpResponse, body) {

                        if (err || httpResponse.statusCode != 200) {
                            res.send(JSON.stringify({code:-1}));
//                            res.end();
                        } else {
                            callback(body);
                        }

                    });
            }

        }

//        console.log('请求参数');
//        console.log(req.method);
//        console.log(req.query);
//        console.log(req.body);
//        console.log('请求参数');
//        var me = this;
//        var httpoption;
//        if (req.method == 'GET') {
//            httpoption = {
//                hostname: option.hostname ? option.hostname : me.hostname,
//                port: option.port ? option.port : me.port,
//                path: option.path + '?' + qs.stringify(req.query),
//                method: req.method
//            }
//        } else {
//            httpoption = {
//                hostname: option.hostname ? option.hostname : me.hostname,
//                port: option.port ? option.port : me.port,
//                path: option.path,
//                method: req.method,
//                headers: {
//                    'Content-Type': 'multipart/form-data'
//                }
//            };
//            console.log(req.file);
//        }
//        var rrr = http.request(httpoption, function (re) {
//            var s = '';
//            re.setEncoding('utf8');
//            re.on('data', function (chunk) {
//                s += chunk;
////                console.log('BODY: ' + s);
//
//            }).on("end", function () {
//                callback(s);
//
//            });
//        });
//
//        rrr.on('error', function (e) {
////            console.log('problem with request: ' + e.message);
//            res.end();
//        });
//        rrr.write(qs.stringify(req.query));
//        rrr.end();
    },
    transfer: function (req, res, option) {
//        console.log(option);
        var me = this;
        me.request(req, res, option, function (s) {
            res.writeHead(200, {'Content-Type': 'application/json'});
            res.end(s);
        });

    }
};