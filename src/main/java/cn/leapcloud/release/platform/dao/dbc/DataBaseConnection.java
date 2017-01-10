package cn.leapcloud.release.platform.dao.dbc;

import com.google.inject.Inject;
import com.zaxxer.hikari.HikariDataSource;
import io.vertx.core.json.JsonObject;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultTransactionProvider;

import java.sql.SQLException;

/**
 * Created by songqian on 16/9/24.
 */
public class DataBaseConnection {
  private static final String DBURL = "jdbc:mysql://localhost:" +
    "3306/leapcloud_release_platform?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
  private static final String DBUSER = "root";
  private static final String DBPASS = "root";

  private static DSLContext jooq;
  private static HikariDataSource dataSource;

  private static JsonObject mysqlConfig;

  @Inject
  public DataBaseConnection(JsonObject config) {
    mysqlConfig = config.getJsonObject("mysql", new JsonObject()
      .put("url", DBURL)
      .put("user", DBUSER)
      .put("password", DBPASS)
    );
  }

  public static DSLContext getJooq() {
    if (jooq == null) {
      try {
        //建立数据连接池
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(mysqlConfig.getString("url"));
        dataSource.setUsername(mysqlConfig.getString("user"));
        dataSource.setPassword(mysqlConfig.getString("password"));
        dataSource.setMaximumPoolSize(100);
        dataSource.setLoginTimeout(30);
        dataSource.setConnectionTimeout(10000);
        dataSource.setAutoCommit(false);

        ConnectionProvider connectionProvider = new DataSourceConnectionProvider(dataSource);
        //采用默认的事务管理
        TransactionProvider transactionProvider = new DefaultTransactionProvider(connectionProvider, false);
        Configuration jooqConf = new DefaultConfiguration();
        jooqConf.set(SQLDialect.MYSQL);
        jooqConf.set(connectionProvider);
        jooqConf.set(transactionProvider);
        jooq = DSL.using(jooqConf);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return jooq;
  }

  public static void close() {
    jooq.close();
    dataSource.close();
  }
}
