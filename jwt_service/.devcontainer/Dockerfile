FROM golang:1.24.5-alpine

RUN apk add --no-cache git bash

RUN addgroup -S vscode && adduser -S vscode -G vscode

USER vscode
WORKDIR /workspace