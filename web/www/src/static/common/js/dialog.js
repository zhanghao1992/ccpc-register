function Dialog(o, option) {
    var me = this;

    me.ie6 = !-[1, ] && !window.XMLHttpRequest;
    me.o = o;
    me.option = option;
    me.bg = $('<div class="hide"></div>');
    me.isOffHeight = 0;
    $(window).on('resize.dialogCheck', function () {
        me.checkHeight();
    });
    if (me.ie6) {
        me.o.css({
            'position': 'absolute',
            'top': ((document.documentElement.clientHeight - me.o.outerHeight()) / 2) + document.documentElement.scrollTop,
            'left': ((document.documentElement.clientWidth - me.o.outerWidth()) / 2) + document.documentElement.scrollLeft,
            'z-index': '9999'
        });
        $(window).on('resize.dialog', function () {
            if (!me.isOffHeight) {
                me.o.css({
                    'top': ((document.documentElement.clientHeight - me.o.outerHeight()) / 2) + document.documentElement.scrollTop,
                    'left': ((document.documentElement.clientWidth - me.o.outerWidth()) / 2) + document.documentElement.scrollLeft
                });
            }
            me.bg.css({
                'width': document.documentElement.clientWidth,
                'height': document.documentElement.clientHeight,
                'top': document.documentElement.scrollTop,
                'left': document.documentElement.scrollLeft
            });
        });
        $(window).on('scroll.dialog', function () {
            if (!me.isOffHeight) {
                me.o.css({
                    'top': ((document.documentElement.clientHeight - me.o.outerHeight()) / 2) + document.documentElement.scrollTop,
                    'left': ((document.documentElement.clientWidth - me.o.outerWidth()) / 2 ) + document.documentElement.scrollLeft
                });
            }
            me.bg.css({
                'top': document.documentElement.scrollTop,
                'left': document.documentElement.scrollLeft
            });
        });
    } else {
        me.o.css({
            'position': 'fixed',
            'top': '50%',
            'left': '50%',
            'z-index': '9999'
        });
    }

    me.o.addClass('hide');
    me.show = function () {
        me.o.removeClass('hide');
        me.bg.removeClass('hide');

        if (me.ie6) {
            me.o.css({
                'top': ((document.documentElement.clientHeight - me.o.outerHeight()) / 2) + document.documentElement.scrollTop,
                'left': ((document.documentElement.clientWidth - me.o.outerWidth()) / 2) + document.documentElement.scrollLeft
            });
            me.bg.css({
                'width': document.documentElement.clientWidth,
                'height': document.documentElement.clientHeight,
                'top': document.documentElement.scrollTop,
                'left': document.documentElement.scrollLeft
            });
        } else {
            me.o.css({
                'margin-left': '-' + (me.o.width() / 2) + 'px',
                'margin-top': '-' + (me.o.height() / 2) + 'px'
            });
        }
        me.checkHeight();
    };
    me.hide = function () {
        me.o.addClass('hide');
        me.bg.addClass('hide');
    };
    me.createBg = function () {

        $(document).ready(function () {

            if (me.ie6) {
                me.bg.css({
                    'width': document.documentElement.clientWidth,
                    'height': document.documentElement.clientHeight,
                    'position': 'absolute',
                    'top': '0',
                    'left': '0',
                    'z-index': '999',
                    'background-color': '#000000',
                    'opacity': '0.5',
                    '-moz-opacity': '0.5',
                    'filter': 'alpha(Opacity=50)'
                });
            } else {
                me.bg.css({
                    'width': '100%',
                    'height': '100%',
                    'position': 'fixed',
                    'top': '0',
                    'left': '0',
                    'z-index': '999',
                    'background-color': '#000000',
                    'opacity': '0.5',
                    '-moz-opacity': '0.5',
                    'filter': 'alpha(Opacity=50)'
                });
            }

            $('body').append(me.bg);
        });

    };
    me.checkHeight = function () {
        var clientH = Math.min(document.documentElement.clientHeight, document.body.clientHeight);
        if ((me.option && me.option.absolute) || clientH < me.o.outerHeight()) {
            me.isOffHeight = 1;
            if (me.ie6) {
                me.o.css({
                    'top': document.documentElement.scrollTop,
                    'left': ((document.documentElement.clientWidth - me.o.outerWidth()) / 2) + document.documentElement.scrollLeft
                });
            } else {
                me.o.css({
                    'position': 'absolute',
                    'top': Math.max(document.documentElement.scrollTop, document.body.scrollTop),
                    'margin-top': '0'
                });
            }

        } else {
            me.isOffHeight = 0;
            if (me.ie6) {

            } else {
                me.o.css({
                    'position': 'fixed',
                    'top': '50%',
                    'margin-top': '-' + (me.o.height() / 2) + 'px'
                });
            }

        }
    };
    me.createBg();
}