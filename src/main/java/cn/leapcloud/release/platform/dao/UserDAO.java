package cn.leapcloud.release.platform.dao;

import cn.leapcloud.release.platform.dao.entity.tables.records.UserRecord;
import org.jooq.Configuration;

/**
 * Created by stream.
 */
public interface UserDAO {

  boolean doCreate(UserRecord userRecord, Configuration configuration) throws Exception;

  boolean doUpdate(UserRecord userRecord) throws Exception;

  UserRecord queryById(int id) throws Exception;

  UserRecord queryByName(String name) throws Exception;


}
