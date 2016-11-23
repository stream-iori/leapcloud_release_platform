FROM 10.10.10.160:8010/maxleap/base:${maxleap-stack.version}
MAINTAINER Stream.Liu <xxliu@maxleap.com>

#----------------------------Copy 项目目录到容器里--------------------------------------------

RUN \
mkdir -p /opt/service/release_platform

#覆盖vert.x 相关配置
ADD lib/ $VERTX_HOME/lib/
ADD log4j2.xml $VERTX_HOME/conf
ADD config.json /opt/service/release_platform

#设置vertcile class name
ENV VERTICLE_CLASS_NAME cn.leapcloud.release.platform.Starter

#设置一些JVM参数
ENV JVM_OPTS="-server -Xms1g -Xmx1g -Xmn384m -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0 -XX:+PrintCommandLineFlags"

WORKDIR /opt/service/release_platform
ENTRYPOINT ["default-start.sh", "--conf", "config.json"]

