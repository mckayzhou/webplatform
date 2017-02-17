/** 
对JavaScript 扩张，类似Java中this.jsonString.startsWith(prefix) 
判断字符串是否是以str为开头的 
*/
if(typeof String.prototype.startsWith != 'function'){
	String.prototype.startsWith=function(str){  
	  if(str==null||str==""||this.length==0||str.length>this.length)  
	   return false;  
	  if(this.substr(0,str.length)==str)  
	     return true;  
	  else  
	     return false;  
	  return true;  
	}  
}

/** 
对Array 扩张，类似Java中this.jsonString.startsWith(prefix) 
判断字符串是否是以str为开头的 
*/
if(typeof Array.prototype.indexOf != 'function'){
	Array.prototype.indexOf=function(obj){  
	  for(var i=0; i<this.length;i++){
		  if(this[i] == obj){
			  return i;
		  }
	  } 
	  return -1;
	}  
}

/**
 * 数字变为中文函数
 * 
 * @param num
 * @returns {String}
 */
function num2Chinese(num){
	var units = new Array("","十","百","千","万","十万","百万","千万","亿","十亿","百亿","千亿","万亿");
	var numArray = new Array('零','一','二','三','四','五','六','七','八','九');

	var result = "";
	
	numStr = num.toString();
	for(var i = 0; i < numStr.length; i++){
		m = numStr[i];
		n = parseInt(m);
		isZero = n == 0;
		unit = units[(numStr.length - 1) - i];
		if(isZero) {
			if('0' == numStr[i - 1]) {
				continue;
			}else{
				result += numArray[n];
			}
		}else{
			result += numArray[n];
			result += unit;
		}
	}
	if(result[result.length - 1] ==  '零')
		result = result.substr(0,result.length - 1);
	if((result.length ==  2 || result.length == 3) && (result[0] == '一' && result[1] == '十')){
		result = result.substr(1, result.length - 1);
	}
	return result;
}

//对Date的扩展，将 Date 转化为指定格式的String   
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
//例子：   
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
if(typeof Date.prototype.Format != 'function'){
	Date.prototype.Format = function(fmt){ 
		var o = {   
		 "M+" : this.getMonth()+1,                 //月份   
		 "d+" : this.getDate(),                    //日   
		 "h+" : this.getHours(),                   //小时   
		 "m+" : this.getMinutes(),                 //分   
		 "s+" : this.getSeconds(),                 //秒   
		 "q+" : Math.floor((this.getMonth()+3)/3), //季度   
		 "S"  : this.getMilliseconds()             //毫秒   
		};   
		if(/(y+)/.test(fmt))   
		 fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
		for(var k in o)   
		 if(new RegExp("("+ k +")").test(fmt))   
		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		return fmt;   
	}  
}

/**
 * 转换表单信息为 zzrw 表单信息
 * @param forms
 */
function transFormLines(forms){
	var formLines = [];
	
	for(i= 0; i< forms.length; i++){
		var formItem = forms[i];
		var id = "form"+i;
		
		var items = [];
		if("02" == formItem.eleType || "03" == formItem.eleType || "04" == formItem.eleType){
			for(j=0;j< formItem.loanValues.length; j++){
				var item = {id: j}
				item.name = formItem.loanValues[j].valueDesc;
				item.value = formItem.loanValues[j].value;
				
				items.push(item);
			}
		}
		
		if("01" == formItem.eleType){
			var name = formItem.eleName;
			var label = formItem.eleDesc;
			var tip = formItem.eleDesc + "不可为空";
			var placeholder = formItem.eleDesc;
			
			var regex = null;
			var maxlength = "30";
			for(j=0;j< formItem.loanValues.length; j++){
				var item = {};
				item.name = formItem.loanValues[j].valueDesc;
				item.value = formItem.loanValues[j].value;
				
				if("placeholder" == item.value){
					placeholder = item.name;
				}
				
				if("regex" == item.value){
					regex = {
						regExp: item.name,
						param: '',
						dataType: '',
						onError: '请输入正确的'+label
					}
				}
				
				if("maxlength" == item.value){
					maxlength = item.value;
				}
			}
			
			var formLine = {	
				id: id,													// 输入input 的 id
				name: name,												// 输入input 的 name
				label: label,											// 描述数据的值
				tag: 'input',											// 输入的类型
				type: 'text',											// 子类型 text | password | email | phone
				placeholder: placeholder,								// 输入 input 的 placeholder
				cssclass: '',											// 输入内容添加的 CSS class
				validate:{},											// formValidator 的 formValidator配置
				input: {												// formValidator 的 inputValidator配置
					type: 'size',
					min: '1',
					onError: tip,
					onErrorMin: tip,
					empty: false
				},
				regex: regex,
				exts:[
					{"name": "maxlength", "value": maxlength}
				]
			}
			formLines.push(formLine);
			
		}else if("02" == formItem.eleType){
			var name = formItem.eleName;
			var label = formItem.eleDesc;
			
			var formLine = {	
				id: id,
				name: name,
				label: label,
				tag: 'select',											// 输入类型为选择项，使用 tag = select | checkbox | radio
				type: '',												// type 值置空，不使用
				placeholder: '',										// placeholder 值置空，不使用
				cssclass: '',
				items:items,											// 可选择项列表
				validate:{},											// formValidator 的 formValidator配置
				input: {												// formValidator 的 inputValidator配置
					empty: false
				}
			}
			formLines.push(formLine);
			
		}else if("03" == formItem.eleType){
			var name = formItem.eleName;
			var label = formItem.eleDesc;
			
			var formLine = {	
				id: id+"radio",											// 输入input 的 id
				name: name,												// 
				label: label,											// 描述数据的值
				tag: 'radio',											// 输入的类型
				value: '',
				type: '',												// 
				placeholder: name,										// 显示display 的值
				cssclass: '',											// 输入内容添加的 CSS class
				validate:{},											// formValidator 的 formValidator配置
				input: null,
				items:items
			}
			formLines.push(formLine);
			
		}else if("04" == formItem.eleType){
			var name = formItem.eleName;
			var label = formItem.eleDesc;
			
			var formLine = {	
				id: id,													// 输入input 的 id
				name: name,												// 
				label: label,											// 描述数据的值
				tag: 'checkbox',										// 输入的类型
				value: '',
				type: '',												// 
				placeholder: '',										// 显示display 的值
				cssclass: '',											// 输入内容添加的 CSS class
				validate:{},											// formValidator 的 formValidator配置
				input: null,
				hide:true,
				items:checkboxItems 
			}
			formLines.push(formLine);
			
		}else if("05" == formItem.eleType){
			
		}else{
			
		}
	}
	
	return formLines;
}