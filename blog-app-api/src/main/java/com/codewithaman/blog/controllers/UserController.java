package com.codewithaman.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithaman.blog.payloads.ApiResponse;
import com.codewithaman.blog.payloads.UserDto;
import com.codewithaman.blog.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	// Post - Create User ResponseEntity<UserDto> createUser

	@PostMapping("/users")
	public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDto userDto) {

		UserDto createUserDto = this.userService.createUser(userDto);

//		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);

		return new ResponseEntity<ApiResponse>(new ApiResponse(createUserDto, "User Created Completely", "1"),
				HttpStatus.CREATED);
	}

	// PUT - update user ResponseEntity<UserDto> updateUser

	@PutMapping("/users/{userId}")
	public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId) {

		
			UserDto updatedUser = this.userService.updateUser(userDto, userId);

			
		
				return new ResponseEntity<ApiResponse>(
						new ApiResponse(updatedUser, "User updated completely Id= " + userId, "1"), HttpStatus.OK);
		
	}

	// DELETE - delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {

		this.userService.deleteUser(userId);

		return new ResponseEntity<ApiResponse>(new ApiResponse(null, "User deleted Successfully", "1"), HttpStatus.OK);
	}

	// GET - user get ResponseEntity<List<UserDto>>

	@GetMapping("/users")
	public ResponseEntity<ApiResponse> getAllUser() {

		List<UserDto> userDtolist = this.userService.getAllUsers();

//		return ResponseEntity.ok(userDtolist);

		return new ResponseEntity<ApiResponse>(new ApiResponse(userDtolist, "All User List", "1"), HttpStatus.OK);
	}

	// GET - user get ResponseEntity<UserDto> getUserId

	@GetMapping("/users/{userId}")
	public ResponseEntity<ApiResponse> getUserId(@PathVariable Long userId) {

		UserDto userDto = this.userService.getUserById(userId);

		return new ResponseEntity<ApiResponse>(new ApiResponse(userDto, "Single User for id= " + userId, "1"),
				HttpStatus.OK);
	}

}
