var Uploader = require('./uploader.js');
module.exports = {

    go: function (req, res, config) {
        var config0, fieldName, base64;
        switch (req.query.action) {
            case 'uploadimage':
                config0 = {
                    pathFormat: config.imagePathFormat,
                    maxSize: config.imageMaxSize,
                    allowFiles: config.imageAllowFiles
                };
                fieldName = config.imageFieldName;
                break;
            case 'uploadscrawl':
                config0 = {
                    pathFormat: config.scrawlPathFormat,
                    maxSize: config.scrawlMaxSize,
                    allowFiles: config.scrawlAllowFiles,
                    oriName: 'scrawl.png'
                };
                fieldName = config.scrawlFieldName;
                base64 = 'base64';
                break;
            case 'uploadvideo':

                config0 = {
                    pathFormat: config.videoPathFormat,
                    maxSize: config.videoMaxSize,
                    allowFiles: config.videoAllowFiles
                };
                fieldName = config.videoFieldName;

                break;
            case 'uploadfile':
            default:
                config0 = {
                    pathFormat: config.filePathFormat,
                    maxSize: config.fileMaxSize,
                    allowFiles: config.fileAllowFiles
                };
                fieldName = config.fileFieldName;

                break;
        }
        /* 生成上传实例对象并完成上传 */
        var up = new Uploader(fieldName, config0, base64, req, res);
//        console.log('+++++');
//        console.log(up.a);
    }
};