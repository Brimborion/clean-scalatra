FROM kong:2.0.2-alpine

LABEL description="Alpine + Kong 2.0.2 + jwt-keycloak plugin"

USER root
RUN apk update \
    && apk add git unzip luarocks wget \
    && rm -rf /var/cache/apk/*
RUN luarocks install kong-plugin-jwt-keycloak