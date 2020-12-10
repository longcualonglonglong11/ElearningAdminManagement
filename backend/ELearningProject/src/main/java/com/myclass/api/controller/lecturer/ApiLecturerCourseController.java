package com.myclass.api.controller.lecturer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.CourseDto;
import com.myclass.dto.EnrollmentDto;
import com.myclass.dto.LoginDto;
import com.myclass.dto.TargetDto;
import com.myclass.dto.UserDto;
import com.myclass.dto.VideoDto;
import com.myclass.service.CourseService;
import com.myclass.service.EnrollmentService;
import com.myclass.service.TargetService;
import com.myclass.service.UserInformationService;
import com.myclass.service.UserService;
import com.myclass.service.VideoService;

@RestController
@RequestMapping(UrlConstants.API_LECTURER_COURSE)
public class ApiLecturerCourseController {
	@Autowired
	private UserInformationService userInformationService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private VideoService videoService;
	@Autowired
	private TargetService targetService;
	@Autowired
	private UserService userService;
	@Autowired
	private EnrollmentService enrollmentService;

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@GetMapping(UrlConstants.GET)
	public Object get() {

		LoginDto account = userInformationService.getUserInformation();
		return new ResponseEntity(courseService.findAllCourseOfLecturer(account.getFullname()), HttpStatus.OK);
	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@GetMapping(UrlConstants.API_FIND_COURSE_ID)
	public Object get(@PathVariable(UrlConstants.COURSE_ID) int courseId) {
		try {
			return new ResponseEntity(courseService.findDtoById(courseId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@PostMapping(UrlConstants.API_ADD)
	public Object post(@RequestBody CourseDto dto) {
		try {
			LoginDto account = userInformationService.getUserInformation();

			dto.setAuthor(account.getFullname());
			if (courseService.addDto(dto))
				return new ResponseEntity(ObjectConstants.API_ADD_SUCCESS, HttpStatus.BAD_GATEWAY);
			return new ResponseEntity(ObjectConstants.API_ADD_FAILURE, HttpStatus.BAD_GATEWAY);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.API_ADD_FAILURE, HttpStatus.BAD_GATEWAY);
		}

	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@PutMapping(UrlConstants.API_EDIT)
	public Object editCourse(@RequestBody CourseDto dto) {
		try {
			LoginDto account = userInformationService.getUserInformation();
			dto.setAuthor(account.getFullname());
			CourseDto curCourse = courseService.findDtoById(dto.getId());

			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_EDIT_COURSE_FAILURE, HttpStatus.BAD_GATEWAY);

			if (courseService.updateDto(dto))
				return new ResponseEntity(ObjectConstants.API_EDIT_SUCCESS, HttpStatus.OK);
			return new ResponseEntity(ObjectConstants.API_EDIT_FAILURE, HttpStatus.BAD_GATEWAY);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.API_EDIT_FAILURE, HttpStatus.BAD_GATEWAY);
		}
	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@DeleteMapping(UrlConstants.API_FIND_COURSE_ID)
	public Object put(@PathVariable(UrlConstants.COURSE_ID) int courseId) {
		try {
			LoginDto account = userInformationService.getUserInformation();
			CourseDto curCourse = courseService.findDtoById(courseId);
			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_REMOVE_COURSE_FAILURE, HttpStatus.BAD_GATEWAY);
			if (courseService.deleteById(courseId))
				return new ResponseEntity(ObjectConstants.API_DELETE_SUCCESS, HttpStatus.OK);
			return new ResponseEntity(ObjectConstants.API_DELETE_FAILURE, HttpStatus.BAD_GATEWAY);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.API_DELETE_FAILURE, HttpStatus.BAD_GATEWAY);
		}
	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@PostMapping(UrlConstants.API_LECTURER_ADD_VIDEO_BY_COURSE_ID)
	public Object addVideo(@RequestBody VideoDto dto, @PathVariable(UrlConstants.COURSE_ID) int courseId) {
		try {
			LoginDto account = userInformationService.getUserInformation();
			CourseDto curCourse = courseService.findDtoById(courseId);

			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_ADD_VIDEO_TO_YOUR_COURSE_FAILURE,
						HttpStatus.BAD_GATEWAY);
			dto.setCourseId(courseId);
			if (videoService.addDto(dto))
				return new ResponseEntity(ObjectConstants.API_LECTURER_ADD_VIDEO_TO_COURSE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.API_LECTURER_ADD_VIDEO_TO_COURSE_FAILURE, HttpStatus.OK);
		}
		return new ResponseEntity(ObjectConstants.API_LECTURER_ADD_VIDEO_TO_COURSE_FAILURE, HttpStatus.OK);

	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@PutMapping(UrlConstants.API_LECTURER_EDIT_VIDEO_BY_COURSE_ID)
	public Object editVideo(@RequestBody VideoDto dto, @PathVariable(UrlConstants.COURSE_ID) int courseId) {
		try {
			LoginDto account = userInformationService.getUserInformation();
			CourseDto curCourse = courseService.findDtoById(courseId);

			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_EDIT_VIDEO_FROM_YOUR_COURSE_FAILURE,
						HttpStatus.BAD_GATEWAY);
			dto.setCourseId(courseId);

			if (videoService.updateDto(dto))
				return new ResponseEntity(ObjectConstants.API_LECTURER_EDIT_VIDEO_FROM_COURSE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.API_LECTURER_EDIT_VIDEO_FROM_COURSE_FAILURE, HttpStatus.OK);
		}
		return new ResponseEntity(ObjectConstants.API_LECTURER_EDIT_VIDEO_FROM_COURSE_FAILURE, HttpStatus.OK);

	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@DeleteMapping(UrlConstants.API_LECTURER_DELETE_VIDEO_BY_COURSE_ID)
	public Object deleteVideo(@PathVariable(UrlConstants.ID) int id) {
		try {
			// id = video id
			VideoDto dto = videoService.findDtoById(id);
			LoginDto account = userInformationService.getUserInformation();
			CourseDto curCourse = courseService.findDtoById(dto.getCourseId());
			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_DELETE_VIDEO_FROM_YOUR_COURSE_FAILURE,
						HttpStatus.BAD_GATEWAY);
			videoService.deleteById(id);
			return new ResponseEntity(ObjectConstants.API_LECTURER_DELETE_VIDEO_FROM_COURSE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.API_LECTURER_DELETE_VIDEO_FROM_COURSE_FAILURE, HttpStatus.OK);
		}
	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@GetMapping(UrlConstants.API_LECTURER_FIND_ALL_VIDEO_BY_COURSE_ID)
	public Object findAllVideoInCourse(@PathVariable(UrlConstants.COURSE_ID) int courseId) {
		try {
			LoginDto account = userInformationService.getUserInformation();
			CourseDto curCourse = courseService.findDtoById(courseId);
			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_FIND_ALL_VIDEO_FROM_YOUR_COURSE_FAILURE,
						HttpStatus.BAD_GATEWAY);

			return new ResponseEntity(videoService.findAllDtoByCourseId(courseId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.NOT_FOUND, HttpStatus.NOT_FOUND);

		}
	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@PutMapping(UrlConstants.API_LECTURER_EDIT_TARGET_BY_COURSE_ID)
	public Object editTarget(@RequestBody TargetDto dto, @PathVariable(UrlConstants.COURSE_ID) int courseId) {
		try {
			LoginDto account = userInformationService.getUserInformation();
			CourseDto curCourse = courseService.findDtoById(courseId);

			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_EDIT_TARGET_FROM_YOUR_COURSE_FAILURE,
						HttpStatus.BAD_GATEWAY);
			dto.setCourseId(courseId);

			if (targetService.updateDto(dto))
				return new ResponseEntity(ObjectConstants.API_LECTURER_EDIT_TARGET_FROM_COURSE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.API_LECTURER_EDIT_TARGET_FROM_COURSE_FAILURE, HttpStatus.OK);
		}
		return new ResponseEntity(ObjectConstants.API_LECTURER_EDIT_TARGET_FROM_COURSE_FAILURE, HttpStatus.OK);

	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@GetMapping(UrlConstants.API_LECTURER_FIND_ALL_TARGET_BY_COURSE_ID)
	public Object findAllTargetInCourse(@PathVariable(UrlConstants.COURSE_ID) int courseId) {
		try {
			LoginDto account = userInformationService.getUserInformation();
			CourseDto curCourse = courseService.findDtoById(courseId);
			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_FIND_ALL_TARGET_FROM_YOUR_COURSE_FAILURE,
						HttpStatus.BAD_GATEWAY);
			return new ResponseEntity(targetService.findAllDtoByCourseId(courseId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.NOT_FOUND, HttpStatus.NOT_FOUND);

		}
	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@PostMapping(UrlConstants.API_LECTURER_ADD_TARGET_BY_COURSE_ID)
	public Object addTarget(@RequestBody TargetDto dto, @PathVariable(UrlConstants.COURSE_ID) int courseId) {
		try {
			LoginDto account = userInformationService.getUserInformation();
			CourseDto curCourse = courseService.findDtoById(courseId);

			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_FIND_ALL_TARGET_FROM_YOUR_COURSE_FAILURE,
						HttpStatus.BAD_GATEWAY);
			dto.setCourseId(courseId);
			if (targetService.addDto(dto))
				return new ResponseEntity(ObjectConstants.API_LECTURER_ADD_TARGET_TO_COURSE_SUCCESS,
						HttpStatus.BAD_GATEWAY);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.API_LECTURER_ADD_TARGET_TO_COURSE_FAILURE,
					HttpStatus.BAD_GATEWAY);
		}
		return new ResponseEntity(ObjectConstants.API_LECTURER_ADD_TARGET_TO_COURSE_FAILURE, HttpStatus.BAD_GATEWAY);

	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@DeleteMapping(UrlConstants.API_LECTURER_DELETE_TARGET_BY_COURSE_ID)
	public Object deleteTarget(@PathVariable(UrlConstants.ID) int id) {
		try {
			// id = target id
			TargetDto dto = targetService.findDtoById(id);
			LoginDto account = userInformationService.getUserInformation();
			CourseDto curCourse = courseService.findDtoById(dto.getCourseId());
			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_DELETE_TARGET_FROM_YOUR_COURSE_FAILURE,
						HttpStatus.BAD_GATEWAY);
			targetService.deleteById(id);
			return new ResponseEntity(ObjectConstants.API_LECTURER_DELETE_TARGET_FROM_COURSE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.API_LECTURER_DELETE_TARGET_FROM_COURSE_FAILURE, HttpStatus.OK);
		}
	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@GetMapping(UrlConstants.API_LECTURER_FIND_ALL_MEMBER_BY_COURSE_ID)
	public Object viewAllMembers(@PathVariable(UrlConstants.COURSE_ID) int courseId) {
		try {
			LoginDto account = userInformationService.getUserInformation();
			CourseDto curCourse = courseService.findDtoById(courseId);
			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_FIND_ALL_MEMBER_FROM_YOUR_COURSE_FAILURE,
						HttpStatus.BAD_GATEWAY);
			return new ResponseEntity(enrollmentService.findAllMember(courseId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.NOT_FOUND, HttpStatus.NOT_FOUND);

		}
	}
	
	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@PostMapping(UrlConstants.API_LECTURER_ADD_MEMBER_TO_COURSE)
	public Object addMember(@RequestBody EnrollmentDto dto) {
		try {
			LoginDto account = userInformationService.getUserInformation();
			List<UserDto> usersInCourse = enrollmentService.findAllMember(dto.getCourseId());
			for (UserDto user : usersInCourse) {
				if (user.getId() == dto.getUserId())
					return new ResponseEntity(ObjectConstants.API_LECTURER_ADD_MEMBER_TO_COURSE_FAILURE, HttpStatus.BAD_GATEWAY);
			}

			CourseDto curCourse = courseService.findDtoById(dto.getCourseId());
			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_ADD_MEMBER_TO_YOUR_COURSE_FAILURE, HttpStatus.BAD_GATEWAY);
			if (enrollmentService.addMember(dto))
				return new ResponseEntity(ObjectConstants.API_LECTURER_ADD_MEMBER_TO_COURSE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ResponseEntity(ObjectConstants.API_LECTURER_ADD_MEMBER_TO_COURSE_FAILURE, HttpStatus.OK);

	}
	
	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@DeleteMapping(UrlConstants.API_LECTURER_DELETE_MEMBER_FROM_COURSE)
	public Object deleteMember(@RequestBody EnrollmentDto dto) {
		try {
			LoginDto account = userInformationService.getUserInformation();
			UserDto curUser = userService.findDtoById(dto.getUserId());
			CourseDto curCourse = courseService.findDtoById(dto.getCourseId());
			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_KICH_MEMBER_FROM_YOUR_COURSE_FAILURE, HttpStatus.BAD_GATEWAY);

			if (curUser.getRoleName().equals(ObjectConstants.TEACHER) || curUser.getRoleName().equals(ObjectConstants.ADMIN)) {
				return new ResponseEntity(ObjectConstants.API_LECTURER_KICH_MEMBER_FROM_COURSE_FAILURE_BY_PRIVILEGE,
						HttpStatus.BAD_GATEWAY);

			}
			if (enrollmentService.kikAMember(dto.getCourseId(), dto.getUserId())
					&& userService.cancleToMember(dto.getUserId(), dto.getCourseId()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_KICH_MEMBER_FROM_COURSE_SUCCESS, HttpStatus.OK);

		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.API_LECTURER_KICH_MEMBER_FROM_COURSE_FAILURE, HttpStatus.BAD_GATEWAY);
		}
		return new ResponseEntity(ObjectConstants.API_LECTURER_KICH_MEMBER_FROM_COURSE_FAILURE, HttpStatus.BAD_GATEWAY);
	}
	
	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@PutMapping(UrlConstants.API_LECTURER_AUTHORIZE_MEMBER_FROM_COURSE)
	public Object authorizeMember(@RequestBody EnrollmentDto dto) {
		try {
			LoginDto account = userInformationService.getUserInformation();
			UserDto curUser = userService.findDtoById(dto.getUserId());
			CourseDto curCourse = courseService.findDtoById(dto.getCourseId());
			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_AUTHORIZE_MEMBER_FROM_YOUR_COURSE_FAILURE, HttpStatus.BAD_GATEWAY);
			List<UserDto> usersInCourse = enrollmentService.findAllMember(dto.getCourseId());
			boolean isInUserInCourse = false;
			for (UserDto user : usersInCourse) {
				if (user.getId() == dto.getUserId())
					isInUserInCourse = true;
			}
			if (!isInUserInCourse) {
				return new ResponseEntity(ObjectConstants.API_LECTURER_MEMBER_NOT_IN_YOUR_COURSE, HttpStatus.BAD_GATEWAY);

			}
			if (curUser.getRoleName().equals(ObjectConstants.ADMIN) || curUser.getRoleName().equals(ObjectConstants.TEACHER))
				return new ResponseEntity(ObjectConstants.API_LECTURER_AUTHORIZE_MEMBER_FROM_COURSE_FAILURE_BY_PRIVILEGE,
						HttpStatus.BAD_GATEWAY);

			if (curUser.getRoleName().equals(ObjectConstants.TEACHING_ASSISTANT)) {
				return new ResponseEntity(ObjectConstants.API_LECTURER_AUTHORIZE_CO_HOST_FROM_COURSE_FAILURE_BY_PRIVILEGE,
						HttpStatus.BAD_GATEWAY);

			}
			if (userService.authorizeToCoHost(dto.getUserId(), dto.getCourseId()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_AUTHORIZE_MEMBER_FROM_COURSE_SUCCESS, HttpStatus.OK);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ResponseEntity(ObjectConstants.API_LECTURER_AUTHORIZE_MEMBER_FROM_COURSE_FAILURE, HttpStatus.OK);
	}
	
	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@PutMapping(UrlConstants.API_LECTURER_CANCEL_MEMBER_FROM_COURSE)
	public Object cancleCoHost(@RequestBody EnrollmentDto dto) {
		try {
			LoginDto account = userInformationService.getUserInformation();
			UserDto curUser = userService.findDtoById(dto.getUserId());
			CourseDto curCourse = courseService.findDtoById(dto.getCourseId());
			if (!curCourse.getAuthor().equals(account.getFullname()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_CANCEL_MEMBER_FROM_YOUR_COURSE_FAILURE, HttpStatus.BAD_GATEWAY);
			List<UserDto> usersInCourse = enrollmentService.findAllMember(dto.getCourseId());
			boolean isInUserInCourse = false;
			for (UserDto user : usersInCourse) {
				if (user.getId() == dto.getUserId())
					isInUserInCourse = true;
			}
			if (!isInUserInCourse) {
				return new ResponseEntity(ObjectConstants.API_LECTURER_MEMBER_NOT_IN_YOUR_COURSE, HttpStatus.BAD_GATEWAY);

			}
			if (curUser.getRoleName().equals(ObjectConstants.ADMIN) || curUser.getRoleName().equals(ObjectConstants.TEACHER))
				return new ResponseEntity(
						ObjectConstants.API_LECTURER_CANCEL_MEMBER_FROM_COURSE_FAILURE_BY_PRIVILEGE,
						HttpStatus.BAD_GATEWAY);

			if (curUser.getRoleName().equals(ObjectConstants.STUDENT)) {
				return new ResponseEntity(ObjectConstants.API_LECTURER_CANCEL_USER_FROM_COURSE_FAILURE_BY_PRIVILEGE,
						HttpStatus.BAD_GATEWAY);

			}
			if (userService.cancleToMember(dto.getUserId(), dto.getCourseId()))
				return new ResponseEntity(ObjectConstants.API_LECTURER_CANCEL_MEMBER_FROM_COURSE_SUCCESS, HttpStatus.OK);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ResponseEntity(ObjectConstants.API_LECTURER_CANCEL_MEMBER_FROM_COURSE_FAILURE, HttpStatus.OK);
	}
}
