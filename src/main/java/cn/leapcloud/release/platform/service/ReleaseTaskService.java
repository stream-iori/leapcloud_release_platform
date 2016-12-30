package cn.leapcloud.release.platform.service;

import cn.leapcloud.release.platform.dao.impl.TaskWithCount;

/**
 * Created by songqian on 16/12/2.
 */
public interface ReleaseTaskService {


  boolean createNewTask(int releaseType, String proposal, String title, String projectURL, String projectDescription)
    throws Exception;

  boolean updateNewTask(int id, int releaseType, String proposal, String title, String projectURL, String projectDescription)
    throws Exception;

  boolean manageNewTask(int id , byte status, String releaseRemark) throws Exception;



}
