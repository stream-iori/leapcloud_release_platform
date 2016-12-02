package cn.leapcloud.release.platform.service.impl;

import cn.leapcloud.release.platform.dao.ReleaseTaskDAO;
import cn.leapcloud.release.platform.dao.entity.tables.records.ReleaseTaskRecord;
import cn.leapcloud.release.platform.service.ReleaseTaskService;
import com.google.inject.Inject;
import org.jooq.DSLContext;

import static cn.leapcloud.release.platform.dao.entity.tables.ReleaseTask.RELEASE_TASK;

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
      releaseTaskRecord.setReleaseType(releaseType);
      releaseTaskRecord.setProposal(proposal);
      releaseTaskRecord.setTitle(title);
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
}

