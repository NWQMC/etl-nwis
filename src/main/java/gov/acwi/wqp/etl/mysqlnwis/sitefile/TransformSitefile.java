package gov.acwi.wqp.etl.mysqlnwis.sitefile;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import gov.acwi.wqp.etl.Application;

@Component
public class TransformSitefile {

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier(Application.DATASOURCE_MYSQLNWIS_QUALIFER)
	private DataSource dataSourceMysqlnwis;

	@Autowired
	@Qualifier(Application.DATASOURCE_NWIS_QUALIFIER)
	private DataSource dataSourceNwis;

	@Bean
	public JdbcPagingItemReader<Sitefile> sitefileReader() {
		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		Map<String, Order> sortKeys = new HashMap<>(1);

		sortKeys.put("site_id", Order.ASCENDING);
		queryProvider.setSelectClause("select *");
		queryProvider.setFromClause("from SITEFILE");
		queryProvider.setSortKeys(sortKeys);

		return new JdbcPagingItemReaderBuilder<Sitefile>()
				.dataSource(dataSourceMysqlnwis)
				.name("mysqlSitefileReader")
				.pageSize(5000)
				.queryProvider(queryProvider)
				.rowMapper(new SitefileRowMapper())
				.build();
	}

	@Bean
	public PassThroughItemProcessor<Sitefile> sitefileProcessor() {
		return new PassThroughItemProcessor<>();
	}

	@Bean
	public JdbcBatchItemWriter<Sitefile> sitefileWriter() {
		return new JdbcBatchItemWriterBuilder<Sitefile>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("insert into sitefile ("
						+ "site_id, agency_cd, site_no, nwis_host, db_no, station_nm, "
						+ "dec_lat_va, dec_long_va, coord_meth_cd, coord_acy_cd, district_cd, "
						+ "country_cd, state_cd, county_cd, land_net_ds, map_scale_fc, "
						+ "alt_va, alt_meth_cd, alt_acy_va, alt_datum_cd, huc_cd, basin_cd, "
						+ "site_tp_cd, site_rmks_tx, drain_area_va, contrib_drain_area_va, "
						+ "construction_dt, aqfr_type_cd, aqfr_cd, nat_aqfr_cd, well_depth_va, "
						+ "hole_depth_va, site_web_cd, dec_coord_datum_cd, site_cn, "
						+ "site_cr, site_mn, site_md, "
						+ "lat_va, long_va, coord_datum_cd, map_nm, topo_cd, instruments_cd, "
						+ "inventory_dt, tz_cd, local_time_fg, reliability_cd, "
						+ "gw_file_cd, depth_src_cd, project_no, site_active) VALUES ("
						+ ":siteId, :agencyCd, :siteNo, :nwisHost, :dbNo, :stationNm, "
						+ ":decLatVa, :decLongVa, :coordMethCd, :coordAcyCd, :districtCd, "
						+ ":countryCd, :stateCd, :countyCd, :landNetDs, :mapScaleFc, "
						+ ":altVa, :altMethCd, :altAcyVa, :altDatumCd, :hucCd, :basinCd, "
						+ ":siteTpCd, :siteRmksTx, :drainAreaVa, :contribDrainAreaVa, "
						+ ":constructionDt, :aqfrTypeCd, :aqfrCd, :natAqfrCd, :wellDepthVa, "
						+ ":holeDepthVa, :siteWebCd, :decCoordDatumCd, :siteCn, "
						+ ":siteCr, :siteMn, :siteMd, "
						+ ":latVa, :longVa, :coordDatumCd, :mapNm, :topoCd, :instrumentsCd,"
						+ ":inventoryDt, :tzCd, :localTimeFg, :reliabilityCd, "
						+ ":gwFileCd, :depthSrcCd, :projectNo, :siteActiveFg)")
				.dataSource(dataSourceNwis)
				.build();
	}
}
