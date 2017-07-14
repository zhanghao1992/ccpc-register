function Page(cO, t, callback) {
    var me = this;
    me.cO = cO;
    me.t = t;
    me.callback = callback;
    me.init = function (page_num, page_total) {
        me.cO.html('<span class="page-a"></span>' +
            '<span class="page-i">共<span class="page-total"></span>页，到第<input id="pageTxt" class="page-txt" type="text">页<a class="page-turn" href="javascript:void(0);">确定</a></span>');
        var i;
        var s = '';
        if (page_num > 1) {
            s = s + '<a class="page-prev" href="javascript:void(0);" data-page="' + (page_num - 1) + '">上一页</a>';
        } else {
            s = s + '<a class="page-prev disabled" href="javascript:void(0);">上一页</a>';
        }
        if (page_num > 3 && page_total > 5) {
            s = s + '<a class="page-num" href="javascript:void(0);" data-page="1">' + 1 + '</a>';
            s = s + '...';
        }

        if (page_total >= 5 && page_num >= 3 && page_num + 2 <= page_total) {
            for (i = page_num - 2; i <= page_num + 2; i++) {
                if (i == page_num) {
                    s = s + '<a class="page-num active" href="javascript:void(0);" data-page="' + i + '">' + i + '</a>';
                } else {
                    s = s + '<a class="page-num" href="javascript:void(0);" data-page="' + i + '">' + i + '</a>';
                }
            }
        } else if (page_total < 5) {
            for (i = 1; i <= page_total; i++) {
                if (i == page_num) {
                    s = s + '<a class="page-num active" href="javascript:void(0);" data-page="' + i + '">' + i + '</a>';
                } else {
                    s = s + '<a class="page-num" href="javascript:void(0);" data-page="' + i + '">' + i + '</a>';
                }
            }
        } else if (page_num < 3) {
            for (i = 1; i <= 5; i++) {
                if (i == page_num) {
                    s = s + '<a class="page-num active" href="javascript:void(0);" data-page="' + i + '">' + i + '</a>';
                } else {
                    s = s + '<a class="page-num" href="javascript:void(0);" data-page="' + i + '">' + i + '</a>';
                }
            }

        } else if (page_num + 2 > page_total) {
            for (i = (page_total - 4); i <= page_total; i++) {
                if (i == page_num) {
                    s = s + '<a class="page-num active" href="javascript:void(0);" data-page="' + i + '">' + i + '</a>';
                } else {
                    s = s + '<a class="page-num" href="javascript:void(0);" data-page="' + i + '">' + i + '</a>';
                }
            }
        }
        if (page_total > page_num + 2 && page_total > 5) {
            s = s + '...';
            s = s + '<a class="page-num" href="javascript:void(0);" data-page="' + page_total + '">' + page_total + '</a>';
        }
        if (page_num < page_total) {
            s = s + '<a class="page-next" href="javascript:void(0);" data-page="' + (page_num + 1) + '">下一页</a>';
        } else {
            s = s + '<a class="page-next disabled" href="javascript:void(0);">下一页</a>';
        }

        me.cO.find('.page-a').html(s);
        me.cO.find('.page-total').html(page_total);
        me.page_total = page_total;

    };
    me.event = function () {

        me.cO.delegate('.page-a a', 'click', function () {
            var _this = this;
            if (!$(_this).hasClass('disabled') && !$(_this).hasClass('ac')) {
                //me.callback.call(me.t, $(_this).data('page'));
                $(me).trigger('pageTurn', [$(_this).data('page')]);
            }
            return false;
        });
        me.cO.delegate('.page-turn', 'click', function () {
            if (!$.trim(me.cO.find('.page-txt').val())) {
                alert('请输入页码');
                me.cO.find('.page-txt').val('');
                return;
            }
            if (!/^\d{0,}$/.test(me.cO.find('.page-txt').val())) {
                alert('格式不对');
                me.cO.find('.page-txt').val('');
                return;
            }
            var p = parseInt(me.cO.find('.page-txt').val());
            if (p < 1 || p > me.page_total) {
                alert('超出范围');
                me.cO.find('.page-txt').val('');
                return;
            }

            //me.callback.call(me.t, p);
            $(me).trigger('pageTurn', [p]);
            return false;
        });
    };

    me.clear = function () {
        me.cO.html('');
    };

    me.event();

}