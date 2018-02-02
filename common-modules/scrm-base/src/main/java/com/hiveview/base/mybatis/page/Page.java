package com.hiveview.base.mybatis.page;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 分页
 */
public class Page<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8119392723123702649L;

	public static final int DEFAULT_PAGE_SIZE = 10;

	/** 表示当前页页码，即当前页是第几页 **/
	private int pageIndex;

	/** 每页显示记录数，即一页要显示多少条记录 **/
	private int pageSize;
	
	/** 总记录数 **/
	private int totalCount;

	/** 页码数量，总页数 **/
	private int pageCount;
	
	/**
	 * 数据集合
	 */
	protected List<T> records;
	
	public Page() {
		this(1, DEFAULT_PAGE_SIZE);
	}
	
	public Page(int pageIndex) {
		this(pageIndex, DEFAULT_PAGE_SIZE);
	}

	public Page(int pageIndex, int pageSize) {
		// check:
		if (pageIndex < 1){
			pageIndex = 1;
		}
		if (pageSize < 1){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	/**
	 * 获取当前页页码，即当前页是第几页
	 * @return
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * 获取每页显示记录数，即一页要显示多少条记录
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 获取总页数
	 * @return
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 获取总记录数
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 获取将要读取第一条记录的索引，即从第几条数据开始读取
	 * @return
	 */
	public int getFirstResult() {
		int offset = ((pageIndex - 1) * pageSize);
		if(offset < 0 ){
			offset = 0 ;
		}
		return offset;
	}
	
	/**
	 * 获取需要读取的记录数
	 */
	public int getMaxResults(){
		return getPageSize();
	}

	/**
	 * 判断是否有上一页
	 * @return
	 */
	public boolean getHasPrevious() {
		return pageIndex > 1;
	}

	/**
	 * 判断是否有下一页
	 * @return
	 */
	public boolean getHasNext() {
		return pageIndex < pageCount;
	}
	
	/**
	 * 判断总记录数是否为0
	 * @return
	 */
	public boolean isEmpty() {
		return totalCount == 0;
	}

	/**
	 * 设置总记录数，这也是在分页中唯一要设置的参数，即只要有这个参数就可以完成整个分页功能了
	 * 在这个方法里，自动地根据"总记录"和"每页显示记录数"计算出了总页数
	 * 在分页显示中就根据总页数来循环出分页信息
	 * @param mytotalCount
	 */
	public void setTotalCount(long mytotalCount) {
		
		/**
		 * 由于用JPA中的count()函数所返回的总记录时，是个long类型，
		 * 如果说得到的是一个int类型时，就不要类型强制转换了，参数mytotalCount就直接用totalCount 
		 */		
		int totalCount = (int)mytotalCount;
		
		this.totalCount = totalCount;
		pageCount = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
		if (totalCount == 0) {
			if (pageIndex != 1){
			}
		} else {
			if (pageIndex > pageCount){
			}
		}
	}
	
	/**
	 * 开始页
	 * @return
	 */
	public int getStartPage(){
		int startp = 1;
		if(this.pageIndex>5){
			startp = this.pageIndex -4;
		}
		return startp;
	}
	
	/**
	 * 结束页
	 * @return
	 */
	public int getEndPage(){
		int endp = this.pageIndex + 4;
		if(endp>this.pageCount){
			endp = this.pageCount;
		}
		return endp;
	}
	
	public List<Integer> getPageItems(){
		List<Integer> pageItems = new ArrayList<Integer>();
		int startp = getStartPage();
		int endp = getEndPage();
		for(int i=startp;i<=endp;i++){
			pageItems.add(i);
		}
		return pageItems;
	}

	/**
	 * 设置当前为第几页
	 * @param pageIndex
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * 设置分页大小
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

}
