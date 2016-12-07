package cn.leapcloud.release.platform.dao.impl;

import cn.leapcloud.release.platform.dao.ReleaseTypeDAO;
import cn.leapcloud.release.platform.dao.entity.tables.records.ReleaseTypeRecord;
import com.google.inject.Inject;
import org.jooq.DSLContext;

import java.util.List;

import static cn.leapcloud.release.platform.dao.entity.tables.ReleaseType.RELEASE_TYPE;

/**
 * Created by songqian on 16/12/7.
 */
public class ReleaseTypeDAOImpl implements ReleaseTypeDAO {

  private DSLContext jooq;

  @Inject
  public ReleaseTypeDAOImpl(DSLContext jooq) {
    this.jooq = jooq;
  }

  @Override
  public List<ReleaseTypeRecord> query() throws Exception {
    return jooq.selectFrom(RELEASE_TYPE).fetch();

  }
}
