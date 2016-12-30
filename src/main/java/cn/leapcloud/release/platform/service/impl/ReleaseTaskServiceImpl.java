package cn.leapcloud.release.platform.service.impl;

import cn.leapcloud.release.platform.dao.ReleaseTaskDAO;
import cn.leapcloud.release.platform.dao.entity.tables.records.ReleaseTaskRecord;
import cn.leapcloud.release.platform.dao.impl.TaskRecordWithCount;
import cn.leapcloud.release.platform.service.ReleaseTaskService;
import cn.leapcloud.release.platform.service.domain.ReleaseTask;
import com.google.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.Field;

import java.sql.Timestamp;
import java.util.List;

import static cn.leapcloud.release.platform.dao.entity.tables.ReleaseTask.RELEASE_TASK;
import static org.jooq.impl.DSL.count;

/**
 * Created by songqian on 16/12/2.
 */
public class ReleaseTaskServiceImpl implements ReleaseTaskService {

  private DSLContext jooq;
  private ReleaseTaskDAO releaseTaskDAO;

  @Inject
  public ReleaseTaskServiceImpl(DSLContext jooq, ReleaseTaskDAO releaseTaskDAO) {
    this.jooq = jooq;
    this.releaseTaskDAO = releaseTaskDAO;
  }

  public boolean createNewTask(int releaseType, String proposal, String title, String projectURL, String projectDescription) throws Exception {
    return jooq.transactionResult(configuration -> {
      ReleaseTaskRecord releaseTaskRecord = jooq.newRecord(RELEASE_TASK);
      //当前时间
      Timestamp now = new Timestamp(System.currentTimeMillis());

      releaseTaskRecord.setReleaseType(releaseType);
      releaseTaskRecord.setProposal(proposal);
      releaseTaskRecord.setTitle(title);
      releaseTaskRecord.setStatus((byte) 0);
      releaseTaskRecord.setProposalTime(now);
      releaseTaskRecord.setUpdateTime(now);
      releaseTaskRecord.setProjectLocation(projectURL);
      releaseTaskRecord.setProjectDesc(projectDescription);
      boolean resultCreateNewTask = releaseTaskDAO.doCreate(releaseTaskRecord, configuration);
      if (resultCreateNewTask) {
        return true;
      } else {
        throw new RuntimeException("create failed");
      }
    });
  }

  public boolean updateNewTask(int id, int releaseType, String proposal, String title, String projectURL, String projectDescription) throws Exception {
    return jooq.transactionResult(configuration -> {
      ReleaseTaskRecord releaseTaskRecord = jooq.newRecord(RELEASE_TASK);
      releaseTaskRecord = releaseTaskDAO.queryById(id);
      releaseTaskRecord.setReleaseType(releaseType);
      releaseTaskRecord.setProposal(proposal);
      releaseTaskRecord.setTitle(title);
      releaseTaskRecord.setProjectLocation(projectURL);
      releaseTaskRecord.setProjectDesc(projectDescription);
      boolean resultUpdateNewTask = releaseTaskDAO.doUpdate(releaseTaskRecord, configuration);
      if (resultUpdateNewTask) {
        return true;
      } else {
        throw new RuntimeException("update failed");
      }
    });
  }

  @Override
  public boolean manageNewTask(int id, byte status, String releaseRemark) throws Exception {
    return jooq.transactionResult(configuration -> {
      ReleaseTaskRecord releaseTaskRecord = jooq.newRecord(RELEASE_TASK);
      releaseTaskRecord = releaseTaskDAO.queryById(id);
      releaseTaskRecord.setStatus(status);
      releaseTaskRecord.setReleaseRemark(releaseRemark);
      boolean resultMangeNewTask = releaseTaskDAO.doUpdate(releaseTaskRecord, configuration);
      if (resultMangeNewTask) {
        return true;
      } else {
        throw new RuntimeException("manage failed");
      }
    });
  }






  private ReleaseTask convertEntityToDomain(ReleaseTask.Builder builder, ReleaseTaskRecord releaseTaskRecord) {
    builder.proposal(releaseTaskRecord.getProposal());
    builder.title(releaseTaskRecord.getTitle());
    builder.id(releaseTaskRecord.getId());
    builder.projectDesc(releaseTaskRecord.getProposal());
    builder.projectLocation(releaseTaskRecord.getProjectLocation());
    builder.releaseRemark(releaseTaskRecord.getReleaseRemark());
    builder.proposalTime(releaseTaskRecord.getProposalTime());
    builder.releaseType(releaseTaskRecord.getReleaseType());
    builder.updateTime(releaseTaskRecord.getUpdateTime());
    builder.releaseRemark(releaseTaskRecord.getReleaseRemark());

    return builder.build();
  }

}


