package cn.leapcloud.release.platform.dao.impl;

import cn.leapcloud.release.platform.dao.entity.tables.records.ReleaseTaskRecord;

import java.util.List;

/**
 * Created by songqian on 16/12/12.
 */
public class TaskRecordWithCount {

  private final int totalCount;
  private final List<ReleaseTaskRecord> records;

  public TaskRecordWithCount(int totalCount, List<ReleaseTaskRecord> records) {
    this.totalCount = totalCount;
    this.records = records;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public List<ReleaseTaskRecord> getRecords() {
    return records;
  }
}
