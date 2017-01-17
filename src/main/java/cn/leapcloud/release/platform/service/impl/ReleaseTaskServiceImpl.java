package cn.leapcloud.release.platform.service.impl;

import cn.leapcloud.release.platform.controller.ConditionParser;
import cn.leapcloud.release.platform.dao.ReleaseTaskDAO;
import cn.leapcloud.release.platform.dao.entity.tables.records.ReleaseTaskRecord;
import cn.leapcloud.release.platform.dao.impl.TaskWithCount;
import cn.leapcloud.release.platform.service.ReleaseTaskService;
import cn.leapcloud.release.platform.service.domain.ReleaseTask;
import cn.leapcloud.release.platform.service.domain.Status;
import com.google.inject.Inject;
import io.vertx.core.json.JsonObject;
import org.jooq.DSLContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static cn.leapcloud.release.platform.dao.entity.tables.ReleaseTask.RELEASE_TASK;

/**
 * Created by songqian on 16/12/2.
 */
public class ReleaseTaskServiceImpl implements ReleaseTaskService {

  private DSLContext jooq;
  private ReleaseTaskDAO releaseTaskDAO;
  private ConditionParser conditionParser;

  @Inject
  public ReleaseTaskServiceImpl(DSLContext jooq, ReleaseTaskDAO releaseTaskDAO,ConditionParser conditionParser) {
    this.jooq = jooq;
    this.releaseTaskDAO = releaseTaskDAO;
    this.conditionParser=conditionParser;
  }

