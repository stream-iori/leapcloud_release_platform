package cn.leapcloud.release.platform.service.impl;

import cn.leapcloud.release.platform.dao.ReleaseTypeDAO;
import cn.leapcloud.release.platform.dao.entity.tables.records.ReleaseTypeRecord;
import cn.leapcloud.release.platform.service.ReleaseTypeService;
import cn.leapcloud.release.platform.service.domain.ReleaseType;
import com.google.inject.Inject;
import org.jooq.DSLContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songqian on 16/12/7.
 */
public class ReleaseTypeServiceImpl implements ReleaseTypeService {
  private DSLContext jooq;
  private ReleaseTypeDAO releaseTypeDAO;

  @Inject
  public ReleaseTypeServiceImpl(DSLContext jooq, ReleaseTypeDAO releaseTypeDAO) {
    this.jooq = jooq;
    this.releaseTypeDAO = releaseTypeDAO;
  }

  @Override
  public List<ReleaseType> queryAll() throws Exception {
    List<ReleaseType> releaseTypes = new ArrayList<>();
    List<ReleaseTypeRecord> releaseTypeRecords = releaseTypeDAO.query();
    ReleaseType.Builder builder = new ReleaseType.Builder();
    for (ReleaseTypeRecord releaseTypeRecord : releaseTypeRecords) {
      builder.id(releaseTypeRecord.getId());
      builder.name(releaseTypeRecord.getName());
      ReleaseType releaseType = builder.build();
      releaseTypes.add(releaseType);
    }
    return releaseTypes;
  }
}
