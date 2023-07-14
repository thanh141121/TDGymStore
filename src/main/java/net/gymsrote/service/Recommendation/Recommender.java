package net.gymsrote.service.Recommendation;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import net.gymsrote.controller.payload.response.ListResponse;
import net.gymsrote.dto.ProductGeneralDetailDTO;
import net.gymsrote.entity.product.Product;
import net.gymsrote.repository.ProductRepo;
import net.gymsrote.service.utils.ServiceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class Recommender {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	ServiceUtils serviceUtils;

    public ListResponse<ProductGeneralDetailDTO> recommendItems(long userId, int numRecommendations) throws IOException, TasteException {
    	DataModel model = new FileDataModel(new File("data/dataIB.csv"));

        ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
        List<RecommendedItem> recommendedItems  = recommender.recommend(userId, numRecommendations);
        List<Product> products = new ArrayList<>();
        for (RecommendedItem recommendedItem : recommendedItems) {
            Long productId = recommendedItem.getItemID();
            Product product = productRepo.findById(productId).orElse(null);
            if (product != null) {
                products.add(product);
            }
        }

        return serviceUtils.convertToListResponse(products, ProductGeneralDetailDTO.class);
    }
}
