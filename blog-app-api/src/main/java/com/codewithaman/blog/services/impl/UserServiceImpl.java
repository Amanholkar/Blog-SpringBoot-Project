package com.codewithaman.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithaman.blog.config.AppConstants;
import com.codewithaman.blog.entities.Role;
import com.codewithaman.blog.entities.User;
import com.codewithaman.blog.exceptions.ResourceNotFoundException;
import com.codewithaman.blog.payloads.UserDto;
import com.codewithaman.blog.repositories.RoleRepo;
import com.codewithaman.blog.repositories.UserRepo;
import com.codewithaman.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired(required = true)
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto user) {
		
		
	   User curentUser = this.dtoToUser(user);

	   String password  = passwordEncoder.encode(curentUser.getPassword());

	   curentUser.setPassword(password);
	   System.out.println("password :- "+password);
	         
	   User savedUser = this.userRepo.save(curentUser);
		return this.userToDto(savedUser);
	}
	
	@Override
	public UserDto updateUser(UserDto user, Long userId) {

		User currentUser = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
		
		currentUser.setName(user.getName());
		currentUser.setEmail(user.getEmail());
		currentUser.setPassword(user.getPassword());
		currentUser.setAbout(user.getAbout());
	
		User updatedUser = this.userRepo.save(currentUser);
		
		return this.userToDto(updatedUser);
	
	
	}

	@Override
	public UserDto getUserById(Long userId) {
		
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {


		List<User> userlist = this.userRepo.findAll();
		
		List<UserDto> userDtolist =userlist.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtolist;
	}

	@Override
	public void deleteUser(Long userId) {
		
		
	    User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));

	    this.userRepo.delete(user);
	}
	
	
	
	private User dtoToUser(UserDto userDto) {
		
		User user1 = this.modelMapper.map(userDto, User.class);
		
//		User user1 = new User();
//		user1.setId(userDto.getId());
//	    user1.setName(userDto.getName());
//	    user1.setEmail(userDto.getEmail());
//	    user1.setPassword(userDto.getPassword());
//	    user1.setAbout(userDto.getAbout());
		return user1;
	}

	private UserDto userToDto(User user) {
		
		
		UserDto user1 = this.modelMapper.map(user, UserDto.class);
//		UserDto user1 = new UserDto();
//		user1.setId(user.getId());
//	    user1.setName(user.getName());
//	    user1.setEmail(user.getEmail());
//	    user1.setPassword(user.getPassword());
//	    user1.setAbout(user.getAbout());
		return user1;
	}

	@Override
	public UserDto registerNewUser(UserDto user) {
		
		User user2 = this.modelMapper.map(user, User.class);

		user2.setPassword(this.passwordEncoder.encode(user2.getPassword()));


	    Role role =	this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		user2.getRoles().add(role);

		User updateUser =  this.userRepo.save(user2);
		return this.modelMapper.map(updateUser, UserDto.class);
	}
	

}
