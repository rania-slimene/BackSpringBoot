package com.teamdevAcademy.academy;
import com.teamdevAcademy.academy.entities.UserRole;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;

import com.teamdevAcademy.academy.config.ApplicationConfig;
import com.teamdevAcademy.academy.entities.Categories;
import com.teamdevAcademy.academy.entities.User;
import com.teamdevAcademy.academy.repositories.CategoriesRepository;
import com.teamdevAcademy.academy.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;

import com.teamdevAcademy.academy.config.ApplicationConfig.*;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {ApplicationConfig.class, AcademyApplicationTests.class})
class AcademyApplicationTests {


	@Autowired
	private UserDetailsService userDetailsService;

	@Test


	public void testLoadUserByUsername() {
		String userEmail = "slimenerania1999@gmail.com";
		UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
		assertNotNull(userDetails);

	}

	@Autowired
	private DaoAuthenticationProvider authenticationProvider;

	@Test
	public void testAuthentication() {
		String userEmail = "slimenerania1999@gmail.com";
		String userPassword = "111111";
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, userPassword);
		Authentication authentication = authenticationProvider.authenticate(authenticationToken);
		assertTrue(authentication.isAuthenticated());
	}


}
