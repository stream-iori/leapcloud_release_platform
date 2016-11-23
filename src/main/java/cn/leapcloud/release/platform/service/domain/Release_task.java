package cn.leapcloud.release.platform.service.domain;

import java.util.Date;

/**
 * Created by songqian on 16/11/23.
 */
public class Release_task {
  private int id;
  private int release_type;
  private String title;
  private String project_location;
  private String project_desc;
  private String proposal;
  private Date proposal_time;
  private Date update_time;
  private int status;
  private String release_remark;

  public int getId() {
    return id;
  }

  public int getRelease_type() {
    return release_type;
  }

  public String getTitle() {
    return title;
  }

  public String getProject_location() {
    return project_location;
  }

  public String getProject_desc() {
    return project_desc;
  }

  public String getProposal() {
    return proposal;
  }

  public Date getProposal_time() {
    return proposal_time;
  }

  public Date getUpdate_time() {
    return update_time;
  }

  public int getStatus() {
    return status;
  }

  public String getRelease_remark() {
    return release_remark;
  }


  @Override
  public String toString() {
    return "Release_task{" +
      "id=" + id +
      ", release_type=" + release_type +
      ", title='" + title + '\'' +
      ", project_location='" + project_location + '\'' +
      ", project_desc='" + project_desc + '\'' +
      ", proposal='" + proposal + '\'' +
      ", proposal_time=" + proposal_time +
      ", update_time=" + update_time +
      ", status=" + status +
      ", release_remark='" + release_remark + '\'' +
      '}';
  }


  public static class Builder {
    private int id = 0;
    private int release_type = 0;
    private String title = null;
    private String project_location = null;
    private String project_desc = null;
    private String proposal = null;
    private Date proposal_time = null;
    private Date update_time = null;
    private int status = 0;
    private String release_remark = null;

    public Builder id(int id) {
      this.id = id;
      return this;
    }

    public Builder release_type(int release_type) {
      this.release_type = release_type;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder project_location(String project_location) {
      this.project_location = project_location;
      return this;
    }

    public Builder project_desc(String project_desc) {
      this.project_desc = project_desc;
      return this;
    }

    public Builder proposal(String proposal) {
      this.proposal = proposal;
      return this;
    }

    public Builder proposal_time(Date proposal_time) {
      this.proposal_time = proposal_time;
      return this;
    }

    public Builder update_time(Date update_time) {
      this.update_time = update_time;
      return this;
    }

    public Builder status(int status) {
      this.status = status;
      return this;
    }

    public Builder release_remark(String release_remark) {
      this.release_remark = release_remark;
      return this;
    }

    public Release_task build() {
      return new Release_task(this);
    }
  }

  private Release_task(Builder builder) {
    id = builder.id;
    release_type = builder.release_type;
    title = builder.title;
    project_location = builder.project_location;
    project_desc = builder.project_desc;
    proposal = builder.proposal;
    proposal_time = builder.proposal_time;
    update_time = builder.update_time;
    status = builder.status;
    release_remark = builder.release_remark;
  }
}

