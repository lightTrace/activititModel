package cn.com.wavenet.bean;

import java.util.Collection;

/**
 * 分页对应的实体类
 */

public class Pagination {
	
	
	/**
	 * 根据当前对象中属性值计算并设置相关属性值
	 */
	/*public void count() {
		// 计算总页数
		int totalPageTemp = this.totalNumber / this.pageNumber;
		int plus = (this.totalNumber % this.pageNumber) == 0 ? 0 : 1;
		totalPageTemp = totalPageTemp + plus;
		if(totalPageTemp <= 0) {
			totalPageTemp = 1;
		}
		this.totalPage = totalPageTemp;
		
		// 设置当前页数
		// 总页数小于当前页数，应将当前页数设置为总页数
		if(this.totalPage < this.currentPage) {
			this.currentPage = this.totalPage;
		}
		// 当前页数小于1设置为1
		if(this.currentPage < 1) {
			this.currentPage = 1;
		}
		
		// 设置limit的参数
		this.startIndex = (this.currentPage - 1) * this.pageNumber+1;
		this.endIndex = this.currentPage * this.pageNumber;
	}*/

	 /**
	   * 开始位置
	   */
	  private int start;
	  /**
	   * 结束位置
	   */
	  private int end;
	  /**
	   * 总记录数
	   */
	  private int count;
	  /**
	   * 总页数
	   */
	  private int pages;
	  /**
	   * 当前是第几页
	   */
	  private int current=1;
	  /**
	   * 每页的记录数
	   */
	  private int countOfPage = 20;
	  /**
	   * 当前页的记录数
	   */
	  private int countOfCurrent; 
	  /**
	   * 当前页的数据
	   */
	  private Collection data;
	  /**
	   * 是否显示全部,也就是不分页
	   */
	  private boolean showAll;
	  /**
	   * 根据总记录数,每页的记录数,当前页,计算出这一页的开始位置和结束位置
	   * @param count
	   * @param countOfPage
	   * @param current
	   */
	  public Pagination(int count,int countOfPage,int current){
	    this.init(count,countOfPage,current);
	  }
	  public Pagination(int countOfPage,int current){
	    this.countOfPage = countOfPage;
	    this.current = current;
	  }

	  public Pagination() {
//	    this(GlobalParam.PAGE_SIZE, 1);
	  }

	  public void init(int count){
	    this.init(count,this.countOfPage,this.current);
	  }
	  public void init(int count,int countOfPage,int current){
	    this.count = count;
	    this.countOfPage = countOfPage;
	    // 计算一共有多少页
	    this.pages = count/countOfPage;
	    if(count%countOfPage != 0){
	      this.pages ++;
	    }
	    // 判断当前页的合法性
	    if(current < 1){
	      current = 1;
	    }
	    else if(current > pages){
	      current = pages;
	    }

	    this.current = current;
	    // 计算开始位置
	    this.start = countOfPage * (current-1) + 1;
	    // 计算结束位置
	    this.end = start + countOfPage -1;
	    if(this.end > this.count){
	      this.end = this.count;
	    }
	    if(current == pages ){
	    	this.countOfCurrent = this.count-(current-1)*countOfPage;
	    }else{
	    	this.countOfCurrent  = countOfPage;
	    }
	  }

	  public Collection getData(){
	    return data;
	  }
	  public int getCount() {
	    return count;
	  }
	  public int getCurrent() {
	    return current;
	  }
	  public int getEnd() {
	    return end;
	  }
	  public int getStart() {
	    return start;
	  }
	  public void setCount(int count) {
	    this.count = count;
		this.init(count);
	  }
	  public void setCurrent(int current) {
		  if(current<1)current=1;
	    this.current = current;
	  }
	  public void setEnd(int end) {
	    this.end = end;
	  }
	  public void setStart(int start) {
	    this.start = start;
	  }
	  public int getPages() {
	    return pages;
	  }
	  public void setPages(int pages) {
	    this.pages = pages;
	  }
	  public int getCountOfPage() {
	    return countOfPage;
	  }
	  public void setCountOfPage(int countOfPage) {
	    this.countOfPage = countOfPage;
	  }
	  public void setData(Collection data) {
	    this.data = data;
	  }
	  public String toString() {
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("count=").append(this.count);
	    buffer.append(",pages=").append(this.pages);
	    buffer.append(",current=").append(this.current);
	    buffer.append(",countOfPage=").append(this.countOfPage);
	    buffer.append(",start=").append(this.start);
	    buffer.append(",end=").append(this.end);
	    buffer.append(",data=").append(this.data);
	    return buffer.toString();
	  }

	  public boolean isShowAll() {
	    return showAll;
	  }
	  public void setShowAll(boolean showAll) {
	    this.showAll = showAll;
	  }
	public int getCountOfCurrent() {
		return countOfCurrent;
	}
	public void setCountOfCurrent(int countOfCurrent) {
		this.countOfCurrent = countOfCurrent;
	}
	
}
