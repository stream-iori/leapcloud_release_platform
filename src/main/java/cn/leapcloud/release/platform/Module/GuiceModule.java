package cn.leapcloud.release.platform.Module;

import cn.leapcloud.release.platform.controller.*;
import cn.leapcloud.release.platform.dao.ReleaseTaskDAO;
import cn.leapcloud.release.platform.dao.ReleaseTypeDAO;
import cn.leapcloud.release.platform.dao.SQLConditionCombine;
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
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import org.jooq.DSLContext;

/**
 * Created by songqian.
 */
public class GuiceModule implements Module {

  private Vertx vertx;
  private JsonObject config;

  public GuiceModule(Vertx vertx, JsonObject config) {
    this.vertx = vertx;
    this.config = config;
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

    binder.bind(ConditionParser.class);

    binder.bind(SQLConditionCombine.class);


  }

  @Provides
  @Singleton
  public DSLContext jooq() {
    return new DataBaseConnection(config).getJooq();
  }

  @Provides
  public Vertx vertx() {
    return this.vertx;
  }

  @Provides
  public JsonObject config() {
    return this.config;
  }

  @Provides
  @Singleton
  public Router router() {
    Router router = Router.router(vertx);
    router.route().handler(CookieHandler.create());
    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
    return router;
  }


}
