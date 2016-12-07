package cn.leapcloud.release.platform.service;

import cn.leapcloud.release.platform.service.domain.ReleaseType;

import java.util.List;

/**
 * Created by songqian on 16/12/7.
 */
public interface ReleaseTypeService {

  List<ReleaseType> queryAll() throws Exception;
  
}
