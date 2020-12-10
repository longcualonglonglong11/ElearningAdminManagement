package com.myclass.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.Transformation;
import com.myclass.constant.CloudConstants;
import com.myclass.constant.ObjectConstants;

public class CloudinaryConfig {
	private Cloudinary cloudinary;

	public CloudinaryConfig(@Value(CloudConstants.CLOUD_API_KEY) String key, @Value(CloudConstants.CLOUD_API_SECRET) String secret,
			@Value(CloudConstants.CLOUD_API_NAME) String cloud) {
		cloudinary = Singleton.getCloudinary();
		cloudinary.config.cloudName = cloud;
		cloudinary.config.apiSecret = secret;
		cloudinary.config.apiKey = key;
	}

	@SuppressWarnings(ObjectConstants.RAW_TYPE)
	public Map upload(Object file, Map options) {
		try {
			return cloudinary.uploader().upload(file, options);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String createUrl(String name, int width, int height, String action) {
		return cloudinary.url()
				.transformation(new Transformation().width(width).height(height).border(CloudConstants.CLOUD_TRANFORMATION).crop(action))
				.imageTag(name);
	}

}