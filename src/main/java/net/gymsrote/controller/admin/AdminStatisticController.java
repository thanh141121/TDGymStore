package net.gymsrote.controller.admin;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.entity.EnumEntity.EStatisticType;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.StatisticService;

@RestController
@RequestMapping("/api/admin/statistic")
public class AdminStatisticController {
	@Autowired
	StatisticService statisticService;
	
	@GetMapping
	public ResponseEntity<?> statistic(
			@Parameter(description = "Search by buyer id")
			@RequestParam(required = false) List<Long> idBuyer,

			@Parameter(description = "Search by seller id")
			@RequestParam(required = false) List<Long> idAdmin,

			@Parameter(description = "Month to statistic")
			@RequestParam(required = false) Integer month,

			@Parameter(description = "Quarter to statistic")
			@RequestParam(required = false) Integer quarter,

			@Parameter(description = "Year to statistic")
			@RequestParam(required = true) Integer year,

			@Parameter(description = "Type (month or quarter) if statistic by year")
			@RequestParam(required = false) EStatisticType type,
			
			@AuthenticationPrincipal UserDetailsImpl<User> user
			){

		var r = statisticService.statistic(idBuyer, idAdmin, month, quarter, year, type, user.getUser());
		return ResponseEntity.ok(r);
	}

	// @GetMapping("/today")
	// public ResponseEntity<?> todayStatistic(){
	// 	return ResponseEntity.ok(statisticService.todayStatistic());
	// }
}
