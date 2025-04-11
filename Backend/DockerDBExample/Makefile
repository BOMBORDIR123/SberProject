REGISTRY=registry.gitlab.com/structura1/backend
VERSION=${shell cat VERSION}

build-docker:
	docker buildx build --platform linux/amd64 -t ${REGISTRY}:${VERSION} .
	docker push ${REGISTRY}:${VERSION}