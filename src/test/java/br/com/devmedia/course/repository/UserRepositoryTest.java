package br.com.devmedia.course.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.devmedia.course.CourseSpringDataApplication;
import br.com.devmedia.course.entity.User;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CourseSpringDataApplication.class)
public class UserRepositoryTest {
    private User user;
    
    @Autowired
    private UserRepository userRepository;
    
    @Before
    public void setUp() {
        this.user = new User("admin", "admin");
    }

    @Test
    public void shouldPersistUser() {
        Assert.assertTrue(this.user.isNew());
        this.userRepository.save(this.user);
        Assert.assertFalse(this.user.isNew());
        Assert.assertNotNull(this.user.getId());
    }
}