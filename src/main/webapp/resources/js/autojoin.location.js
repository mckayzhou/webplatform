/**
 * string trim()
 * 
 * @param str
 * @returns
 */
function trimStr(str){
  return str.replace(/(^\s*)|(\s*$)/g,"");
}

/**
 * 存储位置下拉相关信息
 */
var Location = {
	provinceList: [],
	cityList: [],
	areaList: []
}

/**
 * 获取区域的中文名称
 * @param type 类型： province|city|area
 * @param id
 * @returns
 */
function getLocationName(type, id){
	if(type == "province"){
		for(i=0;i<Location.provinceList.length;i++){
			var loc = Location.provinceList[i];
			if(id == loc.divisionCd){
				return loc.divisionCnNm;
			}
		}
	}else if(type == "city"){
		for(i=0;i<Location.cityList.length;i++){
			var loc = Location.cityList[i];
			if(id == loc.divisionCd){
				return loc.divisionCnNm;
			}
		}
	}else if(type == "area"){
		for(i=0;i<Location.areaList.length;i++){
			var loc = Location.areaList[i];
			if(id == loc.divisionCd){
				return loc.divisionCnNm;
			}
		}
	}
	
	return null;
}

/**
 * 获取地址全名称
 * @param province 省份id
 * @param city	城市id
 * @param area 区域id
 * @returns
 */
function getFullLocationName(province, city, area){
	var result = "";
	for(i=0;i<Location.provinceList.length;i++){
		var loc = Location.provinceList[i];
		var str = trimStr(loc.divisionCnNm);
		if(province == loc.divisionCd){
			result += str;
		}
	}
	
	for(i=0;i<Location.cityList.length;i++){
		var loc = Location.cityList[i];
		if(city == loc.divisionCd){
			var str = trimStr(loc.divisionCnNm);
			if(str == '县' || str == '市辖区'){
				result += result;
			}else{
				result += str;
			}
		}
	}
	for(i=0;i<Location.areaList.length;i++){
		var loc = Location.areaList[i];
		var str = trimStr(loc.divisionCnNm);
		if(area == loc.divisionCd){
			result += str;
		}
	}
	
	return result;
}

/**
 * 初始化地址选择插件
 */
function initLocationSelect(){
	var $province = $('#province');
	var $city = $('#city');
	var $area = $('#area');	
	// 设置默认值
	setSel(provincev,cityv,areav);

	// 省份下拉下拉
	$province.change(function(item) {
		var id = $(this).val();
		
		// 清空城市与区域
		$city.empty();
		$city.append($('<option value="0">选择市县 </option>'));
		$area.empty();		
		$area.append($('<option value="0">选择区县</option>'));
		// 获取城市列表
		loadData(1,id,$city,'');

	})

	// 城市下拉下拉
	$city.change(function(item) {
		var id= $(this).val();
		
		// 清空区域值
		$area.empty();
		$area.append($('<option value="0">选择区县</option>'));
		// 获取区域列表
		loadData(2,id,$area,'');
	})

}

/**
 * 使用地址id设置地址选中状态
 * 
 * @param provinceId 省份id
 * @param cityId 城市id
 * @param areaId 区域id
 */
function setSel(provinceId,cityId,areaId){
	 var $province = $('#province');
	 var $city = $('#city');
	 var $area = $('#area');
	  
	 if(provinceId != null && provinceId != ''){
		  // 清空省份值
		  $province.empty();
		  $province.append($('<option value="0">选择省份</option>'));
		  // 遍历选中省份
		  $.each(Location.provinceList, function(index, item) {
				if (item.divisionCd == provinceId) {
					$province.append($('<option value="'+item.divisionCd+'" selected = "true" >' + item.divisionCnNm + '</option>'));
				} else {
					$province.append($('<option value="'+item.divisionCd+'">' + item.divisionCnNm + '</option>'));
				}
		  });
		  // 清空城市与区域
		  $city.empty();
		  $city.append($('<option value="0">选择市县 </option>'));
		  loadData(1,provinceId,$city,cityId,function(){
			  // 清空区域值
			  $area.empty();
			  $area.append($('<option value="0">选择区县</option>'));
			  var cityId = this.divisionCd;
			  
			  if(cityId != null){
			  	loadData(2,cityId,$area,areaId);
			  }
		  });
	  } else {
		  // 未能确定省份信息
		  $province.empty();
		  $province.append($('<option value="0">选择省份</option>'));
		  $.each(Location.provinceList, function(index, item) {
				$province.append($('<option value="'+item.divisionCd+'">' + item.divisionCnNm + '</option>'));
		  });
		  $city.empty();
		  $city.append($('<option value="0">选择市县</option>'));
		  $area.empty();
		  $area.append($('<option value="0">选择区县</option>'));
	  }
}

