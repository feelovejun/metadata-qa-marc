package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.datastore.MarcSolrClient;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

/**
 * usage:
 * java -cp target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.SolrKeyGenerator http://localhost:8983/solr/tardit 0001.0000000.formatted.json
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Validator {

	private static final Logger logger = Logger.getLogger(Validator.class.getCanonicalName());

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Please provide a MARC file name!");
			System.exit(0);
		}
		long start = System.currentTimeMillis();

		String relativeFileName = args[0];
		Path path = Paths.get(relativeFileName);
		String fileName = path.getFileName().toString();

		JsonPathCache<? extends XmlFieldInstance> cache;
		List<String> records;
		try {
			MarcReader reader = ReadMarc.getReader(path.toString());

			File output = new File("validation-report.txt");
			if (output.exists())
				output.delete();

			int i = 0;
			while (reader.hasNext()) {
				i++;
				Record marc4jRecord = reader.next();
				MarcRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord);
				boolean isValid = marcRecord.validate();
				if (!isValid) {
					String message = String.format(
						"%s in '%s': \n\t%s\n",
							(marcRecord.getErrors().size() == 1 ? "Error" : "Errors"),
							marcRecord.getControl001().getContent(),
							StringUtils.join(marcRecord.getErrors(), "\n\t")
					);
					FileUtils.writeStringToFile(output, message, true);
				}

				if (i % 1000 == 0)
					logger.info(String.format("%s/%d) %s", fileName, i, marcRecord.getId()));
			}

			logger.info(String.format("End of cycle. Validated %d records.", i));
		} catch(SolrServerException ex){
			logger.severe(ex.toString());
			System.exit(0);
		} catch(Exception ex){
			logger.severe(ex.toString());
			ex.printStackTrace();
			System.exit(0);
		}
		long end = System.currentTimeMillis();

		logger.info(String.format("Bye! It took: %.1f s", (float) (end - start) / 1000));

		System.exit(0);
	}
}