package com.myclass.dto;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.myclass.constant.ObjectConstants;

public class CourseDto {

	private int id;
	@NotBlank
	private String title;
	private String image;

	@Min(value = 0)
	private float price;
	@Min(value = 0)
	private int discount;
	private int categoryId;

	@NotBlank
	private String description;
	@NotBlank
	private String content;
	@Temporal(value = TemporalType.DATE)
	@DateTimeFormat(pattern = ObjectConstants.DATE_TIME_FORMAT)
	private Date lastUpdate;
	private int lectureCount;
	private int targetCount;
	private int lengthVideos;
	private int viewCount;
	private String categoryName;
	private String author;
	private float promotionPrice;
	private int totalMembers;
	private MultipartFile[] fileDatas;

	public CourseDto() {
	}

	public CourseDto(int id, String title, String image) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
	}

	public CourseDto(int id, String title, String image, float price, String description, String author) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.price = price;
		this.description = description;
		this.author = author;
	}

	public CourseDto(int id, String title, String image, float price, String description, String author, int discount) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.price = price;
		this.description = description;
		this.author = author;
		this.discount = discount;
	}

	public CourseDto(int id, String title, String image, float price, String description, String author, int discount,
			String content) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.price = price;
		this.description = description;
		this.author = author;
		this.discount = discount;
		this.content = content;
		float promotionPrice = price - price * discount / 100;
		this.promotionPrice = promotionPrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public float getPrice() {
		float roundedDouble = (float) (Math.round(price * 100.0) / 100.0);

		return roundedDouble;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getDiscount() {
		return discount;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLectureCount() {
		return lectureCount;
	}

	public void setLectureCount(int lectureCount) {
		this.lectureCount = lectureCount;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getTargetCount() {
		return targetCount;
	}

	public void setTargetCount(int targetCount) {
		this.targetCount = targetCount;
	}

	public int getLengthVideos() {
		return lengthVideos;
	}

	public void setLengthVideos(int lengthVideos) {
		this.lengthVideos = lengthVideos;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public MultipartFile[] getFileDatas() {
		return fileDatas;
	}

	public void setFileDatas(MultipartFile[] fileDatas) {
		this.fileDatas = fileDatas;
	}

	

	public float getPromotionPrice() {
		float roundedDouble = (float) (Math.round(promotionPrice * 100.0) / 100.0);
		return roundedDouble;
	}

	public void setPromotionPrice(float promotionPrice) {
		float roundedDouble = (float) (Math.round(promotionPrice * 100.0) / 100.0);
		if (roundedDouble != 0)
			this.promotionPrice = roundedDouble - (float) 0.01;
		else
			this.promotionPrice = 0;

	}

	public int getTotalMembers() {
		return totalMembers;
	}

	public void setTotalMembers(int totalMembers) {
		this.totalMembers = totalMembers;
	}

}
