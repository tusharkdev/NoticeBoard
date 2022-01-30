FROM adoptopenjdk/openjdk11
LABEL maintainer="Tushar Kamble"
VOLUME /main-app
ADD target/announcement-0.0.1-SNAPSHOT.jar announcement.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar","/announcement.jar"]