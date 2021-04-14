package gov.acwi.wqp.etl.mysqlnwis;

import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySqlNwisExtract {

	@Autowired
	@Qualifier("qwSampleFlow")
	private Flow qwSampleFlow;

	@Autowired
	@Qualifier("qwResultFlow")
	private Flow qwResultFlow;

	@Autowired
	@Qualifier("sitefileFlow")
	private Flow sitefileFlow;

	@Autowired
	@Qualifier("statFlow")
	private Flow statFlow;

	@Bean
	public Flow mySqlNwisExtractFlow() {
		return new FlowBuilder<SimpleFlow>("mySqlNwisExtractFlow")
				.start(qwSampleFlow)
				.next(qwResultFlow)
				.next(sitefileFlow)
				.next(statFlow)
				.build();
	}
}
