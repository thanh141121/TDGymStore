package net.gymsrote.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.payload.request.PageInfoRequest;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.CommentService;

@RestController
@RequestMapping("/api/admin/comment")
public class AdminCommentController {
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/all")
	public ResponseEntity<?> getCommentForProduct(@RequestBody(required=false) PageInfoRequest infoRequest){
		if(infoRequest == null) infoRequest = new PageInfoRequest();
		Pageable pageable = PageRequest.of(infoRequest.getCurrentPage(), infoRequest.getSize(), infoRequest.buildSort());
		
		return ResponseEntity.ok(commentService.getAllCommentForAdmin(pageable));
		
	}
	
	@DeleteMapping("/{productId}/{userId}")
	public ResponseEntity<?> delete(
			@PathVariable("productId") long productId,
			@PathVariable("userId") long userId
			){
		commentService.delete(userId, productId);
		return ResponseEntity.accepted().body(null);
	}
}
