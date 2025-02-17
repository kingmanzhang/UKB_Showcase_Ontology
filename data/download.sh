#!/bin/bash

# download three schemas from UKB
curl -o ukb_schema/ukb_schema_1.tsv "https://biobank.ndph.ox.ac.uk/showcase/scdown.cgi?id=1&fmt=txt"
curl -o ukb_schema/ukb_schema_3.tsv "https://biobank.ndph.ox.ac.uk/showcase/scdown.cgi?id=3&fmt=txt"
curl -o ukb_schema/ukb_schema_13.tsv "https://biobank.ndph.ox.ac.uk/showcase/scdown.cgi?id=13&fmt=txt"