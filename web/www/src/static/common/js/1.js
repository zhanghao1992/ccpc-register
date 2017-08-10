var badgesList = [
    {
        levelName: '终身特级大师',
        gradeCode: 101,
        badgeSrc: '/static/common/images/badges/zstj.png'
    }
];
var cpList = [
    {
        cpName: '北京联众互动网络股份有限公司',
        QRsrc: '/static/common/images/QRcodes/lzaz.png',
        cpId: 'e44ab68b1c7bb15fc7e014103'
    }
];

//徽章图片
function getBadgeImg(imgEle, levelCode) {
    $.each(badgesList, function (k, v) {
        if (v.gradeCode == levelCode) {
            $(imgEle).prop('src', v.badgeSrc);
        }
    })
}