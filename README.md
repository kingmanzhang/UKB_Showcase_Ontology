[![Maven Package](https://github.com/kingmanzhang/UKB_Showcase_Ontology/actions/workflows/maven-publish.yml/badge.svg?branch=main)](https://github.com/kingmanzhang/UKB_Showcase_Ontology/actions/workflows/maven-publish.yml)
[![Docker Image CI](https://github.com/kingmanzhang/UKB_Showcase_Ontology/actions/workflows/docker-image.yml/badge.svg)](https://github.com/kingmanzhang/UKB_Showcase_Ontology/actions/workflows/docker-image.yml)

# UKB Showcase Ontology

[UKBiobank](https://www.ukbiobank.ac.uk) (UKB) is a large scale dataset of genetic data and clinical phenotypes for ~half a million participants. It is widely used in genetic and epidemiological studies in both academia and the pharmaceutical industry. The goal of this repo is to ontologize the [Showcase](https://biobank.ndph.ox.ac.uk/showcase/) metadata (note: not the individual level data). It can support analytical needs that rely on the hierarchical structure of Showcase. 


# How to use
One can download the ontology file from the [ontology folder](data/ontology/ukb_showcase_ontology.ttl). 

If update is needed, one can rerun the codebase to re-generate the ontology. The app will download the [latest schema files](https://biobank.ndph.ox.ac.uk/showcase/download.cgi) directly from UKB and create the ontology file. 

Run the following command to regenerate the ontology. Note: docker is required. 
`
bash run.sh
`