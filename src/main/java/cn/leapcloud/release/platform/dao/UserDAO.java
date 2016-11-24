package cn.leapcloud.release.platform.dao;

import cn.leapcloud.release.platform.service.domain.User;
import org.jooq.Configuration;

/**
 * Created by stream.
 */
public interface UserDAO {

  boolean doCreate(User user, Configuration configuration) throws Exception;

  boolean doDelete(int id) throws Exception;

  boolean doUpdate(User user) throws Exception;

  User findById(int id) throws Exception;


}
