package cn.leapcloud.release.platform.dao.impl;

import cn.leapcloud.release.platform.dao.ReleaseTaskDAO;
import cn.leapcloud.release.platform.dao.entity.tables.records.ReleaseTaskRecord;
import com.google.inject.Inject;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.List;

import static cn.leapcloud.release.platform.dao.entity.tables.ReleaseTask.RELEASE_TASK;

/**
 * Created by songqian on 16/11/25.
 */
public class ReleaseTaskDAOImpl implements ReleaseTaskDAO {


  private DSLContext jooq;

  @Inject
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
      .set(RELEASE_TASK.STATUS, releaseTaskRecord.getStatus())
      .execute();
    return effectRow > 0;
  }

  @Override
  public boolean doUpdate(ReleaseTaskRecord releaseTaskRecord, Configuration configuration) throws Exception {

    int effectRow = DSL.using(configuration)
      .update(RELEASE_TASK)
      .set(RELEASE_TASK.TITLE, releaseTaskRecord.getTitle())
      .set(RELEASE_TASK.PROJECT_DESC, releaseTaskRecord.getProjectDesc())
      .set(RELEASE_TASK.PROJECT_LOCATION, releaseTaskRecord.getProjectLocation())
      .set(RELEASE_TASK.PROPOSAL, releaseTaskRecord.getProposal())
      .set(RELEASE_TASK.PROPOSAL_TIME, releaseTaskRecord.getProposalTime())
      .set(RELEASE_TASK.UPDATE_TIME, releaseTaskRecord.getUpdateTime())
      .set(RELEASE_TASK.RELEASE_REMARK, releaseTaskRecord.getReleaseRemark())
      .set(RELEASE_TASK.RELEASE_TYPE, releaseTaskRecord.getReleaseType())
      .set(RELEASE_TASK.STATUS, releaseTaskRecord.getStatus())
      .where(RELEASE_TASK.ID.equal(releaseTaskRecord.getId()))
      .execute();
    return effectRow > 0;
  }

  @Override
  public ReleaseTaskRecord queryById(int id) throws Exception {
    return jooq.selectFrom(RELEASE_TASK)
      .where(RELEASE_TASK.ID.equal(id))
      .fetchOne();
  }

  @Override
  public List<ReleaseTaskRecord> query() throws Exception {
    return jooq.selectFrom(RELEASE_TASK).fetch();
  }
}
