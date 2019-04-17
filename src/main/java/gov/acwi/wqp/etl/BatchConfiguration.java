package gov.acwi.wqp.etl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	@Qualifier("mySqlNwisExtractFlow")
	private Flow mySqlNwisExtractFlow;
	
	@Autowired
	@Qualifier("orgDataFlow")
	private Flow orgDataFlow;
	
	@Autowired
	@Qualifier("upsertNwisStationLocalFlow")
	private Flow upsertNwisStationLocalFlow;
	
	@Autowired
	@Qualifier("monitoringLocationFlow")
	private Flow monitoringLocationFlow;
	
	@Autowired
	@Qualifier("activityFlow")
	private Flow activityFlow;
	
//	@Autowired
//	@Qualifier("resultFlow")
//	private Flow resultFlow;


	@Bean
	public Job nwisEtl() {
		return jobBuilderFactory.get("WQP_NWIS_ETL")
//				.start(mySqlNwisExtractFlow)
				.start(orgDataFlow)
				.next(upsertNwisStationLocalFlow)
				.next(monitoringLocationFlow)
				.next(activityFlow)
				//.next(resultFlow)
				.build()
				.build();
	}
		
}
