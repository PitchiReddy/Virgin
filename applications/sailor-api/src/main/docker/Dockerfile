FROM frolvlad/alpine-oraclejdk8:8.131.11-slim
ENV DCONFIG ""
ENV LOG_LOCATION /var/log/applogs
CMD mkdir /var/log/applogs
CMD chmod +777 /var/log/applogs
COPY sailor-api-1.0.jar /etc/sailor-api/app.jar
EXPOSE 8432
CMD java -Xmx512M -Dapplication_properties_path="$APPLICATION_PROPERTIES_PATH" -Dservice_properties_path="$SERVICE_PROPERTIES_PATH" -Dlog.file="$LOG_LOCATION"app.log -jar /etc/sailor-api/app.jar
