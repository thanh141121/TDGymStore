package net.gymsrote.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.controller.payload.response.ListResponse;
import net.gymsrote.dto.statistic.StatisticDTO;
import net.gymsrote.dto.statistic.TodayStatisticDTO;
import net.gymsrote.entity.EnumEntity.EStatisticType;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.User;
import net.gymsrote.repository.ProductRepo;
import net.gymsrote.repository.ProductVariationRepo;
import net.gymsrote.repository.UserRepo;
import net.gymsrote.repository.custom.StatisticRepoCustom;
import net.gymsrote.service.utils.ServiceUtils;

@Service
public class StatisticService {
	@Autowired
	ProductRepo productRepo;

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ProductVariationRepo productVariationRepo;
	
	@Autowired
	StatisticRepoCustom statisticRepo;
	
	@Autowired
	ServiceUtils serviceUtils;
	
	public ListResponse<StatisticDTO> statistic(
			List<Long> idBuyers,
			List<Long> idAdmins,
			Integer month,
			Integer quarter,
			Integer year,
			EStatisticType type,
			User user) {

		if(EUserRole.valueOf(user.getRole().toString()) != EUserRole.ADMIN){
			idAdmins.clear();
			idAdmins.add(user.getId());
		}

		if(month == null && quarter == null && type == null){
			throw new InvalidInputDataException("You must specify type of statistic if statistic by year");
		}

		List<StatisticDTO> os = statisticRepo.statistic(idBuyers, idAdmins, month, quarter, year, type);
		return serviceUtils.convertToListResponse(os, StatisticDTO.class);
	}

	// public DataResponse<TodayStatisticDTO> todayStatistic() {

	// 	TodayStatisticDTO os = statisticRepo.todayStatistic(); // include income and new order
	// 	os.setNewBuyer(userRepo.countByCreateTime(new Date())); // include new buyer
	// 	os.setNewProduct(productRepo.countByCreateTime(new Date())); // include new product

	// 	return serviceUtils.convertToDataResponse(os, TodayStatisticDTO.class);
	// }
}
