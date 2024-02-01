package com.example.demo.dto;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class PageVO {

	private int totalPage;
	private int startPage;
	private int endPage;
	private boolean hasPrev, hasNext;
	private int pageNo;
	
	public PageVO(Page<BoardDTO>list, int pageNo) {
		//pageNo =0 부터 / 현재 선택한 페이지네이션 번호 0부터 시작
		pageNo = pageNo+1;
		this.pageNo=pageNo;
		this.totalPage=list.getTotalPages();
		
		
		int qty = 10;
		//end Page = (int)Math.ceil((double)pageNo/qty)*qty
		
		this.endPage = (int)Math.ceil((double)pageNo/qty)*qty;
		this.startPage = endPage-(qty-1);
		
		if(endPage > totalPage) {
			this.endPage=totalPage; 
		}
		this.hasPrev = pageNo > 10;
		this.hasNext=list.hasNext();
		
	}
	
}
