package com.kingmanzhang;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.vocabulary.*;
import picocli.CommandLine;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "ontologize", description = "Ontologize a given input file.")
public class OntologizeCommand implements Callable<Integer> {

//    @CommandLine.Parameters(index = "0", description = "The input file to ontologize.")
//    private File inputFile;
//
    @CommandLine.Option(names = {"-o", "--out_dir"}, description = "The " +
            "output folder path")
    private File outDir;

    @Override
    public Integer call() throws Exception {

        final String UKB_NS = "https://biobank.ndph.ox.ac.uk/showcase#";
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        // create the active ontology
        Ontology ontology = model.createOntology(UKB_NS);
        ontology.addLabel(model.createTypedLiteral("UKB Showcase Ontology"));
        ontology.addProperty(DC.creator, "Aaron Zhang");
        ontology.addProperty(DC.creator, "https://orcid.org/0000-0002-7284-3950");
        ontology.addProperty(DC.description, "An ontology of UKB Showcase " +
                "surveys. Ontology structure is based on UKB website.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(new Date());
        ontology.addProperty(OWL.versionInfo, today);

        //create a few properties
        Property hasValueType = model.createDatatypeProperty(UKB_NS +
                "datatype");
        Property hasUnit = model.createDatatypeProperty(UKB_NS + "unit");
        Property hasDebut = model.createDatatypeProperty(UKB_NS + "debut");
        Property hasVersion = model.createDatatypeProperty(UKB_NS + "version");
        Property hasStrata = model.createDatatypeProperty(UKB_NS + "strata");

        //first, add category hierarchy
        //parse file from URL "https://biobank.ndph.ox.ac.uk/showcase/scdown.cgi?id=1&fmt=txt"

        String schema13 = "https://biobank.ndph.ox.ac.uk/showcase/scdown" +
                ".cgi?id=13&fmt=txt";
        // Create a URL object
        URL url = new URL(schema13);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))){

            String line = reader.readLine();
            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                // Process each line as needed
                String [] elements = line.split("\t");
                String parent_id = elements[0];
                String child_id = elements[1];

                OntClass parentClass =
                        model.createClass(UKB_NS + "category" + "_" + parent_id);
                OntClass childClass =
                        model.createClass(UKB_NS + "category" + "_" + child_id);
                childClass.addSuperClass(parentClass);
            }

            // Close the reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // add category annotations
        String schema3 = "https://biobank.ndph.ox.ac.uk/showcase/scdown.cgi?id=3&fmt=txt";
        // Create a URL object
        url = new URL(schema3);
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(url.openStream()))){
            String line = reader.readLine();
            while ((line = reader.readLine()) != null){
                String[] elements = line.split("\t", -1);
                String category_id = elements[0];
                String title = elements[1];
                String availability = elements[2];
                String groupType = elements[3];
                String descript = elements[4];
                String notes = elements[5];

                OntClass categoryClass = model.getOntClass(UKB_NS + "category" + "_" + category_id);
                if (categoryClass != null) {
                    categoryClass.addProperty(RDFS.label, title);
                    categoryClass.addProperty(SKOS.notation,
                            "Category_" + category_id);
                    categoryClass.addProperty(SKOS.prefLabel, title);
                    categoryClass.addProperty(SKOS.definition, descript);
                    categoryClass.addProperty(SKOS.note, notes);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }


        // Add fields to each category
        String schema1 = "https://biobank.ndph.ox.ac.uk/showcase/scdown" +
                ".cgi?id=1&fmt=txt";
        url = new URL(schema1);
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(url.openStream()))){
            String line = reader.readLine();
            while ((line = reader.readLine()) != null){
                String[] elements = line.split("\t", -1);
                //Schema 1 has the following columns:
                //field_id	title	availability	stability	private	value_type	base_type	item_type	strata	instanced	arrayed	sexed	units	main_category	encoding_id	instance_id	instance_min	instance_max	array_min	array_max	notes	debut	version	num_participants	item_count	showcase_order	cost_do	cost_on	cost_sc
                //add some to the ontology
                String field_id = elements[0];
                String title = elements[1];
                String availability	= elements[2];
                String stability = elements[3];
                String private_ = elements[4];
                String value_type = elements[5];
                String base_type = elements[6];
                String item_type = elements[7];
                String strata = elements[8];
                String instanced = elements[9];
                String arrayed = elements[10];
                String sexed = elements[11];
                String units = elements[12];
                String main_category = elements[13];
                String encoding_id = elements[14];
                String instance_id = elements[15];
                String instance_min	= elements[16];
                String instance_max	= elements[17];
                String array_min = elements[18];
                String array_max = elements[19];
                String notes = elements[20];
                String debut = elements[21];
                String version	= elements[22];
                String num_participants	= elements[23];
                String item_count = elements[24];
                String showcase_order	= elements[25];
                String cost_do = elements[26];
                String cost_on	= elements[27];
                String cost_sc = elements[28];

                // ignore fields starts with "EMBARGOED"
                if (title.startsWith("EMBARGOED")){
                    continue;
                }
                OntClass fieldClass = model.createClass(UKB_NS + "field" +
                        "_" + field_id);
                fieldClass.addProperty(RDFS.label, title);
                fieldClass.addProperty(SKOS.notation, "Field_" + field_id);
                fieldClass.addProperty(SKOS.prefLabel, title);
                fieldClass.addProperty(SKOS.note, notes);
                fieldClass.addProperty(hasValueType, value_type);
                fieldClass.addProperty(hasUnit, units);
                fieldClass.addProperty(hasDebut,
                        model.createTypedLiteral(debut.substring(0, 10)));
                fieldClass.addProperty(hasVersion,
                        model.createTypedLiteral(version.substring(0, 10)));
                fieldClass.addProperty(hasStrata, strata);

                OntClass categoryClass = model.getOntClass(UKB_NS + "category" + "_" + main_category);
                if (categoryClass != null){
                    fieldClass.addSuperClass(categoryClass);
                }

            }
        } catch (IOException e){
            e.printStackTrace();
        }

        // add top level classes to OWL:Thing
        int[] level1Categories = new int[]{100088, 100000, 100078, 100314,
                100091, 100089, 1};
        for (int i = 0; i < level1Categories.length; i++){
            model.getOntClass(UKB_NS + "category" + "_" + level1Categories[i]).addSuperClass(OWL.Thing);
        }

        // delete class for category 184
        // it's a weird class with no fields or meta information
        model.getOntClass(UKB_NS + "category" + "_" + 184).remove();


        model.setNsPrefix("ukb", "https://biobank.ndph.ox.ac.uk/showcase#");
        model.setNsPrefix("skos", "http://www.w3.org/2004/02/skos/core#");
        model.setNsPrefix("dc", "http://purl.org/dc/elements/1.1/");


        model.write(new FileWriter(outDir.getAbsolutePath() + "/" +
                        "ukb_showcase_ontology.ttl"),
                "TTL");


        return 0; // Success
    }
}
