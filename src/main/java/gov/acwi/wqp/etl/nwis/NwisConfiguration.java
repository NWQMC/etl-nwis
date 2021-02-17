package gov.acwi.wqp.etl.nwis;

import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NwisConfiguration {

	@Autowired
	@Qualifier("sampleParameterFlow")
	private Flow sampleParameterFlow;

	@Autowired
	@Qualifier("nwisMonitoringLocationFlow")
	private Flow nwisMonitoringLocationFlow;

	@Bean
	public Flow nwisFlow() {
		return new FlowBuilder<SimpleFlow>("nwisFlow")
				.start(sampleParameterFlow)
				.next(nwisMonitoringLocationFlow)
				.build();
	}
}
