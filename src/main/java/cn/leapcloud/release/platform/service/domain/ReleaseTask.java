package cn.leapcloud.release.platform.service.domain;

import java.util.Date;

/**
 * Created by songqian on 16/11/23.
 */
public class ReleaseTask {
  private final int id;
  private final int releaseType;
  private final String title;
  private final String projectLocation;
  private final String projectDesc;
  private final String proposal;
  private final Date proposalTime;
  private final Date updateTime;
  private final int status;
  private final String releaseRemark;

  public int getId() {
    return id;
  }

  public int getReleaseType() {
    return releaseType;
  }

  public String getTitle() {
    return title;
  }

  public String getProjectLocation() {
    return projectLocation;
  }

  public String getProjectDesc() {
    return projectDesc;
  }

  public String getProposal() {
    return proposal;
  }

  public Date getProposalTime() {
    return proposalTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public int getStatus() {
    return status;
  }

  public String getReleaseRemark() {
    return releaseRemark;
  }

  @Override
  public String toString() {
    return "ReleaseTask{" +
      "id=" + id +
      ", releaseType=" + releaseType +
      ", title='" + title + '\'' +
      ", projectLocation='" + projectLocation + '\'' +
      ", projectDesc='" + projectDesc + '\'' +
      ", proposal='" + proposal + '\'' +
      ", proposalTime=" + proposalTime +
      ", updateTime=" + updateTime +
      ", status=" + status +
      ", releaseRemark='" + releaseRemark + '\'' +
      '}';
  }

  public static class Builder {
    private int id;
    private int releaseType;
    private String title;
    private String projectLocation;
    private String projectDesc;
    private String proposal;
    private Date proposalTime;
    private Date updateTime;
    private int status;
    private String releaseRemark;

    public Builder id(int id) {
      this.id = id;
      return this;
    }

    public Builder releaseType(int releaseType) {
      this.releaseType = releaseType;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder projectLocation(String projectLocation) {
      this.projectLocation = projectLocation;
      return this;
    }

    public Builder projectDesc(String projectDesc) {
      this.projectDesc = projectDesc;
      return this;
    }

    public Builder proposal(String proposal) {
      this.proposal = proposal;
      return this;
    }

    public Builder proposalTime(Date proposalTime) {
      this.proposalTime = proposalTime;
      return this;
    }

    public Builder updateTime(Date updateTime) {
      this.updateTime = updateTime;
      return this;
    }

    public Builder status(int status) {
      this.status = status;
      return this;
    }

    public Builder releaseRemark(String releaseRemark) {
      this.releaseRemark = releaseRemark;
      return this;
    }

    public ReleaseTask build() {
      return new ReleaseTask(this);
    }
  }

  private ReleaseTask(Builder builder) {
    id = builder.id;
    releaseType = builder.releaseType;
    title = builder.title;
    projectLocation = builder.projectLocation;
    projectDesc = builder.projectDesc;
    proposal = builder.proposal;
    proposalTime = builder.proposalTime;
    updateTime = builder.updateTime;
    status = builder.status;
    releaseRemark = builder.releaseRemark;
  }
}
