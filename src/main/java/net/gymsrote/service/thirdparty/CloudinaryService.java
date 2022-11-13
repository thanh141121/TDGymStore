package net.gymsrote.service.thirdparty;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.Getter;
import lombok.Setter;
import net.gymsrote.controller.payload.response.CloudinaryUploadResponse;

@Getter @Setter
@Service
public class CloudinaryService {
	private String cloudName="drttkrguc";
	private String apiKey ="388413151371752";
	private String apiSecret="PWyZtkG0KAORMILXymfLyrH9JwI";
    
    private Cloudinary cloudinary;

    @PostConstruct
    private void init() {
    	cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", cloudName,
				"api_key", apiKey,
				"api_secret", apiSecret,
				"secure", true));
    }
    
    public CloudinaryUploadResponse upload(byte[] data) throws IOException {    	
    	Map response = cloudinary.uploader().upload(
    			data,
    			ObjectUtils.emptyMap()
    		);
    	
    	return new CloudinaryUploadResponse(
    			response.get("secure_url").toString(),
    			response.get("public_id").toString(),
    			response.get("resource_type").toString());
    }
    
    public boolean delete(String publicId) throws IOException {
        Map response = cloudinary.uploader().destroy(
        		publicId, 
        		ObjectUtils.emptyMap()
        	);
        
        return response.get("result").toString().equals("ok");
    }
    
    public boolean deleteVideo(String publicId) throws IOException {
        Map response = cloudinary.uploader().destroy(
        		publicId, 
        		ObjectUtils.asMap("resource_type", "video")
        	);
        
        return response.get("result").toString().equals("ok");
    }
}
