package com.myclass.controller.admin;

import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestMethod;

import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.CourseDto;
import com.myclass.dto.TargetDto;
import com.myclass.dto.VideoDto;
import com.myclass.entity.Target;
import com.myclass.entity.Video;
import com.myclass.service.CategoryService;
import com.myclass.service.CloudinaryService;
import com.myclass.service.CourseService;
import com.myclass.service.TargetService;
import com.myclass.service.UserService;
import com.myclass.service.VideoService;

@Controller
@RequestMapping(UrlConstants.ADMIN_COURSE)
public class AdminCourseController {
	@Autowired
	CourseService courseService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	VideoService videoService;
	@Autowired
	TargetService targetService;
	@Autowired
	UserService userService;
	@Autowired
	CloudinaryService cloudinaryService;

	@RequestMapping(value = UrlConstants.GET, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllDto());
		return UrlConstants.COURSE_LIST;
	}

	@GetMapping(UrlConstants.ADD)
	public String add(ModelMap modelMap) {
		modelMap.addAttribute(ObjectConstants.LECTURER_LIST, userService.findDtoByAllLecturer());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.COURSE_ITEM, new CourseDto());
		return UrlConstants.COURSE_ADD;
	}

	@PostMapping(UrlConstants.ADD)
	public String add(ModelMap modelMap, @Valid @ModelAttribute(ObjectConstants.COURSE_ITEM) CourseDto dto, BindingResult error) {
		if (error.hasErrors()) {
			modelMap.addAttribute(ObjectConstants.LECTURER_LIST, userService.findDtoByAllLecturer());
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			return UrlConstants.COURSE_ADD;
		}
		String oldImage = dto.getImage();
		String cloudinaryImgURL = ObjectConstants.EMPTY_STRING;
		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		dto.setImage(cloudinaryImgURL);
		if (courseService.addDto(dto))
			return UrlConstants.COURSE_REDIRECT;
		modelMap.addAttribute(ObjectConstants.LECTURER_LIST, userService.findDtoByAllLecturer());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_INSERT);
		return UrlConstants.COURSE_ADD;
	}

	@GetMapping(UrlConstants.EDIT_ID)
	public String edit(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		CourseDto dto = courseService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute(ObjectConstants.LECTURER_LIST, userService.findDtoByAllLecturer());
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			modelMap.addAttribute(ObjectConstants.COURSE_ITEM, dto);
			return UrlConstants.COURSE_EDIT;
		}
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.COURSE_NOT_EXISTS);
		return UrlConstants.COURSE_REDIRECT;

	}

	@PostMapping(UrlConstants.EDIT)
	public String edit(Model modelMap, @Valid @ModelAttribute(ObjectConstants.COURSE_ITEM) CourseDto dto, BindingResult error) {
		if (error.hasErrors()) {
			System.out.println(error);
			modelMap.addAttribute(ObjectConstants.LECTURER_LIST, userService.findDtoByAllLecturer());
			modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
			return UrlConstants.COURSE_EDIT;
		}
		String oldImage = dto.getImage();
		String cloudinaryImgURL = ObjectConstants.EMPTY_STRING;
		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		dto.setImage(cloudinaryImgURL);
		if (courseService.updateDto(dto)) {
			return UrlConstants.COURSE_REDIRECT;
		}
		modelMap.addAttribute(ObjectConstants.LECTURER_LIST, userService.findDtoByAllLecturer());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_UPDATE);
		return UrlConstants.COURSE_EDIT;

	}

	@GetMapping(UrlConstants.DELETE_ID)
	public String delete(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		if (!courseService.deleteById(id)) {
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_DELETE);
			modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllDto());
			return UrlConstants.COURSE_LIST;
		}
		return UrlConstants.COURSE_REDIRECT;

	}

	@GetMapping(UrlConstants.VIDEO_ID)
	public String search(Model modelMap, @PathVariable(UrlConstants.ID) int id) {

		modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(id));
		modelMap.addAttribute(ObjectConstants.VIDEO_LIST, videoService.findAllDtoByCourseId(id));
		return UrlConstants.VIDEO_LIST_IN_COURSE;

	}

	@GetMapping(UrlConstants.VIDEO_ADD_ID)
	public String addVideo(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		VideoDto dto = new VideoDto();
		CourseDto tmpCourse = courseService.findDtoById(id);
		if (tmpCourse != null) {
			dto.setCourseId(tmpCourse.getId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, dto);
			return UrlConstants.VIDEO_ADD_IN_COURSE;
		}
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllDto());
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.COURSE_NOT_EXISTS);
		return UrlConstants.COURSE_LIST;
	}

	@PostMapping(UrlConstants.VIDEO_ADD_IN_COURSE_POST)
	public String addVideo(Model modelMap, @Valid @ModelAttribute(ObjectConstants.VIDEO_ITEM) VideoDto dto, BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, dto);
			return UrlConstants.VIDEO_ADD_IN_COURSE;
		}
		if (videoService.addDto(dto)) {
			return UrlConstants.VIDEO_ADD_IN_COURSE_REDIRECT + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, dto);
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_INSERT);
		return UrlConstants.VIDEO_ADD_IN_COURSE;

	}

	@GetMapping(UrlConstants.VIDEO_EDIT_ID)
	public String editVideo(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		VideoDto dto = videoService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, dto);
			return UrlConstants.VIDEO_EDIT_IN_COURSE;
		}
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllDto());
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.VIDEO_NOT_EXISTS);
		return UrlConstants.COURSE_LIST;

	}

	@PostMapping(UrlConstants.VIDEO_EDIT_IN_COURSE_POST)
	public String editVideo(Model modelMap, @Valid @ModelAttribute(ObjectConstants.VIDEO_ITEM) VideoDto dto, BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, dto);
			return UrlConstants.VIDEO_EDIT_IN_COURSE;
		}
		if (videoService.updateDto(dto)) {
			return UrlConstants.VIDEO_LIST_IN_COURSE_BY_COURSE_ID + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, dto);
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_UPDATE);
		return UrlConstants.VIDEO_EDIT_IN_COURSE;

	}

	@GetMapping(UrlConstants.VIDEO_DELETE_ID)
	public String deleteVideo(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		Optional<Video> video = videoService.findById(id);
		if (!videoService.deleteById(id))
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_DELETE);
		return UrlConstants.VIDEO_LIST_IN_COURSE_BY_COURSE_ID + video.get().getCourseId();

	}

	@GetMapping(UrlConstants.TARGET_ID)
	public String indexTarget(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(id));
		modelMap.addAttribute(ObjectConstants.TARGET_LIST, targetService.findAllDtoByCourseId(id));
		return UrlConstants.TARGET_LIST_IN_COURSE;

	}

	@GetMapping(UrlConstants.TARGET_ADD_ID)
	public String addTarget(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		TargetDto dto = new TargetDto();
		CourseDto tmpCourse = courseService.findDtoById(id);
		if (tmpCourse != null) {
			dto.setCourseId(tmpCourse.getId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute(ObjectConstants.TARGET_ITEM, dto);
			return UrlConstants.TARGET_ADD_IN_COURSE;
		}
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllDto());
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.COURSE_NOT_EXISTS);
		return UrlConstants.COURSE_LIST;
	}

	@PostMapping(UrlConstants.TARGET_ADD_IN_COURSE_POST)
	public String addTarget(Model modelMap, @Valid @ModelAttribute(ObjectConstants.TARGET_ITEM) TargetDto dto, BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute(ObjectConstants.TARGET_ITEM, dto);
			return UrlConstants.TARGET_ADD_IN_COURSE;
		}
		if (targetService.addDto(dto)) {
			return UrlConstants.TARGET_ADD_IN_COURSE_REDIRECT + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute(ObjectConstants.TARGET_ITEM, dto);
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_INSERT);
		return UrlConstants.TARGET_ADD_IN_COURSE;

	}

	@GetMapping(UrlConstants.TARGET_EDIT_ID)
	public String editTarget(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		TargetDto dto = targetService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute(ObjectConstants.TARGET_ITEM, dto);
			return UrlConstants.TARGET_EDIT_IN_COURSE;
		}
		return UrlConstants.COURSE_REDIRECT;
	}

	@PostMapping(UrlConstants.TARGET_EDIT_IN_COURSE_POST)
	public String editTarget(Model modelMap, @Valid @ModelAttribute(ObjectConstants.TARGET_ITEM) TargetDto dto, BindingResult error) {
		if (error.hasErrors()) {
			CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
			dto.setCourseName(tmpCourse.getTitle());
			modelMap.addAttribute(ObjectConstants.TARGET_ITEM, dto);
			return UrlConstants.TARGET_EDIT_IN_COURSE;
		}
		if (targetService.updateDto(dto)) {
			return UrlConstants.TARGET_LIST_IN_COURSE_BY_COURSE_ID + dto.getCourseId();
		}
		CourseDto tmpCourse = courseService.findDtoById(dto.getCourseId());
		dto.setCourseName(tmpCourse.getTitle());
		modelMap.addAttribute(ObjectConstants.TARGET_ITEM, dto);
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_UPDATE);
		return UrlConstants.TARGET_EDIT_IN_COURSE;

	}

	@GetMapping(UrlConstants.TARGET_DELETE_ID)
	public String deleteTarget(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		Optional<Target> target = targetService.findById(id);
		if (!targetService.deleteById(id))
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_DELETE);
		return UrlConstants.TARGET_LIST_IN_COURSE_BY_COURSE_ID + target.get().getCourseId();

	}
}
