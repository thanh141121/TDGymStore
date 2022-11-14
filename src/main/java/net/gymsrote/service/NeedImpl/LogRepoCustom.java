package net.gymsrote.service.NeedImpl;

import net.gymsrote.entity.Log;
import net.gymsrote.utility.LogFilter;
import net.gymsrote.utility.PagingInfo;
import org.springframework.data.domain.Page;
public interface LogRepoCustom {

	Page<Log> search(LogFilter filter, PagingInfo pagingInfo);

}