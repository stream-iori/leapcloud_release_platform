package cn.leapcloud.release.platform.dao.impl;

import cn.leapcloud.release.platform.dao.ReleaseTaskDAO;
import cn.leapcloud.release.platform.dao.entity.tables.records.ReleaseTaskRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import static cn.leapcloud.release.platform.dao.entity.tables.ReleaseTask.RELEASE_TASK;

/**
 * Created by songqian on 16/11/25.
 */
public class ReleaseTaskDAOImpl implements ReleaseTaskDAO {


  private DSLContext jooq;

  public ReleaseTaskDAOImpl(DSLContext jooq) {
    this.jooq = jooq;
  }

  public boolean doCreate(ReleaseTaskRecord releaseTaskRecord, Configuration configuration) throws Exception {

    int effectRow = DSL.using(configuration)
      .insertInto(RELEASE_TASK)
      .set(RELEASE_TASK.TITLE, releaseTaskRecord.getTitle())
      .set(RELEASE_TASK.PROJECT_DESC, releaseTaskRecord.getProjectDesc())
      .set(RELEASE_TASK.PROJECT_LOCATION, releaseTaskRecord.getProjectLocation())
      .set(RELEASE_TASK.PROPOSAL, releaseTaskRecord.getProposal())
      .set(RELEASE_TASK.PROPOSAL_TIME, releaseTaskRecord.getProposalTime())
      .set(RELEASE_TASK.UPDATE_TIME, releaseTaskRecord.getUpdateTime())
      .set(RELEASE_TASK.RELEASE_REMARK, releaseTaskRecord.getReleaseRemark())
      .set(RELEASE_TASK.RELEASE_TYPE, releaseTaskRecord.getReleaseType())
      .set(RELEASE_TASK.WDONESTATUS, releaseTaskRecord.getWdonestatus())
      .execute();
    return effectRow > 0;
  }

  public boolean doUpdate(ReleaseTaskRecord releaseTaskRecord, Configuration configuration) throws Exception {

    int effectRow = jooq.update(RELEASE_TASK)
      .set(RELEASE_TASK.TITLE, releaseTaskRecord.getTitle())
      .set(RELEASE_TASK.PROJECT_DESC, releaseTaskRecord.getProjectDesc())
      .set(RELEASE_TASK.PROJECT_LOCATION, releaseTaskRecord.getProjectLocation())
      .set(RELEASE_TASK.PROPOSAL, releaseTaskRecord.getProposal())
      .set(RELEASE_TASK.PROPOSAL_TIME, releaseTaskRecord.getProposalTime())
      .set(RELEASE_TASK.UPDATE_TIME, releaseTaskRecord.getUpdateTime())
      .set(RELEASE_TASK.RELEASE_REMARK, releaseTaskRecord.getReleaseRemark())
      .set(RELEASE_TASK.RELEASE_TYPE, releaseTaskRecord.getReleaseType())
      .set(RELEASE_TASK.WDONESTATUS, releaseTaskRecord.getWdonestatus())
      .where(RELEASE_TASK.ID.equal(releaseTaskRecord.getId()))
      .execute();
    return effectRow > 0;
  }

  public ReleaseTaskRecord queryById(int id) throws Exception {
    return jooq.selectFrom(RELEASE_TASK)
      .where(RELEASE_TASK.ID.equal(id))
      .fetchOne();
  }


}
