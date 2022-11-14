package net.gymsrote.controller.admin;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.service.OrderService;
import net.gymsrote.entity.EnumEntity.EOrderStatus;
import net.gymsrote.entity.EnumEntity.EPaymentMethod;
import net.gymsrote.entity.EnumEntity.filter.OrderFilter;
import net.gymsrote.entity.EnumEntity.filter.PagingInfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderManageController {
	@Autowired
	OrderService orderService;

	@Operation(summary = "Get order list with given search criteria")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful",
			content = {@Content(mediaType = "application/json")})})
	@GetMapping(produces = "application/json")
	public ResponseEntity<?> getBuyers(
			@RequestParam(required = false) @Parameter(description = "Id of buyer") Long idBuyer,
			@RequestParam(required = false) @Parameter(description = "Id of seller") Long idSeller,
			@RequestParam(required = false) @Parameter(
					description = "Id of delivery address") Long idDeliveryAddress,
			@RequestParam(required = false) @Parameter(
					description = "Date of order (MM/dd/yyyy)") Date createTime,
			@RequestParam(required = false) @Parameter(
					description = "Status of order") EOrderStatus status,
			@RequestParam(required = false) @Parameter(
					description = "Payment method of order") EPaymentMethod paymentMethod,
			@RequestParam(required = false) @Parameter(
					description = "Specify page number") Integer page,
			@RequestParam(required = false) @Parameter(
					description = "Specify page size") Integer size,
			@RequestParam(required = false) @Min(1) @Max(3) @Parameter(
					description = "Specify sort by condition" + "<br>sortBy value:\r\n" + "<br>\r\n"
							+ "&nbsp;&nbsp;&nbsp;&nbsp; 1: &nbsp;&nbsp;by createTime\r\n"
							+ "<br>\r\n" + "&nbsp;&nbsp;&nbsp;&nbsp; 2: &nbsp;&nbsp;by price\r\n"
							+ "<br><i>Example: sortBy = (1+2) = 3 => sort by createTime and price attribute</i>") Integer sortBy,
			@RequestParam(required = false) @Parameter(
					description = "Specify sort order. True for sort in descending order.") Boolean sortDescending) {
		return ResponseEntity
				.ok(orderService.search(
						new OrderFilter(idBuyer, idSeller, idDeliveryAddress, createTime, status,
								paymentMethod),
						new PagingInfo(page, size, sortBy, sortDescending), false));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		return ResponseEntity.ok(orderService.get(id, null));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateOrderStatus(@PathVariable Long id,
			@RequestParam("new-status") EOrderStatus newStatus) {
		return ResponseEntity.ok(orderService.updateStatus(id, null, newStatus));
	}
}