  public boolean createNewTask(int releaseType, String proposal, String title, String projectURL, String projectDescription,String tag) throws Exception {
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
      releaseTaskRecord.setTag(tag);
      boolean resultCreateNewTask = releaseTaskDAO.doCreate(releaseTaskRecord, configuration);
      if (resultCreateNewTask) {
        return true;
      } else {
        throw new RuntimeException("create failed");
      }
    });
  }

  public boolean updateNewTask(int id, int releaseType, String proposal, String title, String projectURL, String projectDescription,String tag) throws Exception {
    return jooq.transactionResult(configuration -> {

      Timestamp now = new Timestamp(System.currentTimeMillis());
      ReleaseTaskRecord releaseTaskRecord = jooq.newRecord(RELEASE_TASK);
      releaseTaskRecord = releaseTaskDAO.queryById(id);
      releaseTaskRecord.setReleaseType(releaseType);
      releaseTaskRecord.setProposal(proposal);
      releaseTaskRecord.setTitle(title);
      releaseTaskRecord.setProjectLocation(projectURL);
      releaseTaskRecord.setProjectDesc(projectDescription);
      releaseTaskRecord.setUpdateTime(now);
      releaseTaskRecord.setTag(tag);
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


//  @Override
//  public TaskWithCount queryAll(int pageSize, int currentPaged) throws Exception {
//
//    List<ReleaseTask> releaseTasks = new ArrayList<>();
//    List<ReleaseTaskRecord> releaseTaskRecords = releaseTaskDAO.query(pageSize, currentPaged).getRecords();
//    int totalCountUp = releaseTaskDAO.query(pageSize, currentPaged).getTotalCount();
//
//    ReleaseTask.Builder builder = new ReleaseTask.Builder();
//
//    for (ReleaseTaskRecord releaseTaskRecord : releaseTaskRecords) {
//      ReleaseTask releaseTask = convertEntityToDomain(builder, releaseTaskRecord);
//      releaseTasks.add(releaseTask);
//    }
//
//    return new TaskWithCount(totalCountUp, releaseTasks);
//  }
//
//  @Override
//  public TaskWithCount queryAll(int pageSize, int currentPaged, int releaseType) throws Exception {
//    List<ReleaseTask> releaseTasks = new ArrayList<>();
//
//    List<ReleaseTaskRecord> releaseTaskRecords = releaseTaskDAO.query(pageSize, currentPaged, releaseType).getRecords();
//    int totalCountUp = releaseTaskDAO.query(pageSize, currentPaged, releaseType).getTotalCount();
//    ReleaseTask.Builder builder = new ReleaseTask.Builder();
//
//    for (ReleaseTaskRecord releaseTaskRecord : releaseTaskRecords) {
//      ReleaseTask releaseTask = convertEntityToDomain(builder, releaseTaskRecord);
//      releaseTasks.add(releaseTask);
//    }
//    return new TaskWithCount(totalCountUp, releaseTasks);
//  }
//
//  @Override
//  public TaskWithCount queryAll(int pageSize, int currentPaged, byte releaseStatus) throws Exception {
//    List<ReleaseTask> releaseTasks = new ArrayList<>();
//
//    List<ReleaseTaskRecord> releaseTaskRecords = releaseTaskDAO.query(pageSize, currentPaged, releaseStatus).getRecords();
//    int totalCountUp = releaseTaskDAO.query(pageSize, currentPaged, releaseStatus).getTotalCount();
//    ReleaseTask.Builder builder = new ReleaseTask.Builder();
//
//    for (ReleaseTaskRecord releaseTaskRecord : releaseTaskRecords) {
//      ReleaseTask releaseTask = convertEntityToDomain(builder, releaseTaskRecord);
//      releaseTasks.add(releaseTask);
//    }
//    return new TaskWithCount(totalCountUp, releaseTasks);
//  }
//
//  @Override
//  public TaskWithCount queryAll(int pageSize, int currentPaged, int releaseType, byte releaseStatus) throws Exception {
//    List<ReleaseTask> releaseTasks = new ArrayList<>();
//
//    List<ReleaseTaskRecord> releaseTaskRecords = releaseTaskDAO.query(pageSize, currentPaged, releaseType, releaseStatus).getRecords();
//    int totalCountUp = releaseTaskDAO.query(pageSize, currentPaged, releaseType, releaseStatus).getTotalCount();
//    ReleaseTask.Builder builder = new ReleaseTask.Builder();
//
//    for (ReleaseTaskRecord releaseTaskRecord : releaseTaskRecords) {
//      ReleaseTask releaseTask = convertEntityToDomain(builder, releaseTaskRecord);
//      releaseTasks.add(releaseTask);
//    }
//    return new TaskWithCount(totalCountUp, releaseTasks);
//  }

  @Override
  public TaskWithCount queryAll(JsonObject jsonObject,int i,int skip, int limit) throws RuntimeException {
    List<ReleaseTask> releaseTasks = new ArrayList<>();
    ConditionParser.SQLCondition sqlCondition = conditionParser.getSQLCondition(jsonObject);
    List<ReleaseTaskRecord> releaseTaskRecords = releaseTaskDAO.query(sqlCondition,i,skip,limit).getRecords();
    int totalCountUp = releaseTaskDAO.query(sqlCondition,i,skip,limit).getTotalCount();

    ReleaseTask.Builder builder = new ReleaseTask.Builder();
    for (ReleaseTaskRecord releaseTaskRecord : releaseTaskRecords) {
      ReleaseTask releaseTask = convertEntityToDomain(builder, releaseTaskRecord);
      releaseTasks.add(releaseTask);
    }
    return new TaskWithCount(totalCountUp, releaseTasks);


  }

  private ReleaseTask convertEntityToDomain(ReleaseTask.Builder builder, ReleaseTaskRecord releaseTaskRecord) {
    builder.proposal(releaseTaskRecord.getProposal());
    builder.title(releaseTaskRecord.getTitle());
    builder.id(releaseTaskRecord.getId());
    builder.projectDesc(releaseTaskRecord.getProjectDesc());
    builder.projectLocation(releaseTaskRecord.getProjectLocation());
    builder.status(Status.getByOrdinal(releaseTaskRecord.getStatus()));
    builder.proposalTime(releaseTaskRecord.getProposalTime());
    builder.releaseType(releaseTaskRecord.getReleaseType());
    builder.updateTime(releaseTaskRecord.getUpdateTime());
    builder.releaseRemark(releaseTaskRecord.getReleaseRemark());

    return builder.build();
  }

}


