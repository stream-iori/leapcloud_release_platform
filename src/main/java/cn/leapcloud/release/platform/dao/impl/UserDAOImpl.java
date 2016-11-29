package cn.leapcloud.release.platform.dao.impl;

import cn.leapcloud.release.platform.dao.UserDAO;
import cn.leapcloud.release.platform.dao.entity.tables.records.UserRecord;
import cn.leapcloud.release.platform.service.domain.User;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import static cn.leapcloud.release.platform.dao.entity.tables.User.USER;


/**
 * Created by songqian on 16/11/24.
 */
public class UserDAOImpl implements UserDAO {

  private DSLContext jooq;

  public UserDAOImpl(DSLContext jooq) {
    this.jooq = jooq;
  }

  public boolean doCreate(User userRecord, Configuration configuration) throws Exception {
    int effectRow = DSL.using(configuration).insertInto(USER)
      .set(USER.NAME, userRecord.getName()).set(USER.MAIL, userRecord.getEmail())
      .execute();
    return effectRow > 0;
  }

  public boolean doUpdate(User userRecord, Configuration configuration) throws Exception {
    int effectRow = jooq.update(USER).set(USER.NAME, userRecord.getName()).set(USER.MAIL, userRecord.getEmail())
      .where(USER.ID.equal(userRecord.getId())).execute();
    return effectRow > 0;
  }

  public UserRecord queryById(int id) throws Exception {
    return jooq.selectFrom(USER).where(USER.ID.equal(id)).fetchOne();
  }

  public UserRecord queryByName(String name) throws Exception {
    return jooq.selectFrom(USER).where(USER.NAME.equal(name)).fetchOne();
  }
}
