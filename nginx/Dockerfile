FROM nginx:stable-alpine

COPY default.conf /etc/nginx/conf.d/default.conf

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

CMD ["/wait-for-it.sh", "spring-boot-service:8080", "--", "nginx", "-g", "daemon off;"]
