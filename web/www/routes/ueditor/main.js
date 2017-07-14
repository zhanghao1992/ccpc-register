var express = require('express');
var fs = require('fs');
var path = require('path');
var router = express.Router();
var multer = require("multer");
var upload = multer();

var configS = (fs.readFileSync(path.join(__dirname, 'config.json'), 'utf8')).replace(/\/\*[\s\S]+?\*\//g, '');
var config = JSON.parse(configS);
var action_upload = require('./action_upload.js');
router.use('/ue', upload.single('upfile'), function (req, res) {
//    console.log('****');
//    console.log(req.query.action);
//    console.log('****');
    var result;
    switch (req.query.action) {
        case 'config':
            result = configS;
            if (req.query.callback) {
                res.end(req.query.callback + '(' + result + ')');
            } else {
                res.end(result);
            }
            break;
        /* 上传图片 */
        case 'uploadimage':
        /* 上传涂鸦 */
        case 'uploadscrawl':
        /* 上传视频 */
        case 'uploadvideo':
        /* 上传文件 */
        case 'uploadfile':
            action_upload.go(req, res, config);
            break;
        default:
            result = JSON.stringify({
                state: '请求地址出错'
            });
            if (req.query.callback) {
                res.end(req.query.callback + '(' + result + ')');
            } else {
                res.end(result);
            }
            break;
    }

});
module.exports = router;