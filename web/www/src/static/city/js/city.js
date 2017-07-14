function City(options) {
    var me = this;
    me.options = options;
    me.items = $('<div class="city-is">');
    me.items.insertBefore(me.options.cityConObj);

    me.selects = $('<div class="city-list hide">');
    me.selects.html('<div class="city-option"><a class="city-confirm" href="javascript:void(0);">确定</a><div class="city-msg"></div></div>');
    me.selects.insertBefore(me.options.addBtnObj);

    $(document).on('mousedown.city', function (e) {
        if (!me.selects[0].contains(e.target)) {
            me.close();
        }

    });

    me.msg = function (s) {
        $('.city-msg').html(s);
    };

    me.delegateNext = function (o) {
        if (o.next().length) {
            me.delegateNext(o.next());
        }
        o.remove();
    };
    me.items.delegate('.city-d', 'click', function () {
        $(this).parent().remove();
        me.doTrigger();
        return false;
    });
    me.close = function () {
        me.selects.addClass('hide').html('<div class="city-option"><a class="city-confirm" href="javascript:void(0);">确定</a><div class="city-msg"></div></div>');
    };
    me.doTrigger = function () {
        var idlist = '';
        $.each(me.items.find('.city-i'), function (k, v) {
            if (k == 0) {
                idlist = idlist + $(v).data('id');
            } else {
                idlist = idlist + ',' + $(v).data('id');
            }
        });
        me.options.inputObj.val(idlist);
        $(me).trigger('cityChange', [idlist]);
    };
    if (me.options.location) {
        var inits = '';
        var initIdList = '';
        $.each(me.options.location, function (k, v) {
            inits = inits + '<span class="city-i" data-id="' + v.id + '">' + v.txt + '<a class="city-d" href="javascript:void(0);">x</a></span>';
            if (k == 0) {
                initIdList = initIdList + v.id;
            } else {
                initIdList = initIdList + ',' + v.id;
            }
        });
        me.items.append(inits);

        me.doTrigger();
    }
    me.selects.delegate('.city-confirm', 'click', function () {
        if (!options.number || $('.city-list-p .active').length == options.number) {
            me.msg('');
            var s = '';
            $.each($('.city-list-p .active'), function (k, v) {
                if (k != 0) {
                    s = s + '-';
                }
                s = s + $(v).html();
            });
            me.items.append('<span class="city-i" data-id="' + $('.city-list-p .active:last').data('id') + '">' + s + '<a class="city-d" href="javascript:void(0);">x</a></span>');
            if (me.items.find('.city-i').length > me.options.limit) {
                me.items.find('.city-i:first').remove();
            }
            var idlist = '';
            $.each(me.items.find('.city-i'), function (k, v) {
                if (k == 0) {
                    idlist = idlist + $(v).data('id');
                } else {
                    idlist = idlist + ',' + $(v).data('id');
                }
            });
            $(me).trigger('cityChange', [idlist]);

            me.options.inputObj.val(idlist);
            me.options.inputObj.parents('.form-sub').find('.form-msg').removeClass('form-msg-w').addClass('hide').html('');
            me.options.inputObj.parents('.form-sub').find('.form-txt').removeClass('form-txt-w');
            me.options.inputObj.parents('.form-sub').find('.form-msg-m').addClass('hide');

            me.close();
        } else {
            me.msg('请选择')
        }
        return false;
    });
    me.selects.delegate('.city-list-p a', 'click', function () {
        var _this = this;
        $(_this).parent().find('a').removeClass('active');
        $(_this).addClass('active');
        me.delegateNext($(_this).parent().next());
        me.selects.append($('<div class="city-list-line"></div>'));
        if (me.options.number && $('.city-list-p .active').length >= me.options.number) {
            return false;
        }
        me.getP({parentCode: $(_this).data('id')});
        return false;
    });

    me.getP = function (send) {
        if (me.options.number && $('.city-list-p .active').length >= me.options.number) {
            return false;
        }
        $.ajax({
            url: '/common/city?typeCode=city',
            type: 'get',
            data: send,
            dataType: 'json',
            cache: false,
            success: function (json) {
                me.detail(json);
            },
            error: function () {

            }

        });
    };
    me.detail = function (json) {
        if (!json.response || !json.response.length) {
            return false;
        }
        var s = '<div class="city-list-p">';
        $.each(json.response, function (k, v) {
            s = s + '<a href="javascript:void(0);" data-id="' + v.code + '">' + v.value + '</a>';
        });
        s = s + '</div>';
        var obj = $(s);
        var lineObj = $('<div class="city-list-line"></div>');
        me.selects.append(obj).append(lineObj).removeClass('hide');
        if (json.response.length == 1) {
            obj.find('a').addClass('active');
            me.getP({parentCode: obj.find('a').data('id')});
        }
    };

    me.options.addBtnObj.click(function () {
        me.getP({parentCode: ''});
        return false;
    });

}