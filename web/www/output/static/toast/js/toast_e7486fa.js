"use strict";!function(t,e){var a=function(e){for(var a=!1,n=t("link"),o=0;o<n.length;o++)if(n.eq(o).attr("href")==e)return void(a=!0);if(!a){var i=document.createElement("link");i.type="text/css",i.rel="stylesheet",i.href=e,document.getElementsByTagName("head")[0].appendChild(i)}};a("/static/toast/css/animate_87d6caf.css"),t.fn.toast=function(a){var n=t(this);return this.each(function(){t(this).css({position:"relative"});var o="",i="",r={position:"absolute",animateIn:"fadeIn",animateOut:"fadeOut",padding:"10px 20px",background:"rgba(7,17,27,0.66)",borderRadius:"6px",duration:3e3,animateDuration:500,fontSize:14,content:"这是一个提示信息",color:"#fff",top:"80%",zIndex:1000001,isCenter:!0,closePrev:!0},s=t.extend(r,a||{}),d="";o=s.isCenter===!0?"50%":s.top,r.isLowerIe9=function(){return!e.FormData},r.createMessage=function(){s.closePrev&&t(".cpt-toast").remove(),i=t("<span class='animated "+s.animateIn+" cpt-toast'></span>").css({position:s.position,padding:s.padding,background:s.background,"font-size":s.fontSize,"-webkit-border-radius":s.borderRadius,"-moz-border-radius":s.borderRadius,"border-radius":s.borderRadius,color:s.color,top:o,"z-index":s.zIndex,"-webkit-transform":"translate3d(-50%,-50%,0)","-moz-transform":"translate3d(-50%,-50%,0)",transform:"translate3d(-50%,-50%,0)","-webkit-animation-duration":s.animateDuration/1e3+"s","-moz-animation-duration":s.animateDuration/1e3+"s","animation-duration":s.animateDuration/1e3+"s"}).html(s.content).appendTo(n),r.colseMessage()},r.colseMessage=function(){var t=r.isLowerIe9();d=t?setTimeout(function(){i.remove()},s.duration):setTimeout(function(){i.removeClass(s.animateIn).addClass(s.animateOut),setTimeout(function(){i.remove()},500)},s.duration)},r.createMessage()})}}(Zepto,window);var showMessage=function(t,e,a,n,o){var n=n||"fadeIn",o=o||"fadeOut",t=t||"这是一个提示信息",e=e||"3000",a=a||!1;$("body").toast({position:"fixed",animateIn:n,animateOut:o,content:t,duration:e,isCenter:a})};