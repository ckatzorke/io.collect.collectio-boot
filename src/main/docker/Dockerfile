FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD collectio-boot.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dgiantbomb.apikey=${GIANTBOMB_API_KEY} -jar /app.jar" ]