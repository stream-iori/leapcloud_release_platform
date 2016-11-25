package cn.leapcloud.release.platform.service;

import cn.leapcloud.release.platform.service.domain.User;

/**
 * Created by stream.
 */
public interface UserService {
  boolean createUser(User user) throws Exception;

  boolean modifyUser(User user) throws Exception;

  User findById(int id) throws Exception;

}
