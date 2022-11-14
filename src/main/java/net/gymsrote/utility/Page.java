package net.gymsrote.utility;
import org.springframework.data.domain.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Page extends PageWithJpaSort {
	private int totalPage;
	private PageRequest pageRequest;
	private Integer sortBy;
	private Boolean sortDescending;
	
	public Page(Integer page, Integer size, int itemCount, Integer sortBy, Boolean sortDescending) {
		this(page, size, itemCount);
		this.sortBy = sortBy;
		
		if (sortDescending == null)
			this.sortDescending = false;
		else
			this.sortDescending = sortDescending;
	}
	
	public Page(Integer page, Integer size, int itemCount) {
		super(page, size, itemCount);
	}
}