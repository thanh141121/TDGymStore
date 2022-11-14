package net.gymsrote.service.NeedImpl;

import org.springframework.data.domain.Page;

import net.gymsrote.entity.Log;
import net.gymsrote.utility.LogFilter;
import net.gymsrote.utility.PagingInfo;

public interface LogRepoCustom {

	Page<Log> search(LogFilter filter, PagingInfo pagingInfo);

}