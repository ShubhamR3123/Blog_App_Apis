package com.shubhamit.blog_app_api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shubhamit.blog_app_api.entity.Role;
import com.shubhamit.blog_app_api.helper.AppConstants;
import com.shubhamit.blog_app_api.repository.RoleRepository;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {

		@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("123456"));
		
		try {
			Role role=new Role();
			role.setId(AppConstants.USER_ADMIN);
			role.setName("ADMIN_USER");

			Role role1=new Role();
			role1.setId(AppConstants.USER_NORMAL);
			role1.setName("NORMAL_USER");

			List<Role> roles = List.of(role,role1);
			List<Role> result = this.roleRepository.saveAll(roles);

			result.forEach(r->{
				System.out.println(r.getName());
			});
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	}

	
}
