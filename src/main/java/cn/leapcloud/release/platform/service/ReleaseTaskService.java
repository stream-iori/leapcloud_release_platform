package cn.leapcloud.release.platform.service;

import cn.leapcloud.release.platform.service.domain.ReleaseTask;

import java.util.List;

/**
 * Created by songqian on 16/12/2.
 */
public interface ReleaseTaskService {


  boolean createNewTask(int releaseType, String proposal, String title, String projectURL, String projectDescription)
    throws Exception;

  boolean updateNewTask(int id, int releaseType, String proposal, String title, String projectURL, String projectDescription)
    throws Exception;

  List<ReleaseTask> queryAll(int pageSize,int currentPaged) throws Exception ;

}
