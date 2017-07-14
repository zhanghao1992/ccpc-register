fis.media('product').match('::package', {
//    postpackager: fis.plugin('loader'),
//    packager: fis.plugin('map', {
//        '/static/common/js/common.js': [
//            '/static/common/js/*.js'
//        ],
//        '/static/common/css/common.css': [
//            '/static/common/css/*.css'
//        ]
//    }),
    spriter: fis.plugin('csssprites')

}).match('*.{js,css,png,jpg}', {
//    domain: 'http://www.xx.com',
    useHash: true
}).match('*.css', {
    // 给匹配到的文件分配属性 `useSprite`
    useSprite: true
}).match('*.js', {
    // fis-optimizer-uglify-js 插件进行压缩，已内置
    optimizer: fis.plugin('uglify-js')

}).match('*.css', {
    // fis-optimizer-clean-css 插件进行压缩，已内置
    optimizer: fis.plugin('clean-css')
}).match('*.png', {
    // fis-optimizer-png-compressor 插件进行压缩，已内置
    optimizer: fis.plugin('png-compressor')
}).match('/static/ue/**', {
    useHash: false,
    optimizer: false
}).match('/static/My97DatePicker/**', {
    useHash: false,
    optimizer: false
});

fis.media('debug');