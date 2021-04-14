package gov.acwi.wqp.etl.nwis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import gov.acwi.wqp.etl.NwisBaseFlowIT;
import gov.acwi.wqp.etl.nwis.monitoringLocation.MLNewQueryHelper;
import gov.acwi.wqp.etl.nwis.monitoringLocation.TransformNwisMonitoringLocationIT;

public class NwisFlowIT extends NwisBaseFlowIT {

	@Autowired
	@Qualifier("nwisFlow")
	private Flow nwisFlow;

	@Test
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/agency/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/altitudeDatum/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/altitudeMethod/altitudeMethod.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/aqfr/aqfr.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/aquiferType/aquiferType.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/county/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/dataReliability/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/fxd/sampleParameterTest/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/huc/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/latLongAccuracy/latLongAccuracy.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/latLongDatum/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/latLongMethod/latLongMethod.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/monitoringLocation/purge/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/natAqfr/natAqfr.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/nwisDistrictCdsByHost/nwisDistrictCdsByHost.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/qwResult/qwResultForSampleParameterTest.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/sampleParameter/sampleParameterOld.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/sitefile/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/siteTp/siteTp.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/state/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/topographicSetting/")

	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testResult/nwis/sampleParameter/empty.xml")

	@DatabaseSetup(value = "classpath:/testData/wqp/huc12nometa/")

	@ExpectedDatabase(connection = CONNECTION_NWIS,
			value = "classpath:/testResult/nwis/sampleParameter/sampleParameter.xml",
			assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(
			connection=CONNECTION_NWIS,
			value="classpath:/testResult/nwis/monitoringLocation/merges/",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TransformNwisMonitoringLocationIT.EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=TransformNwisMonitoringLocationIT.EXPECTED_DATABASE_QUERY_MERGES
			)
	@ExpectedDatabase(
			connection=CONNECTION_NWIS,
			value="classpath:/testResult/nwis/monitoringLocation/new/",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TransformNwisMonitoringLocationIT.EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=MLNewQueryHelper.EXPECTED_DATABASE_QUERY_MERGES_NEW
			)
	public void nwisTest() {
		Job nwisFlowTest = jobBuilderFactory.get("nwisFlowTest")
				.start(nwisFlow)
				.build()
				.build();
		jobLauncherTestUtils.setJob(nwisFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			Thread.sleep(1000);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
}
