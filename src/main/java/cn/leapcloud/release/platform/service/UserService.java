package cn.leapcloud.release.platform.service;

import cn.leapcloud.release.platform.service.domain.User;

/**
 * Created by stream.
 */
public interface UserService {
  boolean createUser(User user) throws Exception;

  boolean modifyUser(User user) throws Exception;

  User findById(int id) throws Exception;

  /**
   * 登录
   * @param username 用户名
   * @param password 密码
   * @return 登录结果
   * @throws Exception
   */
  boolean login(String username, String password) throws RuntimeException;


  boolean isUserExist(String username) throws RuntimeException;

}
