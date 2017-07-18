var dialogPackage = {
    init: function () {
        var me = this;
        me.target = new Dialog($('#dialogPackage'));
        $('#dialogPackage .confirm').click(function () {
            me.confirm();
            return false;
        });
        $('#dialogPackage .dialog-top .close,#dialogPackage .cancel').click(function () {
            me.close();
            return false;
        });
    },
    show: function (s, option) {
        var me = this;
        if (option) {
            if (option.type) {
                $('#dialogPackage .dialog-p').removeClass().addClass('dialog-p').addClass('dialog-p-' + option.type);
            }
            if (option.cancelBtn) {
                $('#dialogPackage .cancel').removeClass('hide');
            } else {
                $('#dialogPackage .cancel').addClass('hide');
            }
            if (option.confirmBtnTxt) {
                $('#dialogPackage .confirm').html(option.confirmBtnTxt);
            }

        } else {
            $('#dialogPackage .dialog-p').removeClass().addClass('dialog-p');
            $('#dialogPackage .cancel').addClass('hide');
        }
        $('#dialogPackage .dialog-con .dialog-p').html(s);
        me.target.show();
    },
    hide: function () {
        var me = this;
        me.target.hide();
        $('#dialogPackage .dialog-con .dialog-p').html('').removeClass().addClass('dialog-p');
        $('#dialogPackage .confirm').html('确认');
    },
    close: function () {
        var me = this;

        $(me).trigger('dialogPackageClose');
        $(dialogPackage).off('dialogPackageConfirm');
        $(dialogPackage).off('dialogPackageClose');
        me.hide();
    },
    confirm: function () {
        var me = this;

        $(me).trigger('dialogPackageConfirm');
        $(dialogPackage).off('dialogPackageConfirm');
        $(dialogPackage).off('dialogPackageClose');
        me.hide();
    },
    setBtnTxt: function (s) {
        $('#dialogPackage .confirm').html(s);
    }
};