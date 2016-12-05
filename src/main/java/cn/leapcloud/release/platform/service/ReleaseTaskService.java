package cn.leapcloud.release.platform.service;

/**
 * Created by songqian on 16/12/2.
 */
public interface ReleaseTaskService {


  boolean createNewTask(int releaseType, String proposal, String title, String projectURL, String projectDescription)
    throws Exception;

  boolean updateNewTask(int id, int releaseType, String proposal, String title, String projectURL, String projectDescription)
    throws Exception;

}
