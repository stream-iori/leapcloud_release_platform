package cn.leapcloud.release.platform.service.domain;

/**
 * Created by songqian on 16/11/23.
 */
public enum Status {
  WAIT, DONE, FAIL, DISCARD;

  public static Status getByOrdinal(byte ordinal) {
    switch (ordinal) {
      case 0:
        return Status.WAIT;
      case 1:
        return DONE;
      case 2:
        return FAIL;
      case 3:
        return DISCARD;
      default:
        return WAIT;
    }
  }

}

