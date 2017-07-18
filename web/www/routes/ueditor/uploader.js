var fs = require('fs');
var path = require('path');
module.exports = function Uploader(fileField, config, type, req, res) {

    var self = this;

    self.fileField = fileField;
    self.config = config;
    self.type = type ? type : 'upload';
    self.stateMap = {
        s0: "SUCCESS", //上传成功标记，在UEditor中内不可改变，否则flash判断会出错
        s1: "文件大小超出 upload_max_filesize 限制",
        s2: "文件大小超出 MAX_FILE_SIZE 限制",
        s3: "文件未被完整上传",
        s4: "没有文件被上传",
        s5: "上传文件为空",
        "ERROR_TMP_FILE": "临时文件错误",
        "ERROR_TMP_FILE_NOT_FOUND": "找不到临时文件",
        "ERROR_SIZE_EXCEED": "文件大小超出网站限制",
        "ERROR_TYPE_NOT_ALLOWED": "文件类型不允许",
        "ERROR_CREATE_DIR": "目录创建失败",
        "ERROR_DIR_NOT_WRITEABLE": "目录没有写权限",
        "ERROR_FILE_MOVE": "文件保存时出错",
        "ERROR_FILE_NOT_FOUND": "找不到上传文件",
        "ERROR_WRITE_CONTENT": "写入文件内容错误",
        "ERROR_UNKNOWN": "未知错误",
        "ERROR_DEAD_LINK": "链接不可用",
        "ERROR_HTTP_LINK": "链接不是http链接",
        "ERROR_HTTP_CONTENTTYPE": "链接contentType不正确"
    };
    self.getStateInfo = function (errCode) {
        return !self.stateMap[errCode] ? self.stateMap["ERROR_UNKNOWN"] : self.stateMap[errCode];
    };
    self.getFileExt = function () {
        return (self.oriName.substring(self.oriName.lastIndexOf('.'))).toLocaleLowerCase();
    };
    self.getFullName = function () {
        var format = self.config["pathFormat"];
        var date = new Date();
        var monthArr = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];
        format = format.replace('{yyyy}', date.getFullYear());
        format = format.replace('{yy}', ('' + date.getFullYear()).substring(2));
        format = format.replace('{mm}', monthArr[date.getMonth()]);
        format = format.replace('{dd}', date.getDate() > 9 ? '' + date.getDate() : '0' + date.getDate());
        format = format.replace('{hh}', date.getHours() > 9 ? '' + date.getHours() : '0' + date.getHours());
        format = format.replace('{ii}', date.getMinutes() > 9 ? '' + date.getMinutes() : '0' + date.getMinutes());
        format = format.replace('{ss}', date.getSeconds() > 9 ? '' + date.getSeconds() : '0' + date.getSeconds());
        format = format.replace('{time}', date.getTime());
        //过滤文件名的非法自负,并替换文件名
        var oriName = self.oriName.substring(0, self.oriName.lastIndexOf('.'));
        oriName = oriName.replace(/[\|\?\"\<\>\/\*\\\\]+/g, '');
        format = format.replace('{filename}', oriName);
        var randCon = parseInt((format.match(/\{rand\:([\d]*)\}/))[1]);
        var randNum = '';
        for (var i = 0; i < randCon; i++) {
            randNum = randNum + '' + Math.floor(10 * Math.random());
        }
        format = format.replace(/\{rand\:[\d]*\}/, randNum);
        var ext = self.getFileExt();
        return format + ext;
    };
    self.getFilePath = function () {
        var fullname = self.fullName;
        var rootPath = global.projectRoot;//todo根目录有问题
        if (fullname.charAt(0) != '/') {
            fullname = '/' + fullname;
        }

        return rootPath + fullname;
    };
    self.getFileName = function () {
        return self.filePath.substring(self.filePath.lastIndexOf('/') + 1);
    };
    self.checkSize = function () {
        return self.fileSize <= (self.config.maxSize);
    };
    self.checkType = function () {
        var checkResult = false;
        for (var i = 0; i < self.config.allowFiles.length; i++) {
            if (self.fileType == self.config.allowFiles[i]) {
                checkResult = true;
                break;
            }
        }
        return checkResult;
    };
    self.getFileInfo = function () {
        return {
            state: self.stateInfo,
            url: self.fullName,
            title: self.fileName,
            original: self.oriName,
            type: self.fileType,
            size: self.fileSize
        };
    };
    self.end = function () {
        if (req.query.callback) {
            res.end(req.query.callback + '(' + JSON.stringify(self.getFileInfo()) + ')');
        } else {
            res.end(JSON.stringify(self.getFileInfo()));
        }
    };
    self.saveFile = function () {
        //保存文件todo
//        console.log('试试取global');
//        console.log(global.projectRoot);
        fs.writeFile(self.filePath, req.file.buffer,{encoding:'utf8'}, function (err) {
            if (err) {

//                console.log('保存出错');
//                console.log(self.filePath);
//                console.log(err);
                self.stateInfo = self.getStateInfo("ERROR_FILE_MOVE");
            } else {
                self.stateInfo = self.stateMap.s0;
            }
            self.end();
        });
    };
    self.upFile = function () {
        var file = self.file = req.file;
//        console.log('+-+-+-+-+-');
//        console.log(file);
        if (!file) {
            self.stateInfo = self.getStateInfo("ERROR_FILE_NOT_FOUND");
            self.end();
            return;
        }
        self.oriName = file.originalname;
        self.fileSize = file.size;
        self.fileType = self.getFileExt();
        self.fullName = self.getFullName();
        self.filePath = self.getFilePath();
        self.fileName = self.getFileName();
        var dirname = path.dirname(self.filePath);
        //检查文件大小是否超出限制
        if (!self.checkSize()) {
            self.stateInfo = self.getStateInfo("ERROR_SIZE_EXCEED");
            self.end();
            return;
        }
        //检查是否不允许的文件格式
        if (!self.checkType()) {
            self.stateInfo = self.getStateInfo("ERROR_TYPE_NOT_ALLOWED");
            self.end();
            return;
        }
        fs.access(dirname, fs.F_OK, function (err) {
//            console.log('检查路径err');
//            console.log(dirname);
//            console.log(err);
            if (err) {
                fs.mkdir(dirname, function (err) {
                    if (err) {
//                        console.log('创建路径err');
//                        console.log(err);
                        self.stateInfo = self.getStateInfo("ERROR_CREATE_DIR");
                        self.end();
                    } else {
                        self.saveFile();
                    }
                });

            } else {
                self.saveFile();
            }
        });

    };

    if (type == 'remote') {
        self.saveRemote();
    } else if (type == 'base64') {
        self.upBase64();
    } else {
        self.upFile();
    }

};