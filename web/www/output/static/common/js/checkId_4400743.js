function isIdcard(r){r=r.replace(/x/g,"X");var r,s,e,t,n,a=(new Array("验证通过!","身份证号码位数不对!","身份证号码出生日期超出范围或含有非法字符!","身份证号码校验错误!","身份证地区非法!"),{11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}),p=new Array;if(p=r.split(""),null==a[parseInt(r.substr(0,2))])return!1;switch(r.length){case 15:return ereg=(parseInt(r.substr(6,2))+1900)%4==0||(parseInt(r.substr(6,2))+1900)%100==0&&(parseInt(r.substr(6,2))+1900)%4==0?/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/:/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/,ereg.test(r)?!0:!1;case 18:return ereg=parseInt(r.substr(6,4))%4==0||parseInt(r.substr(6,4))%100==0&&parseInt(r.substr(6,4))%4==0?/^[1-9][0-9]{5}[0-9]{4}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/:/^[1-9][0-9]{5}[0-9]{4}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/,ereg.test(r)?(t=7*(parseInt(p[0])+parseInt(p[10]))+9*(parseInt(p[1])+parseInt(p[11]))+10*(parseInt(p[2])+parseInt(p[12]))+5*(parseInt(p[3])+parseInt(p[13]))+8*(parseInt(p[4])+parseInt(p[14]))+4*(parseInt(p[5])+parseInt(p[15]))+2*(parseInt(p[6])+parseInt(p[16]))+1*parseInt(p[7])+6*parseInt(p[8])+3*parseInt(p[9]),s=t%11,n="F",e="10X98765432",n=e.substr(s,1),n==p[17]?!0:!1):!1;default:return!1}}