/**
 * 使用地址名称设置地址选中状态
 * 
 * @param provinceNm 省份名称
 * @param cityNm 城市名称
 * @param areaNm 区域名称
 */
function setSelNm(provinceNm,cityNm,areaNm){
	  var $province = $('#province');
	  var $city = $('#city');
	  var $area = $('#area');
	  
	  if(provinceNm != null && provinceNm != ''){
		  var provinceId = null;
		  // 清空省份值
		  $province.empty();
		  $province.append($('<option value="0">选择省份</option>'));
		  // 遍历选中省份
		  $.each(Location.provinceList, function(index, item) {
				if (trimStr(item.divisionCnNm) == provinceNm) {
					provinceId = item.divisionCd;
					$province.append($('<option value="'+item.divisionCd+'" selected = "true" >' + item.divisionCnNm + '</option>'));
				} else {
					$province.append($('<option value="'+item.divisionCd+'">' + item.divisionCnNm + '</option>'));
				}
		  });
		  
		  // 清空城市与区域
		  $city.empty();
		  $city.append($('<option value="0">选择市县 </option>'));
		  loadData(1,provinceId,$city,cityNm, function(){
			  // 清空区域值
			  $area.empty();
			  $area.append($('<option value="0">选择区县</option>'));
			  var cityId = this.divisionCd;
			  if(cityId != null){
				  loadData(2,cityId,$area,areaNm);
			  }
		  });
			  
	  } else {
		  // 未能确定省份信息
		  $province.append($('<option value="0">选择省份</option>'));
		  $.each(Location.provinceList, function(index, item) {
				$province.append($('<option value="'+item.divisionCd+'">' + item.divisionCnNm + '</option>'));
		  });
		  $city.empty();
		  $city.append($('<option value="0">选择市县</option>'));
		  $area.empty();
		  $area.append($('<option value="0">选择区县</option>'));
	  }
}

/**
 * 获取区域子集列表
 * 
 * @param level 1: 省份级别、2: 城市级别
 * @param id 省份或城市id
 * @param dom 处理文档对象	
 * @param sel 默认选中值(选中id或选中名称)
 * @param selFun 基于选中值的操作函数
 */
function loadData(level,id,dom,sel,selFun){
	
	// 选取不同的响应
	if(level == 1){
		rel=webRoot+'/division/getCityByProvinceId';
	}else{
		rel=webRoot+'/division/getAreaByCityId';
	}
	
	$.ajax({
		url: rel,
		type: 'POST',
		dataType: 'json',
		data: {
			id: id
		},
		success: function(data){
			if(data.success){
				// 判断是否有值被选中
				var selected = {};
				
				$.each(data.cityList, function(index, item) {
					 if(item.divisionCd == sel || trimStr(item.divisionCnNm) == sel){
						selected = item;
						dom.append($('<option value="'+item.divisionCd+'"  selected = "true" >' + item.divisionCnNm + '</option>'));
					 } else {
						dom.append($('<option value="'+item.divisionCd+'">' + item.divisionCnNm + '</option>'));
					 }
				});
				
				if(typeof(selFun) === "function"){
					selFun.call(selected);
				}
				
				// 选取不同的响应
				if(level == 1){
					Location.cityList = data.cityList;
				}else{
					Location.areaList = data.cityList;
				}
			}
		}
	})
}