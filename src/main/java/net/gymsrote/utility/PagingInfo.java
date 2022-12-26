package net.gymsrote.utility;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PagingInfo {
	private Integer currentPage;
	private Integer size;
	private Integer sortBy;
	private Boolean sortDescending;
	private Pageable pageable;
	
	public PagingInfo(Integer currentPage, Integer size, Integer sortBy, Boolean sortDescending) {
		if (currentPage == null || currentPage < 1)
			this.currentPage = 0;
		else 
			this.currentPage = currentPage - 1;

		if (size == null || size < 1) 
			this.size = PlatformPolicyParameter.DEFAULT_PAGE_SIZE;
		else 
			this.size = size;
		
		this.sortBy = sortBy;
		this.sortDescending = sortDescending;
		this.pageable = PageRequest.of(this.currentPage, this.size, Sort.by("id").descending());
	}
}