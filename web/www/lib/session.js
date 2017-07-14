module.exports = {
    login: function (req) {
        console.log('logins************************');
        console.log(req.sessionID);
        console.log(req.session);
        console.log('logins************************');
        global.userSessionList[req.session.user.type + req.session.user.id] = {sessionID: req.sessionID};
    },
    check: function (req) {
        if (req.session.user && req.sessionID != global.userSessionList[req.session.user.type + req.session.user.id].sessionID) {
            delete req.session.user;
        }
//        req.session.user = null;
    },
    logout:function (req){
        if(req.session.user){
            delete global.userSessionList[req.session.user.type + req.session.user.id];
            delete req.session.user;
        }

    }
}