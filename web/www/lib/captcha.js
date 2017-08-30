module.exports = {
    expirationTime: 60,
    check: function (req, s) {
        if (!req.session.captcha || !req.session.captcha.value) {
            return {code: 1, msg: '无图形验证码'}
        }
        if (req.session.captcha.createTime + (this.expirationTime * 1000) < new Date().getTime()) {
            return {code: 2, msg: '图形验证码过期'}
        }
        if (req.session.captcha.value.toLowerCase() != s.toLowerCase()) {
            return {code: 3, msg: '图形验证码不正确'}
        }
        if (req.session.captcha.value.toLowerCase() == s.toLowerCase()) {
            return {code: 0, msg: '图形验证通过'}
        }
        return {code: -1, msg: '未知错误'}
    }
};