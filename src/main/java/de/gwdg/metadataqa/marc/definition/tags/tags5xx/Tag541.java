package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Immediate Source of Acquisition Note
 * http://www.loc.gov/marc/bibliographic/bd541.html
 */
public class Tag541 extends DataFieldDefinition {

	private static Tag541 uniqueInstance;

	private Tag541() {
		initialize();
		postCreation();
	}

	public static Tag541 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag541();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "541";
		label = "Immediate Source of Acquisition Note";
		bibframeTag = "ImmediateAcquisition";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd541.html";

		ind1 = new Indicator("Privacy")
			.setCodes(
				" ", "No information provided",
				"0", "Private",
				"1", "Not private"
			)
			.setMqTag("privacy");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Source of acquisition", "NR",
			"b", "Address", "NR",
			"c", "Method of acquisition", "NR",
			"d", "Date of acquisition", "NR",
			"e", "Accession number", "NR",
			"f", "Owner", "NR",
			"h", "Purchase price", "NR",
			"n", "Extent", "R",
			"o", "Type of unit", "R",
			"3", "Materials specified", "NR",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("b").setMqTag("address");
		getSubfield("c").setMqTag("method");
		getSubfield("d").setMqTag("date");
		getSubfield("e").setMqTag("accessionNumber");
		getSubfield("f").setMqTag("owner");
		getSubfield("h").setMqTag("price");
		getSubfield("n").setMqTag("extent");
		getSubfield("o").setMqTag("typeOfUnit");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("5").setMqTag("institutionToWhichFieldApplies");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
