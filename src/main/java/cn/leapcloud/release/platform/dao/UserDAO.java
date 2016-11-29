package cn.leapcloud.release.platform.dao;

import cn.leapcloud.release.platform.dao.entity.tables.records.UserRecord;
import cn.leapcloud.release.platform.service.domain.User;
import org.jooq.Configuration;

/**
 * Created by stream.
 */
public interface UserDAO {

  boolean doCreate(User userRecord, Configuration configuration) throws Exception;

  boolean doUpdate(User userRecord, Configuration configuration) throws Exception;

  UserRecord queryById(int id) throws Exception;

  UserRecord queryByName(String name) throws Exception;


}
