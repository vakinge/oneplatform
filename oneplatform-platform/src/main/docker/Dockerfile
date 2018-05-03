FROM java:8
ADD @project.name@.jar @project.name@.jar
RUN sh -c 'touch /@project.name@.jar'
ENV JAVA_OPTS=""
EXPOSE 8001
CMD exec java $JAVA_OPTS -jar /@project.name@.jar

