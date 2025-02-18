# build docker
docker build -t ukb-showcase-ontology-app .

# run docker
docker run -it --rm -v $(pwd)/data/ontology:/app/data/ontology ukb-showcase-ontology-app