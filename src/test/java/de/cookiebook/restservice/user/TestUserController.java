import de.cookiebook.restservice.user.User;
import de.cookiebook.restservice.user.UserController;
import de.cookiebook.restservice.user.UserRepository;
import de.cookiebook.restservice.user.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestUserController {

    @Mock
    private UserController userController;
    @Mock
    private UserRepository userRepository;

    private User user = User.().username("test-user").mail("tester@test.com").build();

    @Before
    public void setUp() {
        Mockito.when().thenReturn(user);
    }

    @Test
    public void testGetUser() {
        Assert.assertEquals(UserController(getUser));
    }

}
