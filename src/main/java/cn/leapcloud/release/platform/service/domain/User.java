package cn.leapcloud.release.platform.service.domain;

/**
 * Created by stream.
 */
public class User {

  private final int id;
  private final String name;
  private final String email;

  public User(int id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public int id() {
    return id;
  }

  public String name() {
    return name;
  }

  public String email() {
    return email;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", email='" + email + '\'' +
      '}';
  }

  public static final class Builder {
    private int id;
    private String name;
    private String email;

    public void id(int id) {
      this.id = id;
    }

    public void name(String name) {
      this.name = name;
    }

    public void email(String email) {
      this.email = email;
    }

    public User build() {
      return new User(id, name, email);
    }

    public static User build(int id, String name, String email) {
      return new User(id, name, email);
    }
  }

}
