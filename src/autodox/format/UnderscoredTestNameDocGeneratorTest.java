package autodox.format;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UnderscoredTestNameDocGeneratorTest {

	@Test
	public void generates_human_readable_doc_from_underscored_test_name() throws Exception {

		String testName = "generates_human_readable_doc_from_underscored_test_name";

		ArrayList<String> testNames = new ArrayList<String>();
		testNames.add(testName);
		List<String> documentation = new UnderscoredTestNameDocGenerator().format(testNames);
		
		assertThat("Expected human readable documentation to be generated",documentation.contains("generates human readable doc from underscored test name"), is(true));
	}
}
