package net.gymsrote.controller.payload.response;

import lombok.Getter;
import lombok.Setter;
import net.gymsrote.controller.advice.exception.RemoteUploadException;

@Getter
@Setter
public class CloudinaryUploadResponse {
	private String url;

	private String publicId;

	private String resourceType;

	public CloudinaryUploadResponse(String url, String publicId, String resourceType) {
		if (url.isEmpty()) {
			throw new RemoteUploadException(
					"Upload file to cloudinary failed (no URL to access uploaded media found in Cloudinary response)");
		}
		if (publicId.isEmpty()) {
			throw new RemoteUploadException(
					"Upload file to cloudinary failed (no public_id found in Cloudinary response)");
		}
		if (resourceType.isEmpty()) {
			throw new RemoteUploadException(
					"Upload file to cloudinary failed (no resource_type found in Cloudinary response)");
		}
		this.url = url;
		this.publicId = publicId;
		this.resourceType = resourceType;
	}
}
