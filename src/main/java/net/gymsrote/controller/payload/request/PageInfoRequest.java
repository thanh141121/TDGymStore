package net.gymsrote.controller.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.gymsrote.utility.PlatformPolicyParameter;
import org.springframework.data.domain.Sort;
@Setter @ Getter
@Slf4j
public class PageInfoRequest {

	private Integer currentPage = 0;
	private Integer size;
	private String sortBy;
	private String direction = "dsc";
	
	public PageInfoRequest(Integer currentPage, Integer size, String sortBy, String direction) {
		if (currentPage == null || currentPage < 1)
			this.currentPage = 0;
		else 
			this.currentPage = currentPage - 1;

		if (size == null || size < 1) 
			this.size = PlatformPolicyParameter.DEFAULT_PAGE_SIZE;
		else 
			this.size = size;
		
		this.sortBy = sortBy;
		this.direction = direction;
	}

	public Integer getCurrentPage() {
		return currentPage > 0 ?  currentPage - 1 : currentPage;
	}
    public Sort buildSort() {
		if(sortBy != null){
			switch (direction) {
				case "dsc":
					return Sort.by(sortBy).descending();
				case "asc":
					return Sort.by(sortBy).ascending();
				default:
					log.warn("Invalid direction provided in PageSettings, using descending direction as default value");
					return Sort.by(sortBy).descending();
			}
		}else return Sort.unsorted();
    }

	public PageInfoRequest() {
		this.currentPage = 0;
		this.size = PlatformPolicyParameter.DEFAULT_PAGE_SIZE;
	}

	// public void setCurrentPage(Integer currentPage) {
	// 	if(currentPage != null)
	// 		this.currentPage = currentPage;
	// 	else this.currentPage = 0;
	// }
}
