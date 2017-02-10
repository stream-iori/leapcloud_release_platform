package cn.leapcloud.release.platform;

import cn.leapcloud.release.platform.service.UserService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by songqian.
 */
public class UserServiceTest extends Base {

  @BeforeClass
  public static void before() throws InterruptedException {
    startServer();
  }

  @AfterClass
  public static void after() {
    stopServer();
  }

  @Test
  public void findAll() {
    UserService userService = starter.getInjector().getInstance(UserService.class);
    System.out.println(userService.findAll().size());
  }

}
