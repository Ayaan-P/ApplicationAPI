FROM openjdk:17
EXPOSE 5000

ADD target/asylum-api-management.jar app.jar

ENTRYPOINT ["java", "-jar", "/asylum-api-management.jar"]
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar asylumapimanagement.jar
