package cn.leapcloud.release.platform.dao;

import cn.leapcloud.release.platform.dao.entity.tables.records.ReleaseTypeRecord;

import java.util.List;

/**
 * Created by songqian on 16/12/7.
 */
public interface ReleaseTypeDAO {

  List<ReleaseTypeRecord> query() throws Exception;

}



