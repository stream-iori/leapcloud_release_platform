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

  private DSLContext jooq;
  private HikariDataSource dataSource;

  private JsonObject mysqlConfig;

  @Inject
  public DataBaseConnection(JsonObject config) {
    mysqlConfig = config.getJsonObject("mysql");
  }

  public DSLContext getJooq() {
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

  public void close() {
    jooq.close();
    dataSource.close();
  }
}
