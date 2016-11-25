package cn.leapcloud.release.platform.dao;

import cn.leapcloud.release.platform.service.domain.User;
import org.jooq.Configuration;

/**
 * Created by stream.
 */
public interface UserDAO {

  boolean doCreate(User user, Configuration configuration) throws Exception;


  boolean doUpdate(User user) throws Exception;

  User queryById(int id) throws Exception;

  User queryByName(String name) throws Exception;


}
