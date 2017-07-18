function List(options) {

}
$.extend(List.prototype, {
    init: function (options) {
        var me = this;
        me.options = options;
        if (!me.options.clear) {
            me.options.clear = function () {
            };
        }
        if (!me.options.dealListSuccess) {
            me.options.dealListSuccess = function () {
            };
        }

        me.searchUrl = me.options.searchUrl;
        me.send = me.options.send;
        me.getList(me.send);
        if (!me.options.noPage) {
            me.pageObj = new Page(me.options.pageObj);
            $(me.pageObj).on('pageTurn', function (e, p) {
                if (me.send.page_num) {
                    me.send.page_num = p;
                } else {
                    me.send.page = p;
                }

                me.getList(me.send);
            });
        }

    },
    getList: function (send) {
        var me = this;
        $.ajax({
            url: me.searchUrl,
            type: 'GET',
            data: send,
            dataType: 'json',
            cache: false,
            success: function (json) {
                if (json.code == 0) {
                    me.send = send;
                    if (!json.response || !json.response.list || !json.response.list.length) {
                        me.clear('无数据', json);
                        return;
                    }
                    $(me).trigger('listData', [json]);
                    var page_size;
                    if (json.response.page_size) {
                        page_size = json.response.page_size;
                    } else {
                        page_size = me.send.page_size;
                    }
                    if (!me.options.noPage) {

                        if (me.send.page_num) {
                            me.pageObj.init(me.send.page_num, Math.ceil(json.response.total / page_size));
                        } else {
                            me.pageObj.init(me.send.page, Math.ceil(json.response.total / page_size));
                        }
                    }

                    me.dealList(json);
                } else {
                    me.clear(json.msg);
                }
            },
            error: function () {
                me.clear('出错');
            }
        });
    },
    clear: function (s, json) {
        var me = this;
        me.options.clear(s, json);
        if (!me.options.noPage) {
            me.pageObj.clear();
        }
    },
    dealListSuccess: function (json) {
        var me = this;
        me.options.dealListSuccess(json);
    },
    dealList: function (json) {
        var me = this;
        me.dealListSuccess(json);

        me.options.listCon.html(baidu.template(me.options.listTplId, {data: json}));
        if(me.options.successCallback){
            me.options.successCallback(json);
        }

    }
});
