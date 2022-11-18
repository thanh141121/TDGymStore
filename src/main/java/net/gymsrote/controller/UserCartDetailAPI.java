package net.gymsrote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.service.UserService;

@RestController
// @RequestMapping("/api/user")
public class UserCartDetailAPI {
	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET, value = "/api/javainuse")
	public String sayHello() {
		return "Swagger Hello World";
	}
	//
	// @GetMapping(value = "/new")
	// public NewOutput showNew(@RequestParam("page") int page,
	// @RequestParam("limit") int limit) {
	// NewOutput result = new NewOutput();
	// result.setPage(page);
	// Pageable pageable = PageRequest.of(page -1, limit);
	// result.setListResult(newService.findAll(pageable));
	// result.setTotalPage((int) Math.ceil((double) (newService.totalItem()) / limit));
	// return result;
	// }
	//
	// @PostMapping(value = "/new")
	// public NewDTO createNew(@RequestBody NewDTO model) {
	// return newService.save(model);
	// }
	//
	// @PutMapping(value="/new/{id}")
	// public NewDTO updateNew(@RequestBody NewDTO model, @PathVariable("id") long id) {
	// model.setId(id);
	// return newService.save(model);
	//
	// }
	//
	// @DeleteMapping(value = "/new")
	// public void deleteNew(@RequestBody long[] ids) {
	// newService.delete(ids);
	// }
}
