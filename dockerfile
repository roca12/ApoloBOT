# we will use openjdk 8 with alpine as it is a very small linux distro
FROM openjdk:26-slim
# copy the packaged jar file into our docker image
COPY target/apolobot-2025.1.jar /ApoloBOT.jar
# set the startup command to execute the jar
CMD ["java", "-jar", "/ApoloBOT.jar"]
