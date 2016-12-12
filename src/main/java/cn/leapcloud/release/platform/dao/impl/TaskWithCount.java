package cn.leapcloud.release.platform.dao.impl;


import cn.leapcloud.release.platform.service.domain.ReleaseTask;

import java.util.List;

/**
 * Created by songqian on 16/12/12.
 */
public class TaskWithCount {


  private final int totalCount;
  private final List<ReleaseTask> releaseTasks;

  public TaskWithCount(int totalCount, List<ReleaseTask> releaseTasks) {
    this.totalCount = totalCount;
    this.releaseTasks = releaseTasks;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public List<ReleaseTask> getReleaseTasks() {
    return releaseTasks;
  }
}
