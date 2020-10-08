package gov.acwi.wqp.etl.mysqlnwis.sitefile;

import java.util.Date;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public class Sitefile {

	private int siteId;
	private String agencyCd;
	private String siteNo;
	private String nwisHost;
	private String dbNo;
	private String stationNm;
	private BigDecimal decLatVa;
	private BigDecimal decLongVa ;
	private String coordMethCd;
	private String coordAcyCd;
	private String districtCd;
	private String countryCd ;
	private String stateCd;
	private String countyCd;
	private String landNetDs;
	private String mapScaleFc;
	private String altVa;
	private String altMethCd;
	private String altAcyVa;
	private String altDatumCd;
	private String hucCd;
	private String basinCd;
	private String siteTpCd;
	private String siteRmksTx;
	private String drainAreaVa;
	private String contribDrainAreaVa;
	private String constructionDt;
	private String aqfrTypeCd;
	private String aqfrCd;
	private String natAqfrCd;
	private String wellDepthVa;
	private String holeDepthVa;
	private String siteWebCd;
	private String decCoordDatumCd ;
	private String siteCn;
	private Date siteCr;
	private String siteMn;
	private Date siteMd;
	private String latVa;
	private String longVa;
	private String coordDatumCd;
	private String mapNm;
	private String topoCd;
	private String instrumentsCd;
	private String inventoryDt;
	private String tzCd;
	private String localTimeFg;
	private String reliabilityCd;
	private String gwFileCd;
	private String depthSrcCd;
	private String projectNo;
	private String siteActiveFg;
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getAgencyCd() {
		return StringUtils.trimToNull(agencyCd);
	}
	public void setAgencyCd(String agencyCd) {
		this.agencyCd = agencyCd;
	}
	public String getSiteNo() {
		return StringUtils.trimToNull(siteNo);
	}
	public void setSiteNo(String siteNo) {
		this.siteNo = siteNo;
	}
	public String getNwisHost() {
		return StringUtils.trimToNull(nwisHost);
	}
	public void setNwisHost(String nwisHost) {
		this.nwisHost = nwisHost;
	}
	public String getDbNo() {
		return StringUtils.trimToNull(dbNo);
	}
	public void setDbNo(String dbNo) {
		this.dbNo = dbNo;
	}
	public String getStationNm() {
		return StringUtils.trimToNull(stationNm);
	}
	public void setStationNm(String stationNm) {
		this.stationNm = stationNm;
	}
	public BigDecimal getDecLatVa() {
		return decLatVa;
	}
	public void setDecLatVa(BigDecimal decLatVa) {
		this.decLatVa = decLatVa;
	}
	public BigDecimal getDecLongVa() {
		return decLongVa;
	}
	public void setDecLongVa(BigDecimal decLongVa) {
		this.decLongVa = decLongVa;
	}
	public String getCoordMethCd() {
		return StringUtils.trimToNull(coordMethCd);
	}
	public void setCoordMethCd(String coordMethCd) {
		this.coordMethCd = coordMethCd;
	}
	public String getCoordAcyCd() {
		return StringUtils.trimToNull(coordAcyCd);
	}
	public void setCoordAcyCd(String coordAcyCd) {
		this.coordAcyCd = coordAcyCd;
	}
	public String getDistrictCd() {
		return StringUtils.trimToNull(districtCd);
	}
	public void setDistrictCd(String districtCd) {
		this.districtCd = districtCd;
	}
	public String getCountryCd() {
		return StringUtils.trimToNull(countryCd);
	}
	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}
	public String getStateCd() {
		return StringUtils.trimToNull(stateCd);
	}
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}
	public String getCountyCd() {
		return StringUtils.trimToNull(countyCd);
	}
	public void setCountyCd(String countyCd) {
		this.countyCd = countyCd;
	}
	public String getLandNetDs() {
		return StringUtils.trimToNull(landNetDs);
	}
	public void setLandNetDs(String landNetDs) {
		this.landNetDs = landNetDs;
	}
	public String getMapScaleFc() {
		return StringUtils.trimToNull(mapScaleFc);
	}
	public void setMapScaleFc(String mapScaleFc) {
		this.mapScaleFc = mapScaleFc;
	}
	public String getAltVa() {
		return StringUtils.trimToNull(altVa);
	}
	public void setAltVa(String altVa) {
		this.altVa = altVa;
	}
	public String getAltMethCd() {
		return StringUtils.trimToNull(altMethCd);
	}
	public void setAltMethCd(String altMethCd) {
		this.altMethCd = altMethCd;
	}
	public String getAltAcyVa() {
		return StringUtils.trimToNull(altAcyVa);
	}
	public void setAltAcyVa(String altAcyVa) {
		this.altAcyVa = altAcyVa;
	}
	public String getAltDatumCd() {
		return StringUtils.trimToNull(altDatumCd);
	}
	public void setAltDatumCd(String altDatumCd) {
		this.altDatumCd = altDatumCd;
	}
	public String getHucCd() {
		return StringUtils.trimToNull(hucCd);
	}
	public void setHucCd(String huc_Cd) {
		this.hucCd = huc_Cd;
	}
	public String getBasinCd() {
		return StringUtils.trimToNull(basinCd);
	}
	public void setBasinCd(String basinCd) {
		this.basinCd = basinCd;
	}
	public String getSiteTpCd() {
		return StringUtils.trimToNull(siteTpCd);
	}
	public void setSiteTpCd(String siteTpCd) {
		this.siteTpCd = siteTpCd;
	}
	public String getSiteRmksTx() {
		return StringUtils.trimToNull(siteRmksTx);
	}
	public void setSiteRmksTx(String siteRmksTx) {
		this.siteRmksTx = siteRmksTx;
	}
	public String getDrainAreaVa() {
		return StringUtils.trimToNull(drainAreaVa);
	}
	public void setDrainAreaVa(String drainAreaVa) {
		this.drainAreaVa = drainAreaVa;
	}
	public String getContribDrainAreaVa() {
		return StringUtils.trimToNull(contribDrainAreaVa);
	}
	public void setContribDrainAreaVa(String contribDrainAreaVa) {
		this.contribDrainAreaVa = contribDrainAreaVa;
	}
	public String getConstructionDt() {
		return StringUtils.trimToNull(constructionDt);
	}
	public void setConstructionDt(String constructionDt) {
		this.constructionDt = constructionDt;
	}
	public String getAqfrTypeCd() {
		return StringUtils.trimToNull(aqfrTypeCd);
	}
	public void setAqfrTypeCd(String aqfrTypeCd) {
		this.aqfrTypeCd = aqfrTypeCd;
	}
	public String getAqfrCd() {
		return StringUtils.trimToNull(aqfrCd);
	}
	public void setAqfrCd(String aqfrCd) {
		this.aqfrCd = aqfrCd;
	}
	public String getNatAqfrCd() {
		return StringUtils.trimToNull(natAqfrCd);
	}
	public void setNatAqfrCd(String natAqfrCd) {
		this.natAqfrCd = natAqfrCd;
	}
	public String getWellDepthVa() {
		return StringUtils.trimToNull(wellDepthVa);
	}
	public void setWellDepthVa(String wellDepthVa) {
		this.wellDepthVa = wellDepthVa;
	}
	public String getHoleDepthVa() {
		return StringUtils.trimToNull(holeDepthVa);
	}
	public void setHoleDepthVa(String holeDepthVa) {
		this.holeDepthVa = holeDepthVa;
	}
	public String getSiteWebCd() {
		return StringUtils.trimToNull(siteWebCd);
	}
	public void setSiteWebCd(String siteWebCd) {
		this.siteWebCd = siteWebCd;
	}
	public String getDecCoordDatumCd() {
		return StringUtils.trimToNull(decCoordDatumCd);
	}
	public void setDecCoordDatumCd(String decCoordDatumCd) {
		this.decCoordDatumCd = decCoordDatumCd;
	}
	public String getSiteCn() {
		return StringUtils.trimToNull(siteCn);
	}
	public void setSiteCn(String siteCn) {
		this.siteCn = siteCn;
	}
	public Date getSiteCr() {
		return siteCr;
	}
	public void setSiteCr(Date siteCr) {
		this.siteCr = siteCr;
	}
	public String getSiteMn() {
		return StringUtils.trimToNull(siteMn);
	}
	public void setSiteMn(String siteMn) {
		this.siteMn = siteMn;
	}
	public Date getSiteMd() {
		return siteMd;
	}
	public void setSiteMd(Date siteMd) {
		this.siteMd = siteMd;
	}
	public String getLatVa() {
		return StringUtils.trimToNull(latVa);
	}
	public void setLatVa(String latVa) {
		this.latVa = latVa;
	}
	public String getLongVa() {
		return StringUtils.trimToNull(longVa);
	}
	public void setLongVa(String longVa) {
		this.longVa = longVa;
	}
	public String getCoordDatumCd() {
		return StringUtils.trimToNull(coordDatumCd);
	}
	public void setCoordDatumCd(String coordDatumCd) {
		this.coordDatumCd = coordDatumCd;
	}
	public String getMapNm() {
		return StringUtils.trimToNull(mapNm);
	}
	public void setMapNm(String mapNm) {
		this.mapNm = mapNm;
	}
	public String getTopoCd() {
		return StringUtils.trimToNull(topoCd);
	}
	public void setTopoCd(String topoCd) {
		this.topoCd = topoCd;
	}
	public String getInstrumentsCd() {
		return StringUtils.trimToNull(instrumentsCd);
	}
	public void setInstrumentsCd(String instrumentsCd) {
		this.instrumentsCd = instrumentsCd;
	}
	public String getInventoryDt() {
		return StringUtils.trimToNull(inventoryDt);
	}
	public void setInventoryDt(String inventoryDt) {
		this.inventoryDt = inventoryDt;
	}
	public String getTzCd() {
		return StringUtils.trimToNull(tzCd);
	}
	public void setTzCd(String tzCd) {
		this.tzCd = tzCd;
	}
	public String getLocalTimeFg() {
		return StringUtils.trimToNull(localTimeFg);
	}
	public void setLocalTimeFg(String localTimeFg) {
		this.localTimeFg = localTimeFg;
	}
	public String getReliabilityCd() {
		return StringUtils.trimToNull(reliabilityCd);
	}
	public void setReliabilityCd(String reliabilityCd) {
		this.reliabilityCd = reliabilityCd;
	}
	public String getGwFileCd() {
		return StringUtils.trimToNull(gwFileCd);
	}
	public void setGwFileCd(String gwFileCd) {
		this.gwFileCd = gwFileCd;
	}
	public String getDepthSrcCd() {
		return StringUtils.trimToNull(depthSrcCd);
	}
	public void setDepthSrcCd(String depthSrcCd) {
		this.depthSrcCd = depthSrcCd;
	}
	public String getProjectNo() {
		return StringUtils.trimToNull(projectNo);
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getSiteActiveFg() {
		return siteActiveFg;
	}
	public void setSiteActiveFg(String siteActiveFg) {
		this.siteActiveFg = siteActiveFg;
	}
}
