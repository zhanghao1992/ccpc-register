var dialogPackage={init:function(){var a=this;a.target=new Dialog($("#dialogPackage")),$("#dialogPackage .confirm").click(function(){return a.confirm(),!1}),$("#dialogPackage .dialog-top .close,#dialogPackage .cancel").click(function(){return a.close(),!1})},show:function(a,o){var i=this;o?(o.type&&$("#dialogPackage .dialog-p").removeClass().addClass("dialog-p").addClass("dialog-p-"+o.type),o.cancelBtn?$("#dialogPackage .cancel").removeClass("hide"):$("#dialogPackage .cancel").addClass("hide"),o.confirmBtnTxt&&$("#dialogPackage .confirm").html(o.confirmBtnTxt)):($("#dialogPackage .dialog-p").removeClass().addClass("dialog-p"),$("#dialogPackage .cancel").addClass("hide")),$("#dialogPackage .dialog-con .dialog-p").html(a),i.target.show()},hide:function(){var a=this;a.target.hide(),$("#dialogPackage .dialog-con .dialog-p").html("").removeClass().addClass("dialog-p"),$("#dialogPackage .confirm").html("确认")},close:function(){var a=this;$(a).trigger("dialogPackageClose"),$(dialogPackage).off("dialogPackageConfirm"),$(dialogPackage).off("dialogPackageClose"),a.hide()},confirm:function(){var a=this;$(a).trigger("dialogPackageConfirm"),$(dialogPackage).off("dialogPackageConfirm"),$(dialogPackage).off("dialogPackageClose"),a.hide()},setBtnTxt:function(a){$("#dialogPackage .confirm").html(a)}};