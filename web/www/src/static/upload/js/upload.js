function Upload(option) {
    var objC = $('#' + option.container);
    objC.addClass('upload').addClass('clearfix');
    var load = 0;
    var place = $('<div>');
    objC.html(place);

    var inputObj = option.inputObj;
//    var inputObj = $('<input type="hidden" name="' + option.inputName + '" class="' + option.inputClass + '">');
//    inputObj.insertBefore(place);

    var id = 0;
    while ($('#uploadAdd' + id).length) {
        id++;
    }
    var addCon = $('<div>');
    var addObj = $('<a id="uploadAdd' + id + '" class="upload-add" href="javascript:void(0);">添加文件</a>');
    this.addObj = addObj;
    addCon.html(addObj);
    $('<div class="clearfix">').append(addCon).append($('<div class="upload-msg">最大支持20MB的附件</div>')).insertBefore(place);
    var me = this;
    me.setInput = function () {
        if (me.data.length) {
            inputObj.val(JSON.stringify(me.data));
        } else {
            inputObj.val('');
        }

    };
    me.data = [];
    me.fileNum = 0;
    //new
    var pluploadOption = {
        runtimes: 'html5,flash',
        browse_button: 'uploadAdd' + id, // you can pass an id...
        container: addCon[0], // you can pass an id...
        url: option.uploadUrl, //接收上传的服务端url
        flash_swf_url: option.flashUrl,
        file_data_name: 'file',
        filters: {
            max_file_size: '20mb',
            mime_types: [
                {title: "Image files", extensions: "jpg,png,jpeg"},
                {title: "Image files", extensions: "PGN,sgf"},
                {title: "Zip files", extensions: "zip"},
                {title: "Excel files", extensions: "xls,xlsx"},
                {title: "Word files", extensions: "docx"},
                {title: "Pdf files", extensions: "pdf"},
                {title: "Txt files", extensions: "txt"},

            ]
        },

        init: {
            PostInit: function () {
                if (!load && option.fileArr) {
                    load = 1;
                    $.each(option.fileArr, function (k, v) {
                        $('<div class="upload-block fl clearfix">' +
                            '<a href="javascript:void(0);" class="upload-dele">删除</a>' +
                            '<span class="upload-icon"></span>' +
                            '<div class="upload-info">' +
                            '<div class="upload-name">' + v.fileName + '</div>' +
                            '<div class="upload-status"><a target="_blank" href="/common/getFile?fileMD5=' + v.fileMD5 + '">查看</a></div>' +
                            '</div>' +
                            '</div>').insertBefore(place);
                    });
                    me.data = option.fileArr;
                    me.fileNum = me.data.length;
                    me.setInput();

                }
            },

            FilesAdded: function (up, files) {
                //console.log(me.data.length);
                if (me.fileNum == option.limit) {
                    alert('超出限制');
                    uploader.removeFile(files[0]);

                    return false;
                }
                me.fileNum++;
                if (option.paramEleSelect) {
                    uploader.settings.multipart_params = {};
                    $.each($(option.paramEleSelect), function (k, v) {
                        if ($(v).prop('type') == 'radio') {
                            if ($(v).prop('checked')) {
                                uploader.settings.multipart_params[$(v).prop('name')] = $(v).val();
                            }
                        } else if ($(v).prop('type') == 'checkbox') {
                            if ($(v).prop('checked')) {
                                uploader.settings.multipart_params[$(v).prop('name')] = $(v).val();
                            }
                        } else {
                            uploader.settings.multipart_params[$(v).prop('name')] = $(v).val();
                        }

                    });
                }
                $('<div class="upload-block fl clearfix">' +
                    '<a href="javascript:void(0);" class="upload-dele">删除</a>' +
                    '<span class="upload-icon"></span>' +
                    '<div class="upload-info">' +
                    '<div class="upload-name">' + files[0].name + '</div>' +
                    '<div class="upload-status">' +
                    '<div class="upload-percent-box"><div class="upload-percent"></div></div><div class="upload-percent-txt">0%</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>').insertBefore(place);
                uploader.start();
            },
            UploadProgress: function (up, file) {
                objC.find('.upload-percent').css({
                    width: file.percent + '%'
                });
                objC.find('.upload-percent-txt').html(file.percent + '%');
            },
            FileUploaded: function (up, file, res) {
                var json = JSON.parse(res.response);

//todotodo
                if (json.code != 0) {
                    alert('上传出错');
                    objC.find('.upload-block:last').remove();
                    me.fileNum--;
                    return;
                }
                me.data.push({
                    fileName: json.response.fileName,
                    fileMD5: json.response.fileMD5
                });
                me.setInput();
                addObj.parents('.form-sub').find('.form-msg').removeClass('form-txt-w').addClass('hide').html('');
                objC.find('.upload-block:last').remove();
                $('<div class="upload-block fl clearfix">' +
                    '<a href="javascript:void(0);" class="upload-dele">删除</a>' +
                    '<span class="upload-icon"></span>' +
                    '<div class="upload-info">' +
                    '<div class="upload-name">' + json.response.fileName + '</div>' +
                    '<div class="upload-status"><a target="_blank" href="/common/getFile?fileMD5=' + json.response.fileMD5 + '">查看</a><span style="margin-left: 30px;display: inline-block;color:#008000">上传成功</span></div>' +
                    '</div>' +
                    '</div>').insertBefore(place);
                if (option.callback) {
                    option.callback({
                        fileName: json.response.fileName,
                        fileMD5: json.response.fileMD5
                    });
                }

            },
            UploadComplete: function (up, files) {
//                statusObj.hide();
//                statusSubObj.css({
//                    width: '0%'
//                });
            },
            Error: function (up, err) {
                //alert(err.code + ": " + err.message);
                if(err.code == -600){
                    alert('请上传小于20MB的文件')
                }else{
                    alert(err.message);
                }
//                me.clear();
            }
        }
    };

    if (option.file_type && option.file_type == 'excel') {
        pluploadOption.filters.mime_types = [
            {title: "Excel files", extensions: "xls,xlsx"}
        ];
    }
    if (option.param) {
        pluploadOption.url += $.param(option.param);
    }
    var uploader = new plupload.Uploader(pluploadOption);
    uploader.init();
//    var me = this;
    me.clear = function () {

        inputObj.val('');
        objC.find('.upload-block').remove();

    };
    objC.delegate('.upload-dele', 'click', function () {
        var _this = this;
        var index = objC.find('.upload-dele').index(_this);
        objC.find('.upload-block:eq(' + index + ')').remove();
        me.data.splice(index, 1);
        me.fileNum--;
        me.setInput();
        return false;
    });

}