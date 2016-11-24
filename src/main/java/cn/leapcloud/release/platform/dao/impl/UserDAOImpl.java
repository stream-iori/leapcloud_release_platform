package cn.leapcloud.release.platform.dao.impl;

import cn.leapcloud.release.platform.dao.UserDAO;
import cn.leapcloud.release.platform.dao.entity.tables.records.UserRecord;
import cn.leapcloud.release.platform.service.domain.User;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
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


  private RecordMapper<UserRecord, User> userRecordMapper = userRecord -> {
    User user = null;
    if (userRecord != null) {
      user = new User.Builder().id(userRecord.getId()).name(userRecord.getName()).email(userRecord.getMail()).build();
    }
    return user;
  };


  public boolean doCreate(User user, Configuration configuration) throws Exception {
    int effectRow = DSL.using(configuration).insertInto(USER)
      .set(USER.ID, user.getId()).set(USER.NAME, user.getName()).set(USER.MAIL, user.getEmail())
      .execute();
    return effectRow > 0;
  }

  public boolean doDelete(int id) throws Exception {
    int effectRow = jooq.delete(USER).where(USER.ID.equal(id)).execute();
    return effectRow > 0;
  }

  public boolean doUpdate(User user) throws Exception {
    int effectRow = jooq.update(USER).set(USER.NAME, user.getName()).set(USER.MAIL, user.getEmail())
      .where(USER.ID.equal(user.getId())).execute();
    return effectRow > 0;
  }

  public User findById(int id) throws Exception {
    return jooq.selectFrom(USER).where(USER.ID.equal(id)).fetchOne(userRecordMapper);

  }
}
