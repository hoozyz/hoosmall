package com.hoozy.hoosshop.dto;

public class PageInfo { // 페이징 dto

	private int currentPage;
	private int pageLimit;
	private int listCount;
	private int listLimit;

	/**
	 * @param currentPage 현재 페이지
	 * @param pageLimit   한 페이지에 보여질 페이지의 수 = 페이지 기준으로 보여질 숫자
	 * @param listCount   전체 리스트의 수 = 총게시물 숫자
	 * @param listLimit   한 페이지에 표시될 리스트의 수 = 한페이지에 보여질 게시물 수
	 */
	public PageInfo(int currentPage, int pageLimit, int listCount, int listLimit) {
		this.currentPage = currentPage;
		this.pageLimit = pageLimit;
		this.listCount = listCount;
		this.listLimit = listLimit;
	}

	/**
	 * @return 전체 페이지 중 가장 마지막 페이지
	 */
	public int getMaxPage() {
		return (int) Math.ceil((double) this.listCount / this.listLimit);
	}

	/**
	 * 
	 * @return 페이징 된 페이지 중 시작 페이지
	 */
	public int getStartPage() {
		return (this.pageLimit * ((this.currentPage - 1) / this.pageLimit)) + 1;
	}

	/**
	 * 
	 * @return 페이징 된 페이지 중 마지막 페이지
	 */
	public int getEndPage() {

		int endPage = getStartPage() + this.pageLimit - 1;

		return endPage > getMaxPage() ? getMaxPage() : endPage;
	}

	/**
	 * 
	 * @return 현재 페이지
	 */
	public int getCurrentPage() {
		return this.currentPage;
	}

	/**
	 * 
	 * @return 이전 페이지
	 */
	public int getPrevPage() {
		int prevPage = this.getCurrentPage() - 1;

		return prevPage < 1 ? 1 : prevPage;
	}

	/**
	 * 
	 * @return 다음 페이지
	 */
	public int getNextPage() {
		int nextPage = this.getCurrentPage() + 1;

		return nextPage > getMaxPage() ? getMaxPage() : nextPage;
	}

	/**
	 * 
	 * @return 페이지의 시작 리스트
	 */
	public int getStartList() {
		return (this.getCurrentPage() - 1) * this.listLimit + 1;
	}

	/**
	 * 
	 * @return 페이지의 마지막 리스트
	 */
	public int getEndList() {
		int endList = this.getStartList() + this.listLimit - 1;

		return endList > this.listCount ? this.listCount : endList;
	}

	public int getPageLimit() {
		return pageLimit;
	}

	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
	}

	public int getListLimit() {
		return listLimit;
	}

	public void setListLimit(int listLimit) {
		this.listLimit = listLimit;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
