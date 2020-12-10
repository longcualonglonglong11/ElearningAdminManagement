package com.myclass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.myclass.dto.RegisterDto;
import com.myclass.entity.User;
import com.myclass.repository.UserRepository;
import com.myclass.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	UserRepository userRepository;

	@Override
	public int register(RegisterDto dto) {
		try {
			if (!dto.getConfirmPassword().equals(dto.getPassword()))
				return 0;
			if((userRepository.findByEmail(dto.getEmail()) != null))
				return 1;
			User user = new User();
			user.setEmail(dto.getEmail());
			String hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10));
			
			user.setPassword(hashed);
			user.setFullname("New User");
			user.setRoleId(2);
			user.setAvatar("https://vnn-imgs-a1.vgcloud.vn/image1.ictnews.vn/_Files/2020/03/17/trend-avatar-1.jpg");
			user.setBalance(100);
			userRepository.save(user);
			return 2;
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
	}

}
