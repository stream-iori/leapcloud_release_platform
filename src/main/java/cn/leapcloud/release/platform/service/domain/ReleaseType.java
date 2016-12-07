package cn.leapcloud.release.platform.service.domain;

import io.vertx.core.json.JsonObject;

/**
 * Created by songqian on 16/12/7.
 */
public class ReleaseType {
  private  int id;
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "ReleaseType{" +
      "id=" + id +
      ", name='" + name + '\'' +
      '}';
  }
  public JsonObject toJson() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.put("id", id);
    jsonObject.put("name", name);
    return jsonObject;
  }













  public static final class Builder {
    private int id = 0;
    private String name = null;

    public Builder id(int id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public ReleaseType build() {
      return new ReleaseType(this);
    }
  }

    private ReleaseType (Builder b) {
      id = b.id;
      name = b.name;
    }

}
