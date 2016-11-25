package cn.leapcloud.release.platform.service;

import cn.leapcloud.release.platform.dao.UserDAO;
import cn.leapcloud.release.platform.dao.entity.tables.records.UserRecord;
import cn.leapcloud.release.platform.service.domain.User;
import org.jooq.DSLContext;

import javax.inject.Inject;

/**
 * Created by songqian on 16/11/25.
 */
public class UserServiceImpl implements UserService {
  private DSLContext jooq;
  private UserDAO userDAO;

  @Inject
  public UserServiceImpl(DSLContext jooq, UserDAO userDAO) {
    this.jooq = jooq;
    this.userDAO = userDAO;
  }

  public boolean createUser(User user) throws Exception {
    return jooq.transactionResult(configuration -> {
      boolean userCreateResult = userDAO.doCreate(user, configuration);
      if (userCreateResult) {
        return true;
      } else {
        throw new RuntimeException("create failed");
      }
    });
  }

  public boolean modifyUser(User user) throws Exception {
    return jooq.transactionResult(configuration -> {
      boolean userModifyResult = userDAO.doUpdate(user, configuration);
      if (userModifyResult) {
        return true;
      } else {
        throw new RuntimeException("update failed");
      }
    });
  }

  public User findById(int id) throws Exception {
    UserRecord userRecord = userDAO.queryById(id);
    return userConvert(userRecord);
  }

  private User userConvert(UserRecord userRecord) {
    User user = null;
    if (userRecord != null) {
      user = new User.Builder().id(userRecord.getId()).name(userRecord.getName()).email(userRecord.getMail()).build();
    }
    return user;
  }


}
