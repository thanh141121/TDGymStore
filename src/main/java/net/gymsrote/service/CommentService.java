package net.gymsrote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.gymsrote.controller.payload.request.CreateCommentRequest;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.controller.payload.response.ListWithPagingResponse;
import net.gymsrote.dto.CommentDTO;
import net.gymsrote.entity.comment.Comment;
import net.gymsrote.entity.comment.CommentKey;
import net.gymsrote.entity.product.Product;
import net.gymsrote.entity.user.User;
import net.gymsrote.repository.CommentRepo;
import net.gymsrote.repository.ProductRepo;
import net.gymsrote.repository.UserRepo;
import net.gymsrote.service.utils.ServiceUtils;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ProductRepo productRepo;

	@Autowired
	ServiceUtils serviceUtils;
	
	public ListWithPagingResponse<CommentDTO> getCommentForProduct(Long prodcutId, Pageable pageable) {
		return serviceUtils.convertToListResponse(
				commentRepo.findAllOfProduct(prodcutId, pageable),
				CommentDTO.class
				);
	}
	
	public ListWithPagingResponse<CommentDTO> getAllCommentForAdmin(Pageable pageable) {
		return serviceUtils.convertToListResponse(
				commentRepo.findAll(pageable),
				CommentDTO.class
				);
	}
	
	@Transactional
	public DataResponse<CommentDTO> create(CreateCommentRequest data, Long idUser){

		User user = userRepo.getReferenceById(idUser);
		Product product = productRepo.getReferenceById(data.getProductId());
		Comment c = new Comment(user, product, data.getRate(), data.getDescription());
		return serviceUtils.convertToDataResponse(
				commentRepo.save(c),
				CommentDTO.class
				);
	}
	
	public void delete(Long userId, Long productId) {
		commentRepo.deleteById(new CommentKey(userId, productId));
	}

}
