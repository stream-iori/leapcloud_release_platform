package cn.leapcloud.release.platform.dao;

import cn.leapcloud.release.platform.controller.ConditionParser;
import cn.leapcloud.release.platform.dao.entity.tables.records.ReleaseTaskRecord;
import cn.leapcloud.release.platform.dao.impl.TaskRecordWithCount;
import org.jooq.Configuration;

/**
 * Created by songqian on 16/11/25.
 */
public interface ReleaseTaskDAO {

  boolean doCreate(ReleaseTaskRecord releaseTaskRecord, Configuration configuration) throws Exception;

  boolean doUpdate(ReleaseTaskRecord releaseTaskRecord, Configuration configuration) throws Exception;

  ReleaseTaskRecord queryById(int id) throws Exception;

//  TaskRecordWithCount query(int pageSize, int currentPaged) throws Exception;
//
//  TaskRecordWithCount query(int pageSize, int currentPaged, int releaseType) throws Exception;
//
//  TaskRecordWithCount query(int pageSize, int currentPaged, byte releaseStatus) throws Exception;
//
//  TaskRecordWithCount query(int pageSize, int currentPaged, int releaseType, byte releaseStatus) throws Exception;

  TaskRecordWithCount query(ConditionParser.SQLCondition sqlCondition,int i,int skip,int limit) throws RuntimeException;

}
