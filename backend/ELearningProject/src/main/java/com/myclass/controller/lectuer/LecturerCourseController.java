package com.myclass.controller.lectuer;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.CourseDto;
import com.myclass.dto.EnrollmentDto;
import com.myclass.dto.LoginDto;
import com.myclass.dto.TargetDto;
import com.myclass.dto.UserDto;
import com.myclass.dto.VideoDto;
import com.myclass.service.CategoryService;
import com.myclass.service.CloudinaryService;
import com.myclass.service.CourseService;
import com.myclass.service.EnrollmentService;
import com.myclass.service.RoleService;
import com.myclass.service.TargetService;
import com.myclass.service.UserInformationService;
import com.myclass.service.UserService;
import com.myclass.service.VideoService;

@Controller
@RequestMapping(value = { UrlConstants.LECTURER_COURSE })
public class LecturerCourseController {
	@Autowired
	CourseService courseService;
	@Autowired
	EnrollmentService enrollmentService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	TargetService targetService;
	@Autowired
	VideoService videoService;
	@Autowired
	UserInformationService userInformationService;
	@Autowired
	CloudinaryService cloudinaryService;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@GetMapping(UrlConstants.LECTURER_COURSE_DETAIL)
	public String index(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(id));
		modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(id));
		modelMap.addAttribute(ObjectConstants.TARGET_LIST, targetService.findAllDtoByCourseId(id));
		modelMap.addAttribute(ObjectConstants.VIDEO_LIST, videoService.findAllDtoByCourseId(id));
		modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, courseService.findCategoryByCourseId(id));
		modelMap.addAttribute(ObjectConstants.NUMBER_OF_STUDENT, enrollmentService.countEnrollmentInCourse(id));
		return UrlConstants.COURSE_DETAIL_OF_LECTURER;
	}

	@GetMapping(UrlConstants.ADD)
	public String add(Model modelMap) {
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.COURSE_ITEM, new CourseDto());
		return UrlConstants.LECTURER_ADD_COURSE;

	}

	@GetMapping(UrlConstants.DELETE_ID)
	public String add(ModelMap modelMap, @PathVariable(UrlConstants.ID) int id) {

		courseService.deleteById(id);
		return UrlConstants.LECTURER_REDIRECT;
	}

	@PostMapping(UrlConstants.ADD)
	public String add(Model modelMap, @Valid @ModelAttribute(ObjectConstants.COURSE_ITEM) CourseDto dto,
			BindingResult error) {
		LoginDto account = userInformationService.getUserInformation();
		if (error.hasErrors()) {
			modelMap.addAttribute(ObjectConstants.ACCOUNT, account);
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());

			return UrlConstants.LECTURER_ADD_COURSE;
		}
		String oldImage = dto.getImage();
		String cloudinaryImgURL = ObjectConstants.EMPTY_STRING;
		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		dto.setImage(cloudinaryImgURL);
		dto.setAuthor(account.getFullname());
		if (courseService.addDto(dto)) {
			return UrlConstants.LECTURER_REDIRECT;
		}
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_INSERT);
		return UrlConstants.LECTURER_ADD_COURSE;
	}

	@GetMapping(UrlConstants.EDIT_ID)
	public String edit(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());

		CourseDto dto = courseService.findDtoById(id);

		if (dto != null) {
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(id));
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, dto);
			return UrlConstants.LECTURER_EDIT_COURSE;
		}
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.COURSE_NOT_EXISTS);
		return UrlConstants.LECTURER_REDIRECT_EDIT_BY_ID + id;

	}

	@PostMapping(UrlConstants.EDIT)
	public String edit(Model modelMap, @Valid @ModelAttribute(ObjectConstants.COURSE_ITEM) CourseDto dto,
			BindingResult error) {
		LoginDto account = userInformationService.getUserInformation();

		if (error.hasErrors()) {
			modelMap.addAttribute(ObjectConstants.ACCOUNT, account);
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(dto.getId()));

			return UrlConstants.LECTURER_EDIT_COURSE;
		}
		String oldImage = dto.getImage();
		String cloudinaryImgURL = ObjectConstants.EMPTY_STRING;
		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		dto.setImage(cloudinaryImgURL);
		dto.setAuthor(account.getFullname());
		if (courseService.updateDto(dto)) {
			return UrlConstants.LECTURER_REDIRECT_DETAIL_COURSE_BY_ID + dto.getId();
		}
		modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(dto.getId()));
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_UPDATE);
		return UrlConstants.LECTURER_REDIRECT_DETAIL_COURSE_BY_ID + dto.getId();

	}

	@GetMapping(UrlConstants.VIDEO_ADD_ID)
	public String addVideo(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		VideoDto dto = new VideoDto();
		CourseDto tmpCourse = courseService.findDtoById(id);

		if (tmpCourse != null) {
			dto.setCourseId(tmpCourse.getId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, dto);
			modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(id));
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(id));
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			return UrlConstants.LECTURER_ADD_VIDEO_TO_COURSE;
		}
		return UrlConstants.LECTURER_REDIRECT_DETAIL_COURSE_BY_ID + id;
	}

	@PostMapping(UrlConstants.VIDEO_ADD_IN_COURSE_POST)
	public String addVideo(Model modelMap, @Valid @ModelAttribute(ObjectConstants.VIDEO_ITEM) VideoDto dto,
			BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM,
					courseService.findCategoryByCourseId(dto.getCourseId()));
			modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, dto);
			return UrlConstants.LECTURER_ADD_VIDEO_TO_COURSE;
		}
		if (videoService.addDto(dto)) {
			return UrlConstants.LECTURER_REDIRECT_DETAIL_COURSE_BY_ID + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, dto);
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_INSERT);
		return UrlConstants.LECTURER_ADD_VIDEO_TO_COURSE;

	}

	@GetMapping(UrlConstants.VIDEO_EDIT_ID)
	public String editVideo(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		VideoDto dto = videoService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, dto);
			modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM,
					courseService.findCategoryByCourseId(dto.getCourseId()));
			return UrlConstants.LECTURER_EDIT_VIDEO_TO_COURSE;
		}
		return UrlConstants.LECTURER_REDIRECT;
	}

	@PostMapping(UrlConstants.VIDEO_EDIT_IN_COURSE_POST)
	public String editVideo(Model modelMap, @Valid @ModelAttribute(ObjectConstants.VIDEO_ITEM) VideoDto dto,
			BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM,
					courseService.findCategoryByCourseId(dto.getCourseId()));
			modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, dto);
			return UrlConstants.LECTURER_EDIT_VIDEO_TO_COURSE;
		}
		if (videoService.updateDto(dto)) {
			return UrlConstants.LECTURER_REDIRECT_DETAIL_COURSE_BY_ID + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(dto.getCourseId()));
		modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(dto.getCourseId()));
		modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, dto);
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_UPDATE);
		return UrlConstants.LECTURER_EDIT_VIDEO_TO_COURSE;
	}

	@GetMapping(UrlConstants.VIDEO_DELETE_ID)
	public String deleteVideo(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		VideoDto dto = videoService.findDtoById(id);
		if (!videoService.deleteById(id))
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_DELETE);
		return UrlConstants.LECTURER_REDIRECT_DETAIL_COURSE_BY_ID + dto.getCourseId();

	}

	@GetMapping(UrlConstants.TARGET_ADD_ID)
	public String addTarget(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		TargetDto dto = new TargetDto();
		CourseDto tmpCourse = courseService.findDtoById(id);
		if (tmpCourse != null) {
			dto.setCourseId(tmpCourse.getId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(id));
			modelMap.addAttribute(ObjectConstants.TARGET_ITEM, dto);
			return UrlConstants.LECTURER_ADD_TARGET_TO_COURSE;
		}
		return UrlConstants.LECTURER_REDIRECT_DETAIL_COURSE_BY_ID + id;
	}

	@PostMapping(UrlConstants.TARGET_ADD_IN_COURSE_POST)
	public String addTarget(Model modelMap, @Valid @ModelAttribute(ObjectConstants.TARGET_ITEM) TargetDto dto,
			BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute(ObjectConstants.TARGET_ITEM, dto);
			modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM,
					courseService.findCategoryByCourseId(dto.getCourseId()));
			return UrlConstants.LECTURER_ADD_TARGET_TO_COURSE;
		}
		if (targetService.addDto(dto)) {

			return UrlConstants.LECTURER_REDIRECT_DETAIL_COURSE_BY_ID + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute(ObjectConstants.TARGET_ITEM, dto);
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_INSERT);
		return UrlConstants.LECTURER_ADD_TARGET_TO_COURSE;

	}

	@GetMapping(UrlConstants.TARGET_EDIT_ID)
	public String editTarget(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		TargetDto dto = targetService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute(ObjectConstants.TARGET_ITEM, dto);
			modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM,
					courseService.findCategoryByCourseId(dto.getCourseId()));
			return UrlConstants.LECTURER_EDIT_TARGET_TO_COURSE;
		}
		return UrlConstants.LECTURER_REDIRECT;
	}

	@PostMapping(UrlConstants.TARGET_EDIT_IN_COURSE_POST)
	public String editTarget(Model modelMap, @Valid @ModelAttribute(ObjectConstants.TARGET_ITEM) TargetDto dto,
			BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute(ObjectConstants.TARGET_ITEM, dto);
			modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM,
					courseService.findCategoryByCourseId(dto.getCourseId()));
			return UrlConstants.LECTURER_EDIT_TARGET_TO_COURSE;
		}
		if (targetService.updateDto(dto)) {
			return UrlConstants.LECTURER_REDIRECT_DETAIL_COURSE_BY_ID + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute(ObjectConstants.TARGET_ITEM, dto);
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(dto.getCourseId()));
		modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(dto.getCourseId()));
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_UPDATE);
		return UrlConstants.LECTURER_EDIT_TARGET_TO_COURSE;
	}

	@GetMapping(UrlConstants.TARGET_DELETE_ID)
	public String deleteTarget(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		TargetDto dto = targetService.findDtoById(id);
		if (!targetService.deleteById(id))
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_DELETE);
		return UrlConstants.LECTURER_REDIRECT_DETAIL_COURSE_BY_ID + dto.getCourseId();
	}

	@GetMapping(UrlConstants.LECTURER_MEMBER_ID)
	public String member(ModelMap modelMap, @PathVariable(UrlConstants.ID) int id) {
		modelMap.addAttribute(ObjectConstants.ID_CO_HOST,
				roleService.findDtoByName(ObjectConstants.TEACHING_ASSISTANT).getId());
		modelMap.addAttribute(ObjectConstants.USER_LIST, enrollmentService.findAllMember(id));
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(id));
		modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(id));
		return UrlConstants.LECTURER_VIEW_MEMBER_BY_COURSE_ID;
	}

	@GetMapping(UrlConstants.LECTURER_DELETE_MEMBER_BY_COURSE_ID)

	public String kichOut(Model modelMap, @PathVariable(UrlConstants.ID) int id,
			@PathVariable(UrlConstants.COURSE_ID) int courseId) {
		UserDto dto = userService.findDtoById(id);
		LoginDto account = userInformationService.getUserInformation();

		if (dto.getRoleName().equals(ObjectConstants.TEACHER) || dto.getRoleName().equals(ObjectConstants.ADMIN)
				|| (dto.getRoleName().equals(ObjectConstants.TEACHING_ASSISTANT)
						&& account.getRoleName().equals(ObjectConstants.TEACHING_ASSISTANT))) {
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_DELETE);
			modelMap.addAttribute(ObjectConstants.ID_CO_HOST,
					roleService.findDtoByName(ObjectConstants.TEACHING_ASSISTANT).getId());
			modelMap.addAttribute(ObjectConstants.USER_LIST, enrollmentService.findAllMember(courseId));
			modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(courseId));
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(courseId));
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.KICK_MEMBER_FAILURE);
			return UrlConstants.LECTURER_VIEW_MEMBER_BY_COURSE_ID;

		}
		enrollmentService.kikAMember(courseId, id);
		userService.cancleToMember(id, courseId);
		return UrlConstants.LECTURER_REDIRECT_MEMBER_BY_COURSE_ID + courseId;

	}

	@SuppressWarnings(ObjectConstants.UNUSE_TYPE)
	@GetMapping(UrlConstants.LECTURER_ADD_MEMBER_BY_COURSE_ID)
	public String addMember(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		CourseDto tmpCourse = courseService.findDtoById(id);
		EnrollmentDto dto = new EnrollmentDto();
		dto.setCourseName(tmpCourse.getTitle());
		dto.setCourseId(id);
		if (tmpCourse != null) {
			modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, tmpCourse);
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(id));
			modelMap.addAttribute(ObjectConstants.USER_LIST, enrollmentService.findAllWhoNotMember(id));
			modelMap.addAttribute(ObjectConstants.ENROLLMENT_ITEM, dto);
			return UrlConstants.LECTURER_ADD_MEMBER_TO_COURSE;
		} else
			return UrlConstants.LECTURER_REDIRECT_MEMBER_BY_COURSE_ID + id;
	}

	@PostMapping(UrlConstants.LECTURER_ADD_MEMBER_POST)
	public String addMember(Model modelMap, @Valid @ModelAttribute(ObjectConstants.ENROLLMENT_ITEM) EnrollmentDto dto,
			BindingResult error) {

		if (error.hasErrors()) {
			modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(dto.getCourseId()));
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM,
					courseService.findCategoryByCourseId(dto.getCourseId()));
			return UrlConstants.LECTURER_ADD_MEMBER_TO_COURSE;
		}
		if (enrollmentService.addMember(dto)) {

			return UrlConstants.LECTURER_REDIRECT_MEMBER_BY_COURSE_ID + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(dto.getCourseId()));
		modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(dto.getCourseId()));
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ADD_MEMBER_FAILURE);
		return UrlConstants.LECTURER_ADD_MEMBER_TO_COURSE;

	}

	@GetMapping(UrlConstants.AUTHORIZE_MEMBER_BY_COURSE_ID_AND_MEMBER_ID)

	public String authorize(Model modelMap, @PathVariable(UrlConstants.ID) int id,
			@PathVariable(UrlConstants.COURSE_ID) int courseId) {
		UserDto dto = userService.findDtoById(id);
		LoginDto account = userInformationService.getUserInformation();
		String message = ObjectConstants.AUTHORIZE_MEMBER_FAILURE;
		if (dto.getRoleName().equals(ObjectConstants.ADMIN) || dto.getRoleName().equals(ObjectConstants.TEACHER)
				|| account.getRoleName().equals(ObjectConstants.TEACHING_ASSISTANT)) {
			if (account.getRoleName().equals(ObjectConstants.TEACHING_ASSISTANT))
				message = ObjectConstants.AUTHORIZE_MEMBER_FAILURE_BY_PERMISSION;
			modelMap.addAttribute(ObjectConstants.ID_CO_HOST,
					roleService.findDtoByName(ObjectConstants.TEACHING_ASSISTANT).getId());
			modelMap.addAttribute(ObjectConstants.USER_LIST, enrollmentService.findAllMember(courseId));
			modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(courseId));
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(courseId));
			modelMap.addAttribute(ObjectConstants.MESSAGE, message);
			return UrlConstants.LECTURER_VIEW_MEMBER_BY_COURSE_ID;

		}
		userService.authorizeToCoHost(id, courseId);
		return UrlConstants.LECTURER_REDIRECT_MEMBER_BY_COURSE_ID + courseId;

	}

	@GetMapping(UrlConstants.CANCEL_MEMBER_BY_COURSE_ID_AND_MEMBER_ID)

	public String cancleRight(Model modelMap, @PathVariable(UrlConstants.ID) int id,
			@PathVariable(UrlConstants.COURSE_ID) int courseId) {
		UserDto dto = userService.findDtoById(id);
		LoginDto account = userInformationService.getUserInformation();
		if (!dto.getRoleName().equals(ObjectConstants.TEACHING_ASSISTANT)
				|| account.getRoleName().equals(ObjectConstants.TEACHING_ASSISTANT)) {
			modelMap.addAttribute(ObjectConstants.NUMBER_OF_STUDENT,
					roleService.findDtoByName(ObjectConstants.TEACHING_ASSISTANT).getId());
			modelMap.addAttribute(ObjectConstants.USER_LIST, enrollmentService.findAllMember(courseId));
			modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(courseId));
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(courseId));
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.CANCEL_MEMBER_FAILURE_BY_PERMISSION);
			return UrlConstants.LECTURER_VIEW_MEMBER_BY_COURSE_ID;

		} else
			userService.cancleToMember(id, courseId);

		return UrlConstants.LECTURER_REDIRECT_MEMBER_BY_COURSE_ID + courseId;

	}
}
