package gov.acwi.wqp.etl.result;

import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.TestPropertySource;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import gov.acwi.wqp.etl.NwisBaseFlowIT;

//See ConfigurationService:
//Env. Config params to control how result partitions are created, ensuring the partition structure is known & repeatable.
//ETL_RUN_TIME is used to construct unique partition names and overrides the actual runTime of the ETL job.
@TestPropertySource(properties = {"ETL_RESULT_PARTITION_START_DATE=1995-01-01",
                                "ETL_RESULT_PARTITION_ONE_YEAR_BREAK=2020-01-01",
                                "ETL_RESULT_PARTITION_QUARTER_BREAK=2020-01-01",
                                "ETL_RESULT_PARTITION_END_DATE=2020-01-01",
                                "ETL_RUN_TIME=2021-01-01T10:15:30"})
public class TransformResultIT extends NwisBaseFlowIT {

	public static final String TABLE_NAME = "'result_swap_nwis'";
	public static final String EXPECTED_DATABASE_QUERY_ANALYZE = BASE_EXPECTED_DATABASE_QUERY_ANALYZE + TABLE_NAME;
	public static final String EXPECTED_DATABASE_QUERY_PRIMARY_KEY = BASE_EXPECTED_DATABASE_QUERY_PRIMARY_KEY
			+ EQUALS_QUERY + TABLE_NAME;
	public static final String EXPECTED_DATABASE_QUERY_FOREIGN_KEY = BASE_EXPECTED_DATABASE_QUERY_FOREIGN_KEY
			+ EQUALS_QUERY + TABLE_NAME;

	@Autowired
	@Qualifier("resultFlow")
	private Flow resultFlow;

	@Test
	@DatabaseSetup(value = "classpath:/testResult/wqp/result/empty.xml")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/qwResult/qwResult.xml")
	@DatabaseSetup(value = "classpath:/testResult/wqp/activity/activity.xml")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/parm/parm.xml")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/fxd/fxd.xml")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/methWithCit/methWithCit.xml")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/parmMeth/parmMeth.xml")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/nwisWqxRptLevCd/nwisWqxRptLevCd.xml")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/qwSample/qwSample.xml")
	@DatabaseSetup(
			connection=CONNECTION_WQX_DUMP,
			value="classpath:/testData/wqxDump/csv/"
	)
	@ExpectedDatabase(
			value = "classpath:/testResult/wqp/result/result.xml",
			assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformNwisResultStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformResultStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Disabled
	@Test
	@DatabaseSetup(value = "classpath:/testData/wqp/result/resultOld.xml")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/qwResult/qwResult.xml")
	@DatabaseSetup(value = "classpath:/testResult/wqp/activity/activity.xml")
	@DatabaseSetup(value = "classpath:/testResult/wqp/monitoringLocation/")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/parm/parm.xml")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/fxd/fxd.xml")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/methWithCit/methWithCit.xml")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/parmMeth/parmMeth.xml")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/nwisWqxRptLevCd/nwisWqxRptLevCd.xml")
	@DatabaseSetup(
			connection = CONNECTION_NWIS,
			value = "classpath:/testData/nwis/qwSample/qwSample.xml")
	@ExpectedDatabase(
			value = "classpath:/testResult/wqp/result/result.xml",
			assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(
			value = "classpath:/testResult/wqp/result/indexes/all.xml",
			assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table = EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + TABLE_NAME)
	@ExpectedDatabase(
			connection = CONNECTION_INFORMATION_SCHEMA,
			value = "classpath:/testResult/wqp/result/create.xml",
			assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table = EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query = BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + TABLE_NAME)
	@ExpectedDatabase(
			value="classpath:/testResult/wqp/analyze/result.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_ANALYZE,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	@ExpectedDatabase(
			value="classpath:/testResult/wqp/result/primaryKey.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_PRIMARY_KEY,
			query=EXPECTED_DATABASE_QUERY_PRIMARY_KEY)
	@ExpectedDatabase(
			value="classpath:/testResult/wqp/result/foreignKey.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_FOREIGN_KEY,
			query=EXPECTED_DATABASE_QUERY_FOREIGN_KEY)
	public void resultFlowTest() {
		Job resultFlowTest = jobBuilderFactory.get("resultFlowTest")
				.start(resultFlow)
				.build()
				.build();
		jobLauncherTestUtils.setJob(resultFlowTest);
		jdbcTemplate.execute("select add_monitoring_location_primary_key('nwis', 'wqp', 'station')");
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
