package com.profile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProfileApplicationTest {

	private ProfileApplication profileApplication;

	@BeforeEach
	void setUp() {
		profileApplication = new ProfileApplication();
	}

	@Test
	void testMainMethod() {
		try (MockedStatic<SpringApplication> mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
			ProfileApplication.main(new String[]{});
			mockedSpringApplication.verify(() -> SpringApplication.run(ProfileApplication.class, new String[]{}), times(1));
		}
	}

	@Test
	void testInit() {
		profileApplication.init();
		assertEquals(TimeZone.getTimeZone("America/Sao_Paulo"), TimeZone.getDefault());
	}
}