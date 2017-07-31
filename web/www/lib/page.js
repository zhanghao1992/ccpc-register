var session = require('./session');
module.exports = {
    load: function (req, res, option) {
//        console.log('+++++');
//        console.log(req.session.user);
//        console.log('+++++');
        if (!option.data) {
            option.data = {};
        }
        var associationLevel;
        if (req.session.user && req.session.user && req.session.user.type == 'association' && req.session.user.level) {
            associationLevel = req.session.user.level;
        }
        session.check(req);
        if (!req.session.user && associationLevel) {
            if (associationLevel == 1) {
                res.redirect('/association/weiqi/login');
            } else if (associationLevel == 2) {
                res.redirect('/association/weiqi/locallogin');
            }
            return false;
        }
//        console.log('load session');
//        console.log(req.sessionID);
//        console.log(req.session);
//        console.log('load session');

        // console.log('00000')
        // console.log(req.session.user)
        if (req.session.user) {
            option.data.userInfo = req.session.user;
        }

        if (option.auth == undefined) {
            res.render(option.path, option.data);
        } else {
            if (req.session.user) {
                if (option.auth.type == 'org' || option.auth.type == 'player') {
                    if (req.session.user.type == option.auth.type) {
                        res.render(option.path, option.data);
                    } else {
                        res.redirect('/');
                    }
                } else if (option.auth.type == 'association') {
                    if (req.session.user.limits[option.auth.limit]) {
                        res.render(option.path, option.data);
                    } else {
                        res.send('无权访问');
                    }

//                    var i;
//                    var find = false;
//                    for (i in req.session.user.limits) {
//                        if (req.session.user.limits[i] == option.auth.limit) {
//                            find = true;
//                        }
//                    }
//                    if (find) {
//                        res.render(option.path, option.data);
//                    } else {
//                        res.send('无权访问');
//                    }

                }

            } else {
                if (option.auth.type == 'org' || option.auth.type == 'player') {
                    res.redirect('/');
                } else if (option.auth.type == 'association') {
                    res.redirect('/association/weiqi/login');
                }

            }
        }
    }
};