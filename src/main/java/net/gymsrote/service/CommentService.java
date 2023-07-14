package net.gymsrote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.payload.request.CreateCommentRequest;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.controller.payload.response.ListWithPagingResponse;
import net.gymsrote.dto.CommentDTO;
import net.gymsrote.entity.comment.Comment;
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
				CommentDTO.class);
	}

	public ListWithPagingResponse<CommentDTO> getAllCommentForAdmin(Pageable pageable) {
		return serviceUtils.convertToListResponse(
				commentRepo.findAll(pageable),
				CommentDTO.class);
	}

	@Transactional
	public DataResponse<CommentDTO> create(CreateCommentRequest data, Long idUser) {

		User user = userRepo.getReferenceById(idUser);
		Product product = productRepo.getReferenceById(data.getProductId());
		Comment c = new Comment(user, product, data.getRate(), data.getDescription());
		int rate = (int) data.getRate().longValue();
		switch (rate) {
			case 1:
				product.setRating1(product.getRating1() + 1);
				break;
			case 2:
				product.setRating2(product.getRating2() + 1);
				break;
			case 3:
				product.setRating3(product.getRating3() + 1);
				break;
			case 4:
				product.setRating4(product.getRating4() + 1);
				break;
			case 5:
				product.setRating5(product.getRating5() + 1);
				break;
			default:
				throw new InvalidInputDataException("Invalid Rating");

		}
		productRepo.save(product);
		commentRepo.save(c);
		product.setAverageRating(countAverageRating(product));
		return serviceUtils.convertToDataResponse(
				c,
				CommentDTO.class);
	}

	@Transactional
	public void delete(Long id) {
		Comment c = commentRepo.getReferenceById(id);
		commentRepo.deleteById(id);
		Product p = c.getProduct();
		switch (c.getRate().intValue()) {
			case 1:
				p.setRating1(p.getRating1() - 1);
				break;
			case 2:
				p.setRating2(p.getRating2() - 1);
				break;
			case 3:
				p.setRating3(p.getRating3() - 1);
				break;
			case 4:
				p.setRating4(p.getRating4() - 1);
				break;
			case 5:
				p.setRating5(p.getRating5() - 1);
				break;

		}
		productRepo.saveAndFlush(p);
		p.setAverageRating(countAverageRating(p));
	}

	public DataResponse<CommentDTO> getCommentById(Long id) {
		return serviceUtils.convertToDataResponse(
				commentRepo.getReferenceById(id),
				CommentDTO.class);
	}

	private Double countAverageRating(Product p) {
		Long total = (long) (p.getRating1() + p.getRating2() * 2 + p.getRating3() * 3 + p.getRating4() * 4
				+ p.getRating5() * 5);
		int length = p.getComments().size();
		if (length > 0)
			return (double) total / length;
		return 0.0;
	}

}
