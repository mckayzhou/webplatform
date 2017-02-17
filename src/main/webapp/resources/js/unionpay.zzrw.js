//定义公共组件
(function() {
	var ZZRW = function() {

		var _alert = function(msg) {
			var $alertModal = $("#alertModal");
			if ($alertModal.length > 0) {
				$alertModal.modal('show');
				$(".modal-body", $alertModal).html(
						"<p class='alert-content'>" + msg + "</p>");
			$(".modal-footer .alert-info", $alertModal).unbind('click').click(function() {
				var hide = true;
				if (typeof (func) === "function") {
					hide = func.call();
				}
				if (hide == null || hide){$alertModal.modal('hide');}
			});
			
		} else {
			// Alert Modal not Exist!
			global.alert(msg);
		}
		};
		
		var loadTimeout = null;
		var _load = function(msg,success) {
			var $loadModal = $("#loadModal");
			if($loadModal.length > 0) {
				
				var display = $loadModal.css('display');
				if(display == 'none'){$loadModal.modal('show')};
				$(".modal-backdrop").hide();
				$(".load-body", $loadModal).html(
						"<p class='load-content'>"+msg+"</p>");
				
				if(success === true || success === "success") {
					if(loadTimeout != null){
						clearTimeout(loadTimeout);
						loadTimeout = null;
					}
					// 1秒后关闭
					setTimeout(function(){
						$loadModal.modal('hide');
					},1000);
				}else{
					// 6秒后关闭
					loadTimeout = setTimeout(function(){
						$loadModal.modal('hide');
					},6000);
				}
			}
		};

		var _confirm = function(msg, success, error) {
			var $confirmModal = $("#confirmModal");
			if ($confirmModal.length > 0) {
				$confirmModal.modal('show');
				$(".modal-body", $confirmModal).html(
						"<p class='alert-content'>" + msg + "</p>");
				
				$(".modal-footer .confirm-yes").unbind('click');
				$(".modal-footer .confirm-no").unbind('click');
				$(".modal-footer .confirm-yes", $confirmModal).click(
						function() {
							if (typeof (success) === "function") {
								success.call();
							}
							$confirmModal.modal('hide');
						});
				$(".modal-footer .confirm-no", $confirmModal).click(
						function() {
							if (typeof (error) === "function") {
								error.call();
							}
							$confirmModal.modal('hide');
						});
			} else {
				// Modal not Exist!
				var right = confirm(msg);
				if (right == true) {
					if (typeof (success) === "function") {
						success.call();
					}
				} else {
					if (typeof (error) === "function") {
						error.call();
					}
				}
			}
		};

		var _log = function(msg,show) {
			if (window.console) {
				console.log(msg);
			} else {
				// 不支持console打印日志
				if (show) {
					_alert(msg);
				}
			}
		};

		var _createForm = function(target, formData) {
			$target = $(target);
			
			// 格式化数据，处理空值
			function _formatFormData(formData){
				formData.action = formData.action || '';
				formData.method = formData.method || '';
				for(i=0 ; i<formData.formLines.length; i++){
					var formLine = formData.formLines[i];
					
					formLine.value = formLine.value || '';
					formLine.name = formLine.name || '';
					formLine.label = formLine.label || '';
					formLine.tag = formLine.tag || '';
					formLine.type = formLine.type || '';
					formLine.placeholder = formLine.placeholder || '';
					formLine.cssclass = formLine.cssclass || '';
				}
			}
			// 格式化数据，处理空值
			_formatFormData(formData);
			
			if(formData.id && formData.id != ''){
				// 构建dom
				var htmlStr = "";
				
				var $targetForm = $("#"+formData.id);
				if($targetForm != null){
					if(formData.action != null && formData.action != '') {$targetForm.attr("action", formData.action);}
					if(formData.method != null && formData.method != '') {$targetForm.attr("method", formData.method);}
				}else{
					htmlStr += '<form id="'+formData.id+'" action="'+formData.action+'" method="'+formData.method+'">';
				}
				htmlStr += '<div class="form-inner">';
				
				// 遍历所有行，构建相应代码
				for(i=0 ; i<formData.formLines.length; i++){
					var formLine = formData.formLines[i];
					
					if(formLine.hide){
						htmlStr += '<div class="form-group zzrw_'+formLine.tag +'" style="display:none;">';
					}else{
						htmlStr += '<div class="form-group zzrw_'+formLine.tag +'">';
					}
					
					htmlStr += '<label for="'+formLine['id']+'">'+formLine['label']+'</label>';
					
					if(formLine.tag == 'input'){
						htmlStr += '<input id="'+formLine['id']+'" name="'+formLine['name']+'" type="'+formLine['type']+'" class="form-control '+formLine['cssclass']+'" value="'+formLine['value']+'" placeholder="'+formLine['placeholder']+'" />';
						
					}else if(formLine.tag == 'hidden'){
						htmlStr += '<input id="'+formLine['id']+'" name="'+formLine['name']+'" type="hidden" value="'+formLine['value']+'" >';
						
					}else if(formLine.tag == 'display'){
						htmlStr += '<p id="'+formLine['id']+'" class="form-control '+formLine['cssclass']+'">'+formLine['placeholder']+'</p>';
						
					}else if(formLine.tag == 'table'){
						
						if("collapse" == formLine.type){
							htmlStr += '<div id="'+formLine['id']+'" class="panel-group '+formLine['cssclass']+'" role="tablist" aria-mutiselectable="true">';
							
							for(j = 0 ; j < formLine.items.length; j++){
								var formItem = formLine.items[j];
								
								htmlStr += '<div class="panel">';
								
								
								if(formLine.selectable){
									htmlStr += '<div class="panel-heading selectable" role="tab" id="'+(formLine['id']+'h'+j)+'">';
									
									htmlStr +='<h4 class="panel-title">'+formItem[0]+'</h4>';
									htmlStr += '<a class="panel-select"></a>';
								}else{
									htmlStr += '<div class="panel-heading" role="tab" id="'+(formLine['id']+'h'+j)+'">';
									
									htmlStr +='<h4 class="panel-title">'+formItem[0]+'</h4>';
								}
								
								htmlStr += '<a class="panel-dropdown collapsed" role="button" data-toggle="collapse" data-parent="#'+formLine['id']+'" href="#'+(formLine['id']+j)+'" aria-expanded="true" aria-controls="'+(formLine['id']+j)+'"></a>';
								htmlStr += '</div>';
								
								htmlStr += '<div id="'+(formLine['id']+j)+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="'+(formLine['id']+'h'+j)+'"><div class="panel-body">';
								for(k=1 ; k<formItem.length; k++){
									htmlStr += '<p><span class="panel-th">'+formLine.ths[k-1] +'</span><span class="panel-td">'+(formItem[k] || '&nbsp;') +'</span></p>';
								}
								htmlStr += '</div></div>';
								htmlStr += '</div>';
							}
							
							htmlStr += '</div>';
						}else{
							// 此处为定制使用
							htmlStr += '<table id="'+formLine['id']+'" class="table table-bordered table-striped '+formLine['cssclass']+'"><thead><tr>';
							for(j = 0 ; j < formLine.ths.length; j++){
								var th = formLine.ths[j];
								htmlStr += '<th>'+ th +'</th>';
							}
							htmlStr += '</tr></thead><tbody>';
							for(j = 0 ; j < formLine.items.length; j++){
								var formItem = formLine.items[j];
								htmlStr += '<tr>';
								for(k=0 ; k<formItem.length; k++){
									htmlStr += '<td>'+(formItem[k] || '&nbsp;') +'</td>';
								}
								htmlStr += '</tr>';
							}
							htmlStr +='</tbody></table>';
						}
						
					}else if(formLine.tag == 'textarea'){
						htmlStr += '<textarea id="'+formLine['id']+'" name="'+formLine['name']+'" class="form-control '+formLine['cssclass']+'" placeholder="'+formLine['placeholder']+'"></textarea>';
						
					}else if(formLine.tag == 'select' || formLine.tag == 'checkbox' || formLine.tag == 'radio' ){
						if(formLine.items != null){
							if(formLine.tag == 'select'){
								
								htmlStr += '<select id="'+formLine['id']+'" name="'+formLine['name']+'" class="form-control '+formLine['cssclass']+'">';
								for(j = 0 ; j < formLine.items.length; j++){
									var formItem = formLine.items[j];
									
									if(formLine.value != null && formLine.value != '' && formLine.value == formItem.value){
										htmlStr += '<option value="'+formItem.value+'" selected="selected" >'+formItem.name+'</option>';
									}else{
										htmlStr += '<option value="'+formItem.value+'" >'+formItem.name+'</option>';
									}
								}
								htmlStr += '</select><a class="action select" href="javascript:;"></a>';
							}else if(formLine.tag == 'checkbox'){
								for(j = 0 ; j < formLine.items.length; j++){
									var formItem = formLine.items[j];
									htmlStr += '<label class="checkbox-inline zzrw_checkbox '+formLine['cssclass']+'"><input type="checkbox" name="'+formLine['name']+'" id="'+formLine.id+j+'" value="'+formItem.value+'">'+formItem.name+'</label>';
								}
							}else if(formLine.tag == 'radio'){
								for(j = 0 ; j < formLine.items.length; j++){
									var formItem = formLine.items[j];
									if(j == 0){
										htmlStr += '<label class="checkbox-inline zzrw_radio '+formLine['cssclass']+' selected"><input type="radio" checked="checked" name="'+formLine['name']+'" id="'+formLine.id+j+'" value="'+formItem.value+'">'+formItem.name+'</label>';
									}else{
										htmlStr += '<label class="checkbox-inline zzrw_radio '+formLine['cssclass']+'"><input type="radio" name="'+formLine['name']+'" id="'+formLine.id+j+'" value="'+formItem.value+'">'+formItem.name+'</label>';
									}
								}
							}
						}else{
							//TODO: 无选项处理
						}
					}else if(formLine.tag == 'area'){
						htmlStr += '<div class="form-control area">';
						htmlStr += '<div class="form-area"><select name="province" id="province"></select><a class="action select" href="javascript:;"></a></div>';
						htmlStr += '<div class="form-area"><select name="city" id="city"/></select><a class="action select" href="javascript:;"></a></div>';
						htmlStr += '<div class="form-area"><select name="county" id="area"/></select><a class="action select" href="javascript:;"></a></div>';
						htmlStr += '</div>';
						
					}else if(formLine.tag == 'date'){
						htmlStr += '<input id="'+formLine['id']+'" name="'+formLine['name']+'" type="text" readonly class="form-control '+formLine['cssclass']+'" value="" placeholder="'+formLine['placeholder']+'" />';
						htmlStr += '<a id="'+formLine['id']+'Action" class="action date" href="javascript:;"></a>';
					}else if(formLine.tag == 'randCode'){
						
						htmlStr += '<input id="'+formLine['id']+'" name="'+formLine['name']+'" type="text" class="form-control randCode'+formLine['cssclass']+'" value="" placeholder="'+formLine['placeholder']+'" />';
						htmlStr += '<img id="rand'+formLine['id']+'" class="form-image zzrw_randCode" src="'+formLine.exts[0].value+'?t='+Math.random()+'" />';
						
					}else if(formLine.tag == 'UEdit'){
						if(window.UE){
							htmlStr += '<script id="'+formLine['id']+'" name="'+formLine['name']+'" type="text/plain" style="width: 800px; height: 300px;"></script>';
						}else{
							htmlStr += '<textarea id="'+formLine['id']+'" name="'+formLine['name']+'" class="form-control '+formLine['cssclass']+'" placeholder="'+formLine['placeholder']+'"></textarea>';
						}
						
					}
					
					htmlStr += '</div>';
				}
				htmlStr += '</div>';
				
				// 添加表单按钮
				if(formData.button){
					htmlStr += '<div class="form-group zzrw_button">';
					if(formData.button == 'submit'){
						htmlStr += '<button id="btnSub'+formData.id+'" type="submit" class="btn btn-primary">提交</button>';
						
					}else if(formData.button == 'back'){
						htmlStr += '<button id="btnBack'+formData.id+'" type="button" class="btn btn-warning" style="margin-right:20px;">返回</button>';
						htmlStr += '<button id="btnSub'+formData.id+'" type="submit" class="btn btn-primary">提交</button>';
					}
					htmlStr += '</div>';				
				}
				
				
				if($("#"+formData.id) == null){
					htmlStr += '</form>';
				}
				$target.html(htmlStr).addClass('zzrw_form').addClass(formData['cssclass']);
				
				// radio 点击事件
				$("label.zzrw_radio", $target).click(function(e){
					e = e || window.event;
					var elm = e.target || e.srcElement;
					if(elm.tagName == "INPUT"){
						$(this).siblings().removeClass('selected');
						$(this).addClass('selected');
					}
				});
				// checkbox 点击事件
				$("label.zzrw_checkbox", $target).click(function(e){
					e = e || window.event;
					var elm = e.target || e.srcElement;
					if(elm.tagName == "INPUT"){
						$(this).toggleClass('selected');
					}
				});
				
				// 绑定返回按钮事件
				$("#btnBack"+formData.id).click(function(){
					var url="javascript:history.go(-1);";
				    window.location.href=url;
				});
				
				// 添加exts属性值
				for(i=0 ; i<formData.formLines.length; i++){
					var formLine = formData.formLines[i];
					if(formLine.exts){
						for(j=0; j<formLine.exts.length; j++){
							if(formLine.id){
								$("#"+formLine.id).attr(formLine.exts[j].name, formLine.exts[j].value);
							}
						}
					}
				}
				
				// TODO: 初始化日期控件
				if(window.WdatePicker){
					for(i=0 ; i<formData.formLines.length; i++){
						var formLine = formData.formLines[i];
						if(formLine.tag == 'date'){
							if(formLine.id){
								$("#"+formLine.id).attr('onclick','WdatePicker('+(formLine['timepicker'].config || '')+')');
							}
						}
					}
				}
				else if($.fn.datetimepicker){
					for(i=0 ; i<formData.formLines.length; i++){
						var formLine = formData.formLines[i];
						if(formLine.tag == 'date'){
							if(formLine.id){
								var modalHtml = '<div id="'+formLine.id+'PickerModal" class="zzrw_date modal fade" aria-hidden="true">';
								modalHtml += '<div class="modal-dialog"><div class="modal-content">';
								modalHtml += '<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button><h4 class="modal-title">时间选择器</h4></div>';
								modalHtml += '<div class="modal-body"><div id="'+formLine.id+'Picker"></div></div>';
								modalHtml += '</div><!-- /.modal-content --></div><!-- /.modal-dialog --></div><!-- /.modal -->';
								
								$targetForm.append(modalHtml);
								$("#"+formLine.id+"Action").attr('data-toggle', 'modal').attr('data-target','#'+formLine.id + 'PickerModal');
								$("#"+formLine.id + "Picker").datetimepicker(formLine['timepicker']).on('changeDate', function(ev){
							    	var value = $(this).data('date');
							    	
							    	var id = $(this).attr('id');
							    	
							    	$("#"+ id.replace('Picker', '')).val(value);
							    	$("#"+ id + 'Modal').modal('hide');
							    });
								
								/* 移动端不适用此种方式 
								$("#"+formLine.id).datetimepicker(formLine['timepicker']).on('changeDate', function(ev){
							    	var value = $(this).data('date');
							    	$("#"+formLine.id).val(value);
							    });
							    */
							}
						}
					}
				} else{
					for(i=0 ; i<formData.formLines.length; i++){
						var formLine = formData.formLines[i];
						if(formLine.tag == 'date'){
							if(formLine.id){
								$("#"+formLine.id).removeAttr('readonly');
							}
						}
					}
				}
				
				
				// TODO: 初始化UEdit控件
				if(window.UE){
					for(i=0 ; i<formData.formLines.length; i++){
						var formLine = formData.formLines[i];
						if(formLine.tag == 'UEdit'){
							if(formLine.id){
								var ue = UE.getEditor(formLine.id);
							}
						}
					}
					 
				}
				
				// TODO: 初始化地址控件
				
				// 初始化验证码控件
				for(i=0 ; i<formData.formLines.length; i++){
					var formLine = formData.formLines[i];
					if(formLine.tag == 'randCode'){
						if(formLine.id){
							$("#rand"+formLine.id).click(function(){
								$(this).attr('src', formLine.exts[0].value + "?t=" + Math.random());
							});
						}
					}
				}
				
				// 初始化collapse表格属性
				if($.collapse){
					$(".collapse", $target).collapse();
				}
				
				//绑定formValidate验证
				if($.formValidator){
					var validate = formData.validate;
					validate.formID = formData.id;
					// 初始化formValidate
					$.formValidator.initConfig(validate);
					
					for(i=0 ; i<formData.formLines.length; i++){
						var formLine = formData.formLines[i];
						
						var $formLine = $("#"+formLine['id'], $target);
						if(formLine.input != null || formLine.regex != null){
							
							if(formLine.input != null && formLine.regex != null ){
								$formLine.formValidator(formLine['validate']).inputValidator(formLine['input']).regexValidator(formLine['regex']);
							}else{
								// 输入验证
								if(formLine.input != null ){
									$formLine.formValidator(formLine['validate']).inputValidator(formLine['input']);
								}
								// 正则验证
								if(formLine.regex != null ){
									$formLine.formValidator(formLine['validate']).regexValidator(formLine['regex']);
								}
							}
						}
					}
				}
				
			}else{
				_log("FormData id not set!");
			}
			
		};

		// 注册相关函数
		this.alert = _alert;
		this.confirm = _confirm;
		this.load= _load;
		this.log = _log;
		this.createForm = _createForm;
	}

	// 注册window对象
	window.zzrw = new ZZRW();
})(window, jQuery, document);