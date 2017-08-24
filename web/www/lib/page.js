var session = require('./session');
const url = require("url");
var cpList = [
    {
        index: 1,
        cpName: '北京联众互动网络股份有限公司',
        cpId: 'e44ab68b1c7bb15fc7e014103'
    },
    {
        index: 2,
        cpName: '腾讯科技（深圳）有限公司',
        cpId: '584a419472284b39590f88105'
    },
    {
        index: 3,
        cpName: '北京新浪互联信息服务有限公司',
        cpId: '944f8f964634dc2bca99e8109'
    },
    {
        index: 4,
        cpName: '深圳市玩呗娱乐科技有限公司',
        cpId: '2640d6ab6fcfcd89a41d49123'
    },
    {
        index: 5,
        cpName: '深圳市银溪数码技术有限公司',
        cpId: '7e4e50b1166357a640ce03129'
    },
    {
        index: 6,
        cpName: '吉林风雷网络科技股份有限公司',
        cpId: '6a45529cedc5561cf8a215131'
    },
    {
        index: 7,
        cpName: '在线途游',
        cpId: '344a218f47dcb9489cce69135'
    },
    {
        index: 8,
        cpName: '深圳维京人网络科技有限公司',
        cpId: '344a218f47dcb9489cce69135'
    },
    {
        index: 9,
        cpName: '上海波克城市网络科技股份有限公司',
        cpId: '164fffadbec40b80d0d0cb125'
    },
    {
        index: 10,
        cpName: '北京大国小鲜文化传媒有限公司',
        cpId: 'a04dc191dd72187b707158137'
    }
];

module.exports = {
    load: function (req, res, option) {

        // console.log('+++++');
        // console.log(req.session.user);
        // console.log('+++++');
        if (!option.data) {
            option.data = {};
        }

        //厂商cpId是否合法
        var isCp = false;
        cpList.forEach(function (k, index) {
            if (k.cpId == req.query.cpId) {
                isCp = true;
            }
        })

        if (!isCp) {
            res.redirect('/errorCp');
            return false;
        }

        console.log(req.query)

        if (req.session.user) {
            option.data.userInfo = req.session.user;
        }
        res.render(option.path, option.data);
    }
};