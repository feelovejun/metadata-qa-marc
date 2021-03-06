package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * With Note
 * http://www.loc.gov/marc/bibliographic/bd501.html
 */
public class Tag501 extends DataFieldDefinition {

	private static Tag501 uniqueInstance;

	private Tag501() {
		initialize();
		postCreation();
	}

	public static Tag501 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag501();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "501";
		label = "With Note";
		mqTag = "WithNote";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd501.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "With note", "NR",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("5").setMqTag("institutionToWhichFieldApplies");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
