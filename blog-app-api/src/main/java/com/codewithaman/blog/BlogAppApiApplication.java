package com.codewithaman.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codewithaman.blog.config.AppConstants;
import com.codewithaman.blog.entities.Role;
import com.codewithaman.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {

    @Autowired(required = true)
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
	
		System.out.println(this.passwordEncoder.encode("123456"));
		


		try {
			Role roleAdmin = new Role();
			roleAdmin.setRoleId(AppConstants.ADMIN_USER);
			roleAdmin.setName(AppConstants.ROLE_ADMIN);

			Role roleNormal = new Role();
			roleNormal.setRoleId(AppConstants.NORMAL_USER);
			roleNormal.setName(AppConstants.ROLE_NORMAL);


			List<Role> roles = List.of(roleAdmin,roleNormal);

			List<Role> saveRoles = this.roleRepo.saveAll(roles);


			saveRoles.forEach(role ->{

				System.out.println(role);
			}
			);

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
