package cn.leapcloud.release.platform.Module;

import cn.leapcloud.release.platform.controller.ReleaseTaskController;
import cn.leapcloud.release.platform.controller.ReleaseTypeController;
import cn.leapcloud.release.platform.controller.RestfulServer;
import cn.leapcloud.release.platform.controller.UserController;
import cn.leapcloud.release.platform.dao.ReleaseTaskDAO;
import cn.leapcloud.release.platform.dao.ReleaseTypeDAO;
import cn.leapcloud.release.platform.dao.UserDAO;
import cn.leapcloud.release.platform.dao.dbc.DataBaseConnection;
import cn.leapcloud.release.platform.dao.impl.ReleaseTaskDAOImpl;
import cn.leapcloud.release.platform.dao.impl.ReleaseTypeDAOImpl;
import cn.leapcloud.release.platform.dao.impl.UserDAOImpl;
import cn.leapcloud.release.platform.service.ReleaseTaskService;
import cn.leapcloud.release.platform.service.ReleaseTypeService;
import cn.leapcloud.release.platform.service.UserService;
import cn.leapcloud.release.platform.service.impl.ReleaseTaskServiceImpl;
import cn.leapcloud.release.platform.service.impl.ReleaseTypeServiceImpl;
import cn.leapcloud.release.platform.service.impl.UserServiceImpl;
import com.google.inject.*;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.jooq.DSLContext;

/**
 * Created by songqian.
 */
public class GuiceModule implements Module {

  private Vertx vertx;

  public GuiceModule(Vertx vertx) {
    this.vertx = vertx;
  }

  public void configure(Binder binder) {
    //
    binder.bind(UserDAO.class).to(UserDAOImpl.class).in(Scopes.SINGLETON);
    binder.bind(UserService.class).to(UserServiceImpl.class).in(Scopes.SINGLETON);
    binder.bind(UserController.class);
    //
    binder.bind(ReleaseTaskService.class).to(ReleaseTaskServiceImpl.class).in(Scopes.SINGLETON);
    binder.bind(ReleaseTaskDAO.class).to(ReleaseTaskDAOImpl.class).in(Scopes.SINGLETON);
    binder.bind(ReleaseTaskController.class);
    //
    binder.bind(ReleaseTypeDAO.class).to(ReleaseTypeDAOImpl.class).in(Scopes.SINGLETON);
    binder.bind(ReleaseTypeService.class).to(ReleaseTypeServiceImpl.class).in(Scopes.SINGLETON);
    binder.bind(ReleaseTypeController.class);
    //
    binder.bind(RestfulServer.class);
  }

  @Provides
  public DSLContext jooq() {
    return DataBaseConnection.getJooq();
  }

  @Provides
  public Vertx vertx() {
    return this.vertx;
  }

  @Provides
  @Singleton
  public Router router() {
    return Router.router(vertx);
  }



}
