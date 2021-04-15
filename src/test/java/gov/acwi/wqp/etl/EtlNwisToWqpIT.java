package gov.acwi.wqp.etl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import gov.acwi.wqp.etl.dbFinalize.UpdateLastETLIT;

public class EtlNwisToWqpIT extends NwisBaseFlowIT {

	public static final String EXPECTED_DATABASE_TABLE_STATION_SUM = "station_sum_nwis";
	public static final String EXPECTED_DATABASE_QUERY_STATION_SUM = BASE_EXPECTED_DATABASE_QUERY_STATION_SUM + EXPECTED_DATABASE_TABLE_STATION_SUM;

	public static final String EXPECTED_DATABASE_QUERY_TABLE = BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE_LIKE
			+ "'%nwis%' and table_schema='wqp'";
	public static final String EXPECTED_DATABASE_QUERY_INDEX = BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX_LIKE
			+ "'%nwis' and tablename not like '%swap%'";

	public static final String EXPECTED_DATABASE_QUERY_ANALYZE = BASE_EXPECTED_DATABASE_QUERY_ANALYZE_BARE
			+ "where relname like '%_nwis' and relname not like '%swap%'";

	public static final String EXPECTED_DATABASE_QUERY_PRIMARY_KEY = BASE_EXPECTED_DATABASE_QUERY_PRIMARY_KEY
			+ " like '%_nwis'";

	public static final String EXPECTED_DATABASE_QUERY_FOREIGN_KEY = BASE_EXPECTED_DATABASE_QUERY_FOREIGN_KEY
			+ " like '%_nwis'";

	@Autowired
	@Qualifier("nwisToWqpFlow")
	private Flow nwisToWqpFlow;

	@Autowired
	@Qualifier("jdbcTemplateNwis")
	private JdbcTemplate jdbcTemplateNwis;

	/* EE:  Due to how indexing works on PG11 vs PG12, this test fails w/ the newly partitioned result table. */
	@Disabled
	@Test
	// Lookups and mysql data tables
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/agency/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/altitudeDatum/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/altitudeMethod/altitudeMethod.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/aqfr/aqfr.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/aquiferType/aquiferType.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/bodyPart/bodyPart.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/county/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/dataReliability/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/fxd/fxd.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/gwLevelAccuracy/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/gwLevelDateTimeAccuracy/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/gwLevelMethod/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testResult/nwis/gwLevels/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/gwLevelSitestatus/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/gwLevelSource/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/huc/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/hydCondCd/hydCondCd.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/hydEventCd/hydEventCd.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/latLongAccuracy/latLongAccuracy.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/latLongDatum/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/latLongMethod/latLongMethod.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/methWithCit/methWithCit.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/monitoringLocation/purge/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/natAqfr/natAqfr.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/nwisDistrictCdsByHost/nwisDistrictCdsByHost.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/nwisWqxMediumCd/nwisWqxMediumCd.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/nwisWqxRptLevCd/nwisWqxRptLevCd.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/parm/parm.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/parmAlias/parmAlias.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/parmMeth/parmMeth.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/qwResult/qwResult.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/qwSample/qwSample.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/sitefile/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/siteTp/siteTp.xml")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/state/")
	@DatabaseSetup(connection = CONNECTION_NWIS, value = "classpath:/testData/nwis/topographicSetting/")
	@DatabaseSetup(value = "classpath:/testData/wqp/huc12nometa/")
	@DatabaseSetup(value="classpath:/testData/wqp/qwportalSummary/")

	//Tables
	@ExpectedDatabase(
			connection=CONNECTION_INFORMATION_SCHEMA,
			value="classpath:/testResult/endToEnd/installTables/",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=EXPECTED_DATABASE_QUERY_TABLE)

	//Indexes
	@ExpectedDatabase(
			connection = CONNECTION_INFORMATION_SCHEMA,
			value = "classpath:/testResult/endToEnd/installIndexes/",
			assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table = EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query = EXPECTED_DATABASE_QUERY_INDEX)

	//Analyzed
	@ExpectedDatabase(
			value = "classpath:/testResult/endToEnd/installAnalyzed/",
			assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table = EXPECTED_DATABASE_TABLE_CHECK_ANALYZE,
			query = EXPECTED_DATABASE_QUERY_ANALYZE)

	//Primary Keys
	@ExpectedDatabase(
			value = "classpath:/testResult/endToEnd/primaryKey/",
			assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table = EXPECTED_DATABASE_TABLE_CHECK_PRIMARY_KEY,
			query = EXPECTED_DATABASE_QUERY_PRIMARY_KEY)

	//Foreign Keys
	@ExpectedDatabase(
			value = "classpath:/testResult/endToEnd/foreignKey/",
			assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table = EXPECTED_DATABASE_TABLE_CHECK_FOREIGN_KEY,
			query = EXPECTED_DATABASE_QUERY_FOREIGN_KEY)

	// NWIS base data
	@ExpectedDatabase(connection = CONNECTION_NWIS, value = "classpath:/testResult/endToEnd/sampleParameter.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/orgData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/projectData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/monitoringLocation.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/biologicalHabitatMetric.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/activity.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/activityMetric.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/result.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/resDetectQntLimit.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/projectMLWeighting.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/wqp/publicSrsnames/publicSrsnames.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)

	//Summaries Data
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/activitySum.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/resultSum.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/orgGrouping.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/mlGrouping.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/organizationSum.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/qwportalSummary.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)

	//Codes Data
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/assemblage.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/characteristicName.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/characteristicType.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/country.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/county.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/monitoringLoc.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/organization.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/project.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/projectDim.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/sampleMedia.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/siteType.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/state.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value = "classpath:/testResult/endToEnd/taxaName.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)

	@ExpectedDatabase(
			value = "classpath:/testResult/endToEnd/lastEtl.xml",
			assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table = UpdateLastETLIT.TABLE_NAME_LAST_ETL,
			query = UpdateLastETLIT.EXPECTED_DATABASE_QUERY_LAST_ETL)
	public void nwisToWqpFlowTest() {
		Job nwisToWqpEtlFlowTest = jobBuilderFactory.get("nwisToWqpEtlFlowTest")
				.start(nwisToWqpFlow)
				.build()
				.build();
		jobLauncherTestUtils.setJob(nwisToWqpEtlFlowTest);
		try {
			//Manually bump up identity past loaded test data. DBUnit @DatabaseSetup will not accomplish this.
			jdbcTemplateNwis.execute("alter table nwis.monitoring_location alter monitoring_location_id restart with 100");
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			Thread.sleep(1000);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
