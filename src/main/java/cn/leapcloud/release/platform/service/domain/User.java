package cn.leapcloud.release.platform.service.domain;

import io.vertx.core.json.JsonObject;

/**
 * Created by stream.
 */
public class User {

  private final int id;
  private final String name;
  private final String email;
  private final String ddId;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getDdId() {
    return ddId;
  }

  public JsonObject toJson() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.put("id", id);
    jsonObject.put("name", name);
    jsonObject.put("email", email);
    jsonObject.put("ddId", ddId);
    return jsonObject;
  }


  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", email='" + email + '\'' +
      ", ddid='" + ddId + '\'' +
      '}';
  }

  public static final class Builder {
    private int id = 0;
    private String name = null;
    private String email = null;
    private String ddId = null;

    public Builder id(int id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder ddId(String ddId) {
      this.ddId = ddId;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }

  private User(Builder b) {
    id = b.id;
    name = b.name;
    email = b.email;
    ddId = b.ddId;
  }
}
