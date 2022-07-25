package com.codewithaman.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponse {
	
	
	private Object data;
	
	private String message;
	
	private String status ;

}
