package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Form of item
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006continuing06 extends ControlSubfieldDefinition {
	private static Tag006continuing06 uniqueInstance;

	private Tag006continuing06() {
		initialize();
		extractValidCodes();
	}

	public static Tag006continuing06 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag006continuing06();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Form of item";
		id = "tag006continuing06";
		mqTag = "formOfItem";
		positionStart = 6;
		positionEnd = 7;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
		codes = Utils.generateCodes(
			" ", "None of the following",
			"a", "Microfilm",
			"b", "Microfiche",
			"c", "Microopaque",
			"d", "Large print",
			"f", "Braille",
			"o", "Online",
			"q", "Direct electronic",
			"r", "Regular print reproduction",
			"s", "Electronic",
			"|", "No attempt to code"
		);
	}
}