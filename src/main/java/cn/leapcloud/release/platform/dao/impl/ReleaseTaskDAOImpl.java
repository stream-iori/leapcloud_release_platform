package cn.leapcloud.release.platform.dao.impl;

import cn.leapcloud.release.platform.dao.ReleaseTaskDAO;
import cn.leapcloud.release.platform.dao.entity.tables.records.ReleaseTaskRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import java.util.List;

import static cn.leapcloud.release.platform.dao.entity.tables.ReleaseTask.RELEASE_TASK;
import static org.jooq.impl.DSL.count;

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
  public TaskRecordWithCount query(int pageSize, int currentPaged) throws Exception {
    //获得总条目数
    Field<Integer> cf = count();
    Integer count = jooq.select(cf).from(RELEASE_TASK).fetchOne(cf);
    //获得总页数
    int page = (int) Math.ceil((double) count / pageSize);
    int offset = (currentPaged - 1) * pageSize;
    List<ReleaseTaskRecord> records = jooq.selectFrom(RELEASE_TASK).orderBy(RELEASE_TASK.PROPOSAL_TIME.desc()).limit(offset, pageSize).fetch();
    return new TaskRecordWithCount(count, records);
  }

  @Override
  public TaskRecordWithCount query(int pageSize, int currentPaged, int releaseType) throws Exception {

    Field<Integer> cf = count();
    Integer count = jooq.select(cf).from(RELEASE_TASK).where(RELEASE_TASK.RELEASE_TYPE.eq(releaseType)).fetchOne(cf);

    int offset = (currentPaged - 1) * pageSize;
    List<ReleaseTaskRecord> records = jooq.selectFrom(RELEASE_TASK).where(RELEASE_TASK.RELEASE_TYPE.eq(releaseType))
      .orderBy(RELEASE_TASK.PROPOSAL_TIME.desc()).limit(offset, pageSize).fetch();
    return new TaskRecordWithCount(count, records);
  }

  @Override
  public TaskRecordWithCount query(int pageSize, int currentPaged, byte releaseStatus) throws Exception {

    Field<Integer> cf = count();
    Integer count = jooq.select(cf).from(RELEASE_TASK).where(RELEASE_TASK.STATUS.eq(releaseStatus)).fetchOne(cf);

    int offset = (currentPaged - 1) * pageSize;
    List<ReleaseTaskRecord> records = jooq.selectFrom(RELEASE_TASK).where(RELEASE_TASK.STATUS.eq(releaseStatus))
      .orderBy(RELEASE_TASK.PROPOSAL_TIME.desc()).limit(offset, pageSize).fetch();
    return new TaskRecordWithCount(count, records);
  }

  @Override
  public TaskRecordWithCount query(int pageSize, int currentPaged, int releaseType, byte releaseStatus) throws Exception {
    Field<Integer> cf = count();
    Integer count = jooq.select(cf).from(RELEASE_TASK).where(RELEASE_TASK.STATUS.eq(releaseStatus),RELEASE_TASK.RELEASE_TYPE.eq(releaseType)).fetchOne(cf);

    int offset = (currentPaged - 1) * pageSize;
    List<ReleaseTaskRecord> records = jooq.selectFrom(RELEASE_TASK).where(RELEASE_TASK.STATUS.eq(releaseStatus),RELEASE_TASK.RELEASE_TYPE.eq(releaseType))
      .orderBy(RELEASE_TASK.PROPOSAL_TIME.desc()).limit(offset, pageSize).fetch();
    return new TaskRecordWithCount(count, records);
  }
}
