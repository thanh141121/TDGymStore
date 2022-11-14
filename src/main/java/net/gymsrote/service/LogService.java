package net.gymsrote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.gymsrote.controller.payload.response.ListWithPagingResponse;
import net.gymsrote.dto.LogDTO;
import net.gymsrote.entity.Log;
import net.gymsrote.entity.EnumEntity.ELogType;
import net.gymsrote.repository.LogRepo;
import net.gymsrote.repository.UserRepo;
import net.gymsrote.service.utils.ServiceUtils;
import net.gymsrote.utility.LogFilter;
import net.gymsrote.utility.PagingInfo;

@Service
public class LogService {
	@Autowired
	LogRepo logRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ServiceUtils serviceUtils;
	
	public void logInfo(Long idUser, String content) {		
		logRepo.save(new Log(
				userRepo.getReferenceById(idUser),
				content,
				ELogType.INFORMATION
			)
		);
	}
	
	public void logWarning(Long idUser, String content) {		
		logRepo.save(new Log(
				userRepo.getReferenceById(idUser),
				content,
				ELogType.WARNING
			)
		);
	}
	
	public ListWithPagingResponse<LogDTO> search(LogFilter filter, PagingInfo pagingInfo) {
		return serviceUtils.convertToListResponse(
				logRepo.search(filter, pagingInfo),
				LogDTO.class
			);
	}
}
