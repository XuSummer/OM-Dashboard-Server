FROM adtiming/omjava
MAINTAINER openmediation
ENV SYSTEMNAME "omsystem"
ENV SERVERNAME "om-ds-server"
ENV OMYML_DBADDRESS ""
ENV OMYML_DBNAME ""
ENV OMYML_USERNAME ""
ENV OMYML_PASSWORD ""
ENV OMYML_SERVER ""
ENV OMYML_REDIS_PASSWORD ""
ENV OMYML_OMADCDOMAIN ""
RUN mkdir ${SERVERNAME}
ARG JAR_FILE
ADD docker/docker-entrypoint.sh /
ADD docker/application-loc.yml /${SERVERNAME}/
ADD ${JAR_FILE} /${SERVERNAME}/
WORKDIR /
RUN chmod +x /docker-entrypoint.sh /${SERVERNAME}/${SERVERNAME}.jar
EXPOSE 19013
ENTRYPOINT ["/docker-entrypoint.sh"] 
CMD ["om-ds-server"]
