package gov.acwi.wqp.etl.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import gov.acwi.wqp.etl.NwisBaseFlowIT;
import liquibase.pro.packaged.ba;

//	@DatabaseSetup(connection = CONNECTION_NWIS)
@DatabaseSetup(connection = "nwis", value = "classpath:/testData/nwis/nwisDistrictCdsByHost/nwisDistrictCdsByHost.xml")
public class DmsToGeometryIT extends NwisBaseFlowIT {
	@Autowired
	@Qualifier("orgDataFlow")
	private Flow orgDataFlow;

	protected String sqlTemplate = "select ST_X(geom) long,  ST_Y(geom) lat from ("
			+ "select dms_to_geometry('%s', '%s') geom) a";

	// test cases covering the different lengths of dms longitute and latitude trings
	protected TestCase[] testCases = { tc("0773337.819", -77.5605053, "353113.665", 35.5204625),
			tc("1704659.38", -170.7831611, "-141929.17", -14.3247694),
			tc("0835603.77", -83.9343806, "341842.73", 34.3118694),
			tc("0791636.98", -79.2769389, "352646.1", 35.4461389), tc("0670515.6", -67.0876667, "180900.4", 18.1501111),
			tc("0794248.83", -79.7135639, "404236", 40.71), tc("0794213", -79.70, "410114.7", 41.02075),
			tc("-1790555", 179.10, "512901", 51.48), tc("1005238", -100.88, "363002", 36.50),
			tc("963120", -96.52, "485809", 48.97) };

	// test cases with badly formatted dms values, expect null to be returned
	protected TestCase[] badInputCases = { tc("0773337.81a", null, "353113.665", null),
			tc("0773337.81", null, "", null), tc("", null, "", null), tc(" ", null, " ", null),
			tc("a773337.81", null, "73337.81", null), tc("0773337.81a", null, "353113.665", null),
			tc("80773337..81", null, "173337.81", null), tc("0773337.81", null, "353113..665", null),
			tc(".077", null, "353113.665", null), tc("0773337.81", null, ".665", null),
			tc("0835603a77", null, "341842.73", null), tc("0835603.77", null, "341842-73", null) };

	// test cases covering some well know or edge values
	protected TestCase[] wellKnowInputCases = { tc("0000000.0", 0.0, "000000", 0.0),
			tc("903000.0", -90.50, "904500", 90.75), tc("1795959.99", -179.9999972, "895959.0", 89.9997222),
			tc("1795959", -180.00, "895959", 90.00) };

	// test cases where function is called with a null parameter.
	protected TestCase[] nullParamTestCases = { tc("null", null, "000000", null), tc("0000000.0", null, "null", null),
			tc("null", null, "null", null) };

	@Test
	public void dmsToGeometryTest() {
		try {
			System.out.println("IN dmsToGemoteryTest");
			for (TestCase testCase : allTestCases()) {
				String sql = String.format(sqlTemplate, testCase.dms_long, testCase.dms_lat).replace("'null'", "null");
				Map<String, Object> rowSet = jdbcTemplate.queryForMap(sql);
				assertTrue(rowSet.containsKey("long"));
				assertTrue(rowSet.containsKey("lat"));
				if (testCase.expected_dec_long_va == null) {
					assertNull(rowSet.get("long"));
				} else {
					assertTrue(rowSet.get("long") instanceof Double);
					assertEquals(testCase.expected_dec_long_va, rowSet.get("long"), "longitude off: " + testCase);
				}
				if (testCase.expected_dec_lat_va == null) {
					assertNull(rowSet.get("lat"));
				} else {
					assertTrue(rowSet.get("lat") instanceof Double);
					assertEquals(testCase.expected_dec_lat_va, rowSet.get("lat"), "latitude off: " + testCase);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	protected List<TestCase> allTestCases() {
		List<TestCase> cases = new ArrayList<TestCase>();
		cases.addAll(Arrays.asList(testCases));
		cases.addAll(Arrays.asList(badInputCases));
		cases.addAll(Arrays.asList(wellKnowInputCases));
		cases.addAll(Arrays.asList(nullParamTestCases));
		return cases;
	}

	protected TestCase tc(String dms_long, Double expected_dec_long_va, String dms_lat, Double expected_dec_lat_va) {
		return new TestCase(dms_long, expected_dec_long_va, dms_lat, expected_dec_lat_va);
	};

	protected class TestCase {
		String dms_long;
		Double expected_dec_long_va;
		String dms_lat;
		Double expected_dec_lat_va;

		public TestCase(String dms_long, Double expected_dec_long_va, String dms_lat, Double expected_dec_lat_va) {
			this.dms_long = dms_long;
			this.expected_dec_long_va = expected_dec_long_va;
			this.dms_lat = dms_lat;
			this.expected_dec_lat_va = expected_dec_lat_va;
		}

		@Override
		public String toString() {
			return "TestCase [dms_long=" + dms_long + ", expected_dec_long_va=" + expected_dec_long_va + ", dms_lat="
					+ dms_lat + ", expected_dec_lat_va=" + expected_dec_lat_va + "]";
		}
	}

}
