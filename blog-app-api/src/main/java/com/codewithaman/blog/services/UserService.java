package com.codewithaman.blog.services;

import java.util.List;




import com.codewithaman.blog.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto user);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user,Long userId);
	
	UserDto getUserById(Long userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Long userId);

	
}
