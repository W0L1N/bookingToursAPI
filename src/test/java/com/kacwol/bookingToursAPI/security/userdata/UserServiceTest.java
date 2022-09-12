package com.kacwol.bookingToursAPI.security.userdata;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Mock
    private UserDataRepo repo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    @Test
    @WithMockUser(username = "user", authorities = {"USER","ADMIN"})
    public void containsAuthority_shouldWork() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String authority1 = "USER";
        String authority2 = "ADMIN";

        String authority3 = "I DON'T EXIST";

        Assert.assertTrue(userService.containsAuthority(auth, authority1));
        Assert.assertTrue(userService.containsAuthority(auth, authority2));

        Assert.assertFalse(userService.containsAuthority(auth, authority3));

    }
}
