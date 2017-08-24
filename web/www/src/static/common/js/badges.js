var badgesList = [
    {
        levelName: '终身特级大师',
        gradeCode: 101,
        badgeSrc: __uri('/static/common/images/badges/zstj.png')
    },
    {
        levelName: '五星特级大师',
        gradeCode: 102,
        badgeSrc: __uri('/static/common/images/badges/xjtj5.png')
    },
    {
        levelName: '四星特级大师',
        gradeCode: 103,
        badgeSrc: __uri('/static/common/images/badges/xjtj4.png')
    },
    {
        levelName: '三星特级大师',
        gradeCode: 104,
        badgeSrc: __uri('/static/common/images/badges/xjtj3.png')
    },
    {
        levelName: '二星特级大师',
        gradeCode: 105,
        badgeSrc: __uri('/static/common/images/badges/xjtj2.png')
    },
    {
        levelName: '一星特级大师',
        gradeCode: 106,
        badgeSrc: __uri('/static/common/images/badges/xjtj1.png')
    },
    {
        levelName: '四星国家大师',
        gradeCode: 107,
        badgeSrc: __uri('/static/common/images/badges/xj4.png')
    },
    {
        levelName: '三星国家大师',
        gradeCode: 108,
        badgeSrc: __uri('/static/common/images/badges/xj3.png')
    },
    {
        levelName: '二星国家大师',
        gradeCode: 109,
        badgeSrc: __uri('/static/common/images/badges/xj2.png')
    },
    {
        levelName: '一星国家大师',
        gradeCode: 110,
        badgeSrc: __uri('/static/common/images/badges/xj1.png')
    },
    {
        levelName: '一级牌手',
        gradeCode: 111,
        badgeSrc: __uri('/static/common/images/badges/ps1.png')
    },
    {
        levelName: '二级牌手',
        gradeCode: 112,
        badgeSrc: __uri('/static/common/images/badges/ps2.png')
    },
    {
        levelName: '三级牌手',
        gradeCode: 113,
        badgeSrc: __uri('/static/common/images/badges/ps3.png')
    },
    {
        levelName: '四级牌手',
        gradeCode: 114,
        badgeSrc: __uri('/static/common/images/badges/ps4.png')
    },
    {
        levelName: '五级牌手',
        gradeCode: 115,
        badgeSrc: __uri('/static/common/images/badges/ps5.png')
    },
    {
        levelName: '无等级牌手',
        gradeCode: 116,
        badgeSrc: __uri('/static/common/images/badges/wups.png')
    }
];
var cpList = [
    {
        index: 1,
        cpName: '北京联众互动网络股份有限公司',
        QRsrc: __uri('/static/common/images/QRcodes/lzaz.png'),
        cpId: 'e44ab68b1c7bb15fc7e014103'
    },
    {
        index: 2,
        cpName: '腾讯科技（深圳）有限公司',
        QRsrc: __uri('/static/common/images/QRcodes/tx.jpg'),
        cpId: '584a419472284b39590f88105'
    },
    {
        index: 3,
        cpName: '北京新浪互联信息服务有限公司',
        QRsrc: __uri('/static/common/images/QRcodes/xlaz.png'),
        cpId: '944f8f964634dc2bca99e8109'
    },
    {
        index: 4,
        cpName: '深圳市玩呗娱乐科技有限公司',
        QRsrc: __uri('/static/common/images/QRcodes/wb.png'),
        cpId: '2640d6ab6fcfcd89a41d49123'
    },
    {
        index: 5,
        cpName: '深圳市银溪数码技术有限公司',
        QRsrc: __uri('/static/common/images/QRcodes/yinxi.png'),
        cpId: '7e4e50b1166357a640ce03129'
    },
    {
        index: 6,
        cpName: '吉林风雷网络科技股份有限公司',
        QRsrc: __uri('/static/common/images/QRcodes/flewm.png'),
        cpId: '6a45529cedc5561cf8a215131'
    },
    {
        index: 7,
        cpName: '在线途游',
        QRsrc: __uri('/static/common/images/QRcodes/tuyou.jpg'),
        cpId: '344a218f47dcb9489cce69135'
    },
    {
        index: 8,
        cpName: '深圳维京人网络科技有限公司',
        QRsrc: __uri('/static/common/images/QRcodes/qp.png'),
        cpId: '344a218f47dcb9489cce69135'
    },
    {
        index: 9,
        cpName: '上海波克城市网络科技股份有限公司',
        QRsrc: __uri('/static/common/images/QRcodes/pike.png'),
        cpId: '164fffadbec40b80d0d0cb125'
    },
    {
        index: 10,
        cpName: '北京大国小鲜文化传媒有限公司',
        QRsrc: __uri('/static/common/images/QRcodes/dgxx.png'),
        cpId: 'a04dc191dd72187b707158137'
    }
];

// function __uri(src) {
//     return src;
// }

//徽章图片
function getBadgeImg(imgEle, levelCode) {
    $.each(badgesList, function (k, v) {
        if (v.gradeCode == levelCode) {
            $(imgEle).prop('src', v.badgeSrc);
        }
    })
}