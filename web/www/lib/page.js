var session = require('./session');
const url = require("url");
var cpList = [
    {
        index: 1,
        cpName: '北京联众互动网络股份有限公司',
        cpId: 'e44ab68b1c7bb15fc7e01410301'
    },
    {
        index: 2,
        cpName: '腾讯科技（深圳）有限公司（手Q）',
        cpId: '584a419472284b39590f8810506'
    },
    {
        index: 3,
        cpName: '腾讯科技（深圳）有限公司（微信）',
        cpId: '4a4f2ebd196a1692c2ca8010505'
    },
    {
        index: 4,
        cpName: '北京新浪互联信息服务有限公司',
        cpId: '944f8f964634dc2bca99e810902'
    },
    {
        index: 5,
        cpName: '深圳市玩呗娱乐科技有限公司',
        cpId: '2640d6ab6fcfcd89a41d4912301'
    },
    {
        index: 6,
        cpName: '深圳市银溪数码技术有限公司',
        cpId: '7e4e50b1166357a640ce0312901'
    },
    {
        index: 7,
        cpName: '吉林风雷网络科技股份有限公司',
        cpId: '6a45529cedc5561cf8a21513101'
    },
    {
        index: 8,
        cpName: '在线途游',
        cpId: '1244dcb577b471777e613e11101'
    },
    {
        index: 9,
        cpName: '深圳维京人网络科技有限公司',
        cpId: '344a218f47dcb9489cce6913501'
    },
    {
        index: 10,
        cpName: '上海波克城市网络科技股份有限公司',
        cpId: '164fffadbec40b80d0d0cb12501'
    },
    {
        index: 11,
        cpName: '北京大国小鲜文化传媒有限公司',
        cpId: 'a04dc191dd72187b70715813701'
    },
    {
        index: 12,
        cpName: '天津中棋惟业体育发展有限公司',
        cpId: 'e943cc98cc4c7b66708f2d10702'
    },
    {
        index: 13,
        cpName: '北京诚誉天下科技有限公司（皮皮）',
        cpId: 'cf4cb7b12efb1501892b7e13301'
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