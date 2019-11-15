package gov.acwi.wqp.etl.mysqlnwis.sitefile;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SitefileRowMapper implements RowMapper<Sitefile> {

	public static final String SITE_ID_COLUMN_NAME = "site_id";
	public static final String AGENCY_CD_COLUMN_NAME = "agency_cd";
	public static final String SITE_NO_COLUMN_NAME = "site_no"; 
	public static final String NWIS_HOST_COLUMN_NAME = "nwis_host";
	public static final String DB_NO_COLUMN_NAME = "db_no"; 
	public static final String STATION_NM_COLUMN_NAME = "station_nm";
	public static final String DEC_LAT_VA_COLUMN_NAME = "dec_lat_va";
	public static final String DEC_LONG_VA_COLUMN_NAME = "dec_long_va"; 
	public static final String COORD_METH_CD_COLUMN_NAME = "coord_meth_cd"; 
	public static final String COORD_ACY_CD_COLUMN_NAME = "coord_acy_cd";
	public static final String DISTRICT_CD_COLUMN_NAME = "district_cd"; 
	public static final String COUNTRY_CD_COLUMN_NAME = "country_cd";
	public static final String STATE_CD_COLUMN_NAME = "state_cd";
	public static final String COUNTY_CD_COLUMN_NAME = "county_cd";
	public static final String LAND_NET_DS_COLUMN_NAME = "land_net_ds";
	public static final String MAP_SCALE_FC_COLUMN_NAME = "map_scale_fc";
	public static final String ALT_VA_COLUMN_NAME = "alt_va";
	public static final String ALT_METH_CD_COLUMN_NAME = "alt_meth_cd";
	public static final String ALT_ACY_VA_COLUMN_NAME = "alt_acy_va";
	public static final String ALT_DATUM_CD_COLUMN_NAME = "alt_datum_cd";
	public static final String HUC_CD_COLUMN_NAME = "huc_cd";
	public static final String BASIN_CD_COLUMN_NAME = "basin_cd";
	public static final String SITE_TP_CD_COLUMN_NAME = "site_tp_cd";
	public static final String SITE_RMKS_TX_COLUMN_NAME = "site_rmks_tx"; 
	public static final String DRAIN_AREA_VA_COLUMN_NAME = "drain_area_va";
	public static final String CONTRIB_DRAIN_AREA_VA_COLUMN_NAME = "contrib_drain_area_va";
	public static final String CONSTRUCTION_DT_COLUMN_NAME = "construction_dt"; 
	public static final String AQFR_TYPE_CD_COLUMN_NAME = "aqfr_type_cd"; 
	public static final String AQFR_CD_COLUMN_NAME = "aqfr_cd";
	public static final String NAT_AQFR_CD_COLUMN_NAME = "nat_aqfr_cd";
	public static final String WELL_DEPTH_VA_COLUMN_NAME = "well_depth_va";
	public static final String HOLE_DEPTH_VA_COLUMN_NAME = "hole_depth_va";
	public static final String SITE_WEB_CD_COLUMN_NAME = "site_web_cd"; 
	public static final String DEC_COORD_DATUM_CD_COLUMN_NAME = "dec_coord_datum_cd";
	public static final String SITE_CN_COLUMN_NAME = "site_cn";
	public static final String SITE_CR_COLUMN_NAME = "site_cr"; 
	public static final String SITE_MN_COLUMN_NAME = "site_mn"; 
	public static final String SITE_MD_COLUMN_NAME = "site_md";
	public static final String LAT_VA_COLUMN_NAME = "lat_va";
	public static final String LONG_VA_COLUMN_NAME = "long_va";
	public static final String COORD_DATUM_CD_COLUMN_NAME = "coord_datum_cd";
	public static final String MAP_NM_COLUMN_NAME = "map_nm";
	public static final String TOPO_CD_COLUMN_NAME = "topo_cd";
	public static final String INSTRUMENTS_CD_COLUMN_NAME = "instruments_cd";
	public static final String INVENTORY_DT_COLUMN_NAME = "inventory_dt";
	public static final String TZ_CD_COLUMN_NAME = "tz_cd";
	public static final String LOCAL_TIME_FG_COLUMN_NAME = "local_time_fg";
	public static final String RELIABILITY_CD_COLUMN_NAME = "reliability_cd";
	public static final String GW_FILE_CD_COLUMN_NAME = "gw_file_cd";
	public static final String DEPTH_SRC_CD_COLUMN_NAME = "depth_src_cd";
	public static final String PROJECT_NO_COLUMN_NAME = "project_no";

	public Sitefile mapRow(ResultSet rs, int rowNum) throws SQLException {
		Sitefile sitefile = new Sitefile();

		sitefile.setSiteId(rs.getInt(SITE_ID_COLUMN_NAME));
		sitefile.setAgencyCd(rs.getString(AGENCY_CD_COLUMN_NAME));
		sitefile.setSiteNo(rs.getString(SITE_NO_COLUMN_NAME));
		sitefile.setNwisHost(rs.getString(NWIS_HOST_COLUMN_NAME));
		sitefile.setDbNo(rs.getString(DB_NO_COLUMN_NAME));
		sitefile.setStationNm(rs.getString(STATION_NM_COLUMN_NAME));
		sitefile.setDecLatVa(rs.getBigDecimal(DEC_LAT_VA_COLUMN_NAME));
		sitefile.setDecLongVa(rs.getBigDecimal(DEC_LONG_VA_COLUMN_NAME));
		sitefile.setCoordMethCd(rs.getString(COORD_METH_CD_COLUMN_NAME));
		sitefile.setCoordAcyCd(rs.getString(COORD_ACY_CD_COLUMN_NAME));
		sitefile.setDistrictCd(rs.getString(DISTRICT_CD_COLUMN_NAME));
		sitefile.setCountryCd(rs.getString(COUNTRY_CD_COLUMN_NAME));
		sitefile.setStateCd(rs.getString(STATE_CD_COLUMN_NAME));
		sitefile.setCountyCd(rs.getString(COUNTY_CD_COLUMN_NAME));
		sitefile.setLandNetDs(rs.getString(LAND_NET_DS_COLUMN_NAME));
		sitefile.setMapScaleFc(rs.getString(MAP_SCALE_FC_COLUMN_NAME));
		sitefile.setAltVa(rs.getString(ALT_VA_COLUMN_NAME));
		sitefile.setAltMethCd(rs.getString(ALT_METH_CD_COLUMN_NAME));
		sitefile.setAltAcyVa(rs.getString(ALT_ACY_VA_COLUMN_NAME));
		sitefile.setAltDatumCd(rs.getString(ALT_DATUM_CD_COLUMN_NAME));
		sitefile.setHucCd(rs.getString(HUC_CD_COLUMN_NAME));
		sitefile.setBasinCd(rs.getString(BASIN_CD_COLUMN_NAME));
		sitefile.setSiteTpCd(rs.getString(SITE_TP_CD_COLUMN_NAME));
		sitefile.setSiteRmksTx(rs.getString(SITE_RMKS_TX_COLUMN_NAME));
		sitefile.setDrainAreaVa(rs.getString(DRAIN_AREA_VA_COLUMN_NAME));
		sitefile.setContribDrainAreaVa(rs.getString(CONTRIB_DRAIN_AREA_VA_COLUMN_NAME));
		sitefile.setConstructionDt(rs.getString(CONSTRUCTION_DT_COLUMN_NAME));
		sitefile.setAqfrTypeCd(rs.getString(AQFR_TYPE_CD_COLUMN_NAME));
		sitefile.setAqfrCd(rs.getString(AQFR_CD_COLUMN_NAME));
		sitefile.setNatAqfrCd(rs.getString(NAT_AQFR_CD_COLUMN_NAME));
		sitefile.setWellDepthVa(rs.getString(WELL_DEPTH_VA_COLUMN_NAME));
		sitefile.setHoleDepthVa(rs.getString(HOLE_DEPTH_VA_COLUMN_NAME));
		sitefile.setSiteWebCd(rs.getString(SITE_WEB_CD_COLUMN_NAME));
		sitefile.setDecCoordDatumCd(rs.getString(DEC_COORD_DATUM_CD_COLUMN_NAME));
		sitefile.setSiteCn(rs.getString(SITE_CN_COLUMN_NAME));
		sitefile.setSiteCr(rs.getDate(SITE_CR_COLUMN_NAME));
		sitefile.setSiteMn(rs.getString(SITE_MN_COLUMN_NAME));
		sitefile.setSiteMd(rs.getDate(SITE_MD_COLUMN_NAME));

		sitefile.setLatVa(rs.getString(LAT_VA_COLUMN_NAME));
		sitefile.setLongVa(rs.getString(LONG_VA_COLUMN_NAME));
		sitefile.setCoordDatumCd(rs.getString(COORD_DATUM_CD_COLUMN_NAME));
		sitefile.setMapNm(rs.getString(MAP_NM_COLUMN_NAME));
		sitefile.setTopoCd(rs.getString(TOPO_CD_COLUMN_NAME));
		sitefile.setInstrumentsCd(rs.getString(INSTRUMENTS_CD_COLUMN_NAME));
		sitefile.setInventoryDt(rs.getString(INVENTORY_DT_COLUMN_NAME));
		sitefile.setTzCd(rs.getString(TZ_CD_COLUMN_NAME));
		sitefile.setLocalTimeFg(rs.getString(LOCAL_TIME_FG_COLUMN_NAME));
		sitefile.setReliabilityCd(rs.getString(RELIABILITY_CD_COLUMN_NAME));
		sitefile.setGwFileCd(rs.getString(GW_FILE_CD_COLUMN_NAME));
		sitefile.setDepthSrcCd(rs.getString(DEPTH_SRC_CD_COLUMN_NAME));
		sitefile.setProjectNo(rs.getString(PROJECT_NO_COLUMN_NAME));

		return sitefile;
	}
}
