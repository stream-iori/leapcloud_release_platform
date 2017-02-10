package cn.leapcloud.release.platform.dao;

import cn.leapcloud.release.platform.dao.entity.tables.records.UserRecord;
import org.jooq.Configuration;

import java.util.List;

/**
 * Created by stream.
 */
public interface UserDAO {

  boolean doCreate(UserRecord userRecord, Configuration configuration) throws Exception;

  boolean doUpdate(UserRecord userRecord, Configuration configuration) throws Exception;

  List<UserRecord> query() throws RuntimeException;

  UserRecord queryByName(String name);

}
