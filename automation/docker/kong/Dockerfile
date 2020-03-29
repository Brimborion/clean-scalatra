FROM kong:2.0.2-alpine

LABEL description="Alpine + Kong 2.0.2 + kong-oidc plugin"

USER root
RUN apk update \
    && apk add git unzip luarocks wget \
    && rm -rf /var/cache/apk/*
RUN luarocks install kong-oidc