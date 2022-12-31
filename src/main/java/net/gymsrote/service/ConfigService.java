package net.gymsrote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.controller.payload.response.ListResponse;
import net.gymsrote.dto.ConfigDTO;
import net.gymsrote.entity.Config;
import net.gymsrote.repository.ConfigRepo;
import net.gymsrote.service.utils.ServiceUtils;

@Service
public class ConfigService {
	@Autowired
	private ConfigRepo repo;

	@Autowired
	ServiceUtils serviceUtils;
	
	public DataResponse<ConfigDTO> get(Long id) {
		return serviceUtils.convertToDataResponse(repo.findById(id).orElseThrow(
				() -> new InvalidInputDataException("Không tìm thấy 'Config' với id: " + id)), ConfigDTO.class);
	}

	public ListResponse<ConfigDTO> getAll() {
		return serviceUtils.convertToListResponse(repo.findAll(), ConfigDTO.class);
	}

	public DataResponse<ConfigDTO> create(ConfigDTO body) {
		return serviceUtils.convertToDataResponse(repo.save(new Config(body.getValue())), ConfigDTO.class);
	}

	public DataResponse<ConfigDTO> update(Long id, ConfigDTO body) {
		Config config = repo.getReferenceById(id);
		config.setValue(body.getValue());
		return serviceUtils.convertToDataResponse(repo.save(config), ConfigDTO.class);
	}

	public DataResponse<ConfigDTO> delete(Long id) {
		Config config = repo.findById(id).orElseThrow(
				() -> new InvalidInputDataException("Không tìm thấy 'Config' với id: " + id));
		repo.delete(config);
		return serviceUtils.convertToDataResponse(config, ConfigDTO.class);
	}

	//select
	public DataResponse<Long> select(Long id) {
		return serviceUtils.convertToDataResponse(repo.select(id), Long.class);
	}

}
