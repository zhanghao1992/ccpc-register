var session = require('./session');
const url = require("url");
var cpList = [
    {
        cpName: '北京联众互动网络股份有限公司',
        QRsrc: '/static/common/images/QRcodes/lzaz.png',
        cpId: 'e44ab68b1c7bb15fc7e014103'
    },
    {
        cpName: '腾讯科技（深圳）有限公司',
        QRsrc: '/static/common/images/QRcodes/tx.jpg',
        cpId: '584a419472284b39590f88105'
    },
    {
        cpName: '北京新浪互联信息服务有限公司',
        QRsrc: '/static/common/images/QRcodes/xlaz.png',
        cpId: '944f8f964634dc2bca99e8109'
    },
    {
        cpName: '深圳市玩呗娱乐科技有限公司',
        QRsrc: '/static/common/images/QRcodes/wb.png',
        cpId: '2640d6ab6fcfcd89a41d49123'
    },
    {
        cpName: '深圳市东方博雅科技有限公司',
        QRsrc: '/static/common/images/QRcodes/by.jpg',
        cpId: '5d45b39efdf5004b0fe5a8127'
    },
    {
        cpName: '深圳市银溪数码技术有限公司',
        QRsrc: '/static/common/images/QRcodes/yx.jpg',
        cpId: '7e4e50b1166357a640ce03129'
    },
    {
        cpName: '吉林风雷网络科技股份有限公司',
        QRsrc: '/static/common/images/QRcodes/flewm.png',
        cpId: '6a45529cedc5561cf8a215131'
    },
    {
        cpName: '深圳维京人网络科技有限公司',
        QRsrc: '/static/common/images/QRcodes/qp.png',
        cpId: '344a218f47dcb9489cce69135'
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