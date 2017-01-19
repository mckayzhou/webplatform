/**   
 * Copyright © 2016 本代码版权归周林波所有，严禁未经许可使用。
 * 
 * @Title: page.java 
 * @Prject: springweb
 * @Package: com.mckay.util 
 * @Description:
 * @author:
 * @date: 2016年12月20日 下午9:38:08 
 * @version: V1.0   
 */
package com.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName: page
 * @Description: 封装的page分页类
 * @author: 周林波
 * @date: 2016年12月20日 下午9:38:08
 */
public class Page<T> {
	public enum OrderType {
		asc, desc
	}

	private static final Integer MAX_PAGE_SIZE = 500;// 分页允许的最大尺寸
	private List<T> list;
	private Integer pageSize = 10;
	private Integer nowPage = 1;
	
	@JSONField(serialize = false)
	private List<Order> multiOrder = new ArrayList<Order>();// 多列排序
	private Integer totalCount = 0;// 总记录数
	private Integer maxPage = 0;// 总页数
	
	@JSONField(serialize = false)
	private String property;// 关键字段检索名称
	
	@JSONField(serialize = false)
	private String keyWords;
	
	@JSONField(serialize = false)
	private MatchMode matchMode = MatchMode.ANYWHERE;// 关键字检索的匹配模式

	public Page<T> addOrder(Order order) {
		this.multiOrder.add(order);
		return this;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize < 1 ? 1 : pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
	}
	public Integer getNowPage() {
		return nowPage;
	}
	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public boolean hasNext() {
		return totalCount > pageSize * nowPage ? true : false;
	}
	public Integer getMaxPage() {
		this.maxPage = this.totalCount / this.pageSize;
		return this.totalCount % this.pageSize > 0 ? ++this.maxPage : this.maxPage;
	}
	public void setMaxPage(Integer maxPage) {
		this.maxPage = maxPage;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public MatchMode getMatchMode() {
		return matchMode;
	}
	public void setMatchMode(MatchMode matchMode) {
		this.matchMode = matchMode;
	}
	public List<Order> getMultiOrder() {
		return multiOrder;
	}
	public void setMultiOrder(List<Order> multiOrder) {
		this.multiOrder = multiOrder;
	}
	public Page() {
		super();
	}
}
