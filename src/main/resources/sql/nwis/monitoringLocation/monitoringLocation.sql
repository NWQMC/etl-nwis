with sitefile as (select sitefile.site_no site_identification_number,
                         sitefile.station_nm site_name,
                         sitefile.lat_va dms_latitude,
                         sitefile.long_va dms_longitude,
                         sitefile.dec_lat_va decimal_latitude,
                         sitefile.dec_long_va decimal_longitude,
                         sitefile.country_cd country,
                         sitefile.land_net_ds land_net_location_description,
                         sitefile.map_nm name_of_location_map,
                         sitefile.map_scale_fc scale_of_location_map,
                         case
                             when sitefile.alt_datum_cd is not null then
                                 case
                                     when sitefile.alt_va = '.' then '0'
                                     else sitefile.alt_va
                                 end
                             else null
                         end altitude_of_guage_land_surface,
                         case
                             when sitefile.alt_va is not null and 
                                  sitefile.alt_datum_cd is not null
                                  then sitefile.alt_acy_va
                             else null
                         end altitude_accuracy,
                         sitefile.basin_cd drainage_basin,
                         sitefile.instruments_cd flags_for_instruments_at_site,
                         sitefile.construction_dt date_of_first_construction,
                         sitefile.inventory_dt date_site_established_or_inventoried,
                         sitefile.drain_area_va drainage_area,
                         case
                             when sitefile.contrib_drain_area_va = '.' then '0'
                             else sitefile.contrib_drain_area_va
                         end contributing_drainage_area,
                         sitefile.tz_cd time_zone_abbreviation,
                         sitefile.local_time_fg site_honors_daylight_savings_time,
                         sitefile.gw_file_cd data_other_gw_files,
                         case when sitefile.well_depth_va in ('.', '-') then '0' else sitefile.well_depth_va end well_depth,
                         case when sitefile.hole_depth_va in ('.', '-') then '0' else sitefile.hole_depth_va end hole_depth,
                         sitefile.depth_src_cd source_of_hole_depth,
                         sitefile.project_no project_numer,
                         sitefile.site_id,
                         sitefile.agency_cd,
                         sitefile.site_tp_cd,
                         sitefile.coord_meth_cd,
                         sitefile.coord_acy_cd,
                         sitefile.coord_datum_cd,
                         sitefile.dec_coord_datum_cd,
                         sitefile.district_cd,
                         sitefile.state_cd,
                         sitefile.county_cd,
                         sitefile.country_cd,
                         sitefile.alt_meth_cd,
                         case
                             when sitefile.alt_va is not null then sitefile.alt_datum_cd
                             else null
                         end alt_datum_cd,
                         sitefile.huc_cd,
                         sitefile.basin_cd,
                         sitefile.topo_cd,
                         sitefile.tz_cd,
                         sitefile.local_time_fg,
                         sitefile.reliability_cd,
                         sitefile.gw_file_cd,
                         sitefile.nat_aqfr_cd,
                         sitefile.aqfr_cd,
                         sitefile.aqfr_type_cd,
                         sitefile.depth_src_cd,
                         case
                             when sitefile.dec_long_va <> 0 and sitefile.dec_lat_va  <> 0
                                 then st_SetSrid(st_MakePoint(dec_long_va, dec_lat_va), 4269)
                             else dms_to_geometry(long_va, lat_va)
                         end geom,
                         sitefile.nwis_host,
                         sitefile.db_no,
                         sitefile.site_web_cd,
                         coalesce(sitefile.agency_cd, '') || '-' || coalesce(sitefile.site_no, '') monitoring_location_identifier
                    from nwis.sitefile
                   where sitefile.site_web_cd = 'Y' and
                         sitefile.db_no = '01' and
                         sitefile.nwis_host not in ('fltlhsr001', 'fltpasr001', 'flalssr003') and
                         sitefile.country_cd != 'CN'
                 )
insert
  into nwis.monitoring_location (agency,
                                 site_identification_number,
                                 site_name,
                                 site_type,
                                 dms_latitude,
                                 dms_longitude,
                                 decimal_latitude,
                                 decimal_longitude,
                                 latitude_longitude_method,
                                 latitude_longitude_accuracy,
                                 latitude_longitude_accuracy_value,
                                 latitude_longitude_accuracy_unit,
                                 latitude_longitude_datum,
                                 decimal_latitude_longitude_datum,
                                 district,
                                 state,
                                 county,
                                 country,
                                 land_net_location_description,
                                 name_of_location_map,
                                 scale_of_location_map,
                                 altitude_of_guage_land_surface,
                                 method_altitude_determined,
                                 altitude_accuracy,
                                 altitude_datum,
                                 subbasin_hydrologic_unit,
                                 drainage_basin,
                                 topographic_setting,
                                 flags_for_instruments_at_site,
                                 date_of_first_construction,
                                 date_site_established_or_inventoried,
                                 drainage_area,
                                 contributing_drainage_area,
                                 time_zone_abbreviation,
                                 site_honors_daylight_savings_time,
                                 data_reliability,
                                 data_other_gw_files,
                                 national_aquifer,
                                 local_aquifer,
                                 local_aquifer_type,
                                 well_depth,
                                 hole_depth,
                                 source_of_hole_depth,
                                 project_numer,
                                 site_id,
                                 agency_cd,
                                 site_tp_cd,
                                 coord_meth_cd,
                                 coord_acy_cd,
                                 coord_datum_cd,
                                 dec_coord_datum_cd,
                                 district_cd,
                                 state_cd,
                                 county_cd,
                                 country_cd,
                                 alt_meth_cd,
                                 alt_datum_cd,
                                 huc_cd,
                                 basin_cd,
                                 topo_cd,
                                 tz_cd,
                                 local_time_fg,
                                 reliability_cd,
                                 gw_file_cd,
                                 nat_aqfr_cd,
                                 aqfr_cd,
                                 aqfr_type_cd,
                                 depth_src_cd,
                                 geom,
                                 calculated_huc_12,
                                 nwis_host,
                                 db_no,
                                 site_web_cd,
                                 monitoring_location_identifier)
select agency.name agency,
       sitefile.site_identification_number,
       sitefile.site_name,
       site_tp.site_tp_nm site_type,
       sitefile.dms_latitude,
       sitefile.dms_longitude,
       sitefile.decimal_latitude,
       sitefile.decimal_longitude,
       coalesce(lat_long_method.description, 'Unknown') latitude_longitude_method,
       lat_long_accuracy.description latitude_longitude_accuracy,
       lat_long_accuracy.accuracy latitude_longitude_accuracy_value,
       lat_long_accuracy.unit latitude_longitude_accuracy_unit,
       coalesce(lat_long_datum.description, 'Unknown') latitude_longitude_datum,
       decimal_lat_long_datum.description decimal_latitude_longitude_datum,
       district.state_nm district,
       state.state_nm state,
       county.county_nm county,
       sitefile.country,
       sitefile.land_net_location_description,
       sitefile.name_of_location_map,
       sitefile.scale_of_location_map,
       sitefile.altitude_of_guage_land_surface,
       altitude_method.description method_altitude_determined,
       sitefile.altitude_accuracy,
       altitude_datum.description altitude_datum,
       huc.name subbasin_hydrologic_unit,
       sitefile.drainage_basin,
       topographic_setting.name topographic_setting,
       sitefile.flags_for_instruments_at_site,
       sitefile.date_of_first_construction,
       sitefile.date_site_established_or_inventoried,
       sitefile.drainage_area,
       sitefile.contributing_drainage_area,
       sitefile.time_zone_abbreviation,
       sitefile.site_honors_daylight_savings_time,
       data_reliability.description data_reliability,
       sitefile.data_other_gw_files,
       nat_aqfr.nat_aqfr_nm national_aquifer,
       aqfr.aqfr_nm local_aquifer,
       aquifer_type.description local_aquifer_type,
       sitefile.well_depth,
       sitefile.hole_depth,
       sitefile.source_of_hole_depth,
       sitefile.project_numer,
       sitefile.site_id,
       sitefile.agency_cd,
       sitefile.site_tp_cd,
       sitefile.coord_meth_cd,
       sitefile.coord_acy_cd,
       sitefile.coord_datum_cd,
       sitefile.dec_coord_datum_cd,
       sitefile.district_cd,
       sitefile.state_cd,
       sitefile.county_cd,
       sitefile.country_cd,
       sitefile.alt_meth_cd,
       sitefile.alt_datum_cd,
       sitefile.huc_cd,
       sitefile.basin_cd,
       sitefile.topo_cd,
       sitefile.tz_cd,
       sitefile.local_time_fg,
       sitefile.reliability_cd,
       sitefile.gw_file_cd,
       sitefile.nat_aqfr_cd,
       sitefile.aqfr_cd,
       sitefile.aqfr_type_cd,
       sitefile.depth_src_cd,
       sitefile.geom,
       huc12nometa.huc12 calculated_huc_12,
       sitefile.nwis_host,
       sitefile.db_no,
       sitefile.site_web_cd,
       sitefile.monitoring_location_identifier
  from sitefile
       left join nwis.agency
         on sitefile.agency_cd = agency.code
       left join nwis.altitude_datum
         on sitefile.alt_datum_cd = altitude_datum.code
       left join nwis.altitude_method
         on sitefile.alt_meth_cd = altitude_method.code
       left join nwis.aqfr
         on sitefile.aqfr_cd = aqfr.aqfr_cd and
            sitefile.country_cd = aqfr.country_cd and
            sitefile.state_cd = aqfr.state_cd
       left join nwis.aquifer_type
         on sitefile.aqfr_type_cd = aquifer_type.code
       left join nwis.county
         on sitefile.county_cd = county.county_cd and
            sitefile.state_cd = county.state_cd and
            sitefile.country_cd = county.country_cd
       left join nwis.data_reliability
         on sitefile.reliability_cd = data_reliability.code
       left join nwis.huc
         on sitefile.huc_cd = huc.code
       left join nwis.lat_long_accuracy
         on sitefile.coord_acy_cd = lat_long_accuracy.code
       left join nwis.lat_long_datum
         on sitefile.coord_datum_cd = lat_long_datum.code
       left join nwis.lat_long_datum decimal_lat_long_datum
         on sitefile.dec_coord_datum_cd = decimal_lat_long_datum.code
       left join nwis.lat_long_method
         on sitefile.coord_meth_cd = lat_long_method.code
       left join nwis.nat_aqfr
         on sitefile.nat_aqfr_cd = nat_aqfr.nat_aqfr_cd and
            sitefile.country_cd = nat_aqfr.country_cd and
            sitefile.state_cd = nat_aqfr.state_cd
       left join nwis.site_tp
         on sitefile.site_tp_cd = site_tp.site_tp_cd
       left join nwis.state district
         on sitefile.state_cd = district.state_cd and
            sitefile.country_cd = district.country_cd
       left join nwis.state
         on sitefile.state_cd = state.state_cd and
            sitefile.country_cd = state.country_cd
       left join nwis.topographic_setting
         on sitefile.topo_cd = topographic_setting.code
       left join wqp.huc12nometa
         on st_covers(huc12nometa.geometry, sitefile.geom)
on conflict on constraint monitoring_location_ak do
    update set agency = excluded.agency,
               site_identification_number = excluded.site_identification_number,
               site_name = excluded.site_name,
               site_type = excluded.site_type,
               dms_latitude = excluded.dms_latitude,
               dms_longitude = excluded.dms_longitude,
               decimal_latitude = excluded.decimal_latitude,
               decimal_longitude = excluded.decimal_longitude,
               latitude_longitude_method = excluded.latitude_longitude_method,
               latitude_longitude_accuracy = excluded.latitude_longitude_accuracy,
               latitude_longitude_accuracy_value = excluded.latitude_longitude_accuracy_value,
               latitude_longitude_accuracy_unit = excluded.latitude_longitude_accuracy_unit,
               latitude_longitude_datum = excluded.latitude_longitude_datum,
               decimal_latitude_longitude_datum = excluded.decimal_latitude_longitude_datum,
               district = excluded.district,
               state = excluded.state,
               county = excluded.county,
               country = excluded.country,
               land_net_location_description = excluded.land_net_location_description,
               name_of_location_map = excluded.name_of_location_map,
               scale_of_location_map = excluded.scale_of_location_map,
               altitude_of_guage_land_surface = excluded.altitude_of_guage_land_surface,
               method_altitude_determined = excluded.method_altitude_determined,
               altitude_accuracy = excluded.altitude_accuracy,
               altitude_datum = excluded.altitude_datum,
               subbasin_hydrologic_unit = excluded.subbasin_hydrologic_unit,
               drainage_basin = excluded.drainage_basin,
               topographic_setting = excluded.topographic_setting,
               flags_for_instruments_at_site = excluded.flags_for_instruments_at_site,
               date_of_first_construction = excluded.date_of_first_construction,
               date_site_established_or_inventoried = excluded.date_site_established_or_inventoried,
               drainage_area = excluded.drainage_area,
               contributing_drainage_area = excluded.contributing_drainage_area,
               time_zone_abbreviation = excluded.time_zone_abbreviation,
               site_honors_daylight_savings_time = excluded.site_honors_daylight_savings_time,
               data_reliability = excluded.data_reliability,
               data_other_gw_files = excluded.data_other_gw_files,
               national_aquifer = excluded.national_aquifer,
               local_aquifer = excluded.local_aquifer,
               local_aquifer_type = excluded.local_aquifer_type,
               well_depth = excluded.well_depth,
               hole_depth = excluded.hole_depth,
               source_of_hole_depth = excluded.source_of_hole_depth,
               project_numer = excluded.project_numer,
               site_id = excluded.site_id,
               agency_cd = excluded.agency_cd,
               site_tp_cd = excluded.site_tp_cd,
               coord_meth_cd = excluded.coord_meth_cd,
               coord_acy_cd = excluded.coord_acy_cd,
               coord_datum_cd = excluded.coord_datum_cd,
               dec_coord_datum_cd = excluded.dec_coord_datum_cd,
               district_cd = excluded.district_cd,
               state_cd = excluded.state_cd,
               county_cd = excluded.county_cd,
               country_cd = excluded.country_cd,
               alt_meth_cd = excluded.alt_meth_cd,
               alt_datum_cd = excluded.alt_datum_cd,
               huc_cd = excluded.huc_cd,
               basin_cd = excluded.basin_cd,
               topo_cd = excluded.topo_cd,
               tz_cd = excluded.tz_cd,
               local_time_fg = excluded.local_time_fg,
               reliability_cd = excluded.reliability_cd,
               gw_file_cd = excluded.gw_file_cd,
               nat_aqfr_cd = excluded.nat_aqfr_cd,
               aqfr_cd = excluded.aqfr_cd,
               aqfr_type_cd = excluded.aqfr_type_cd,
               depth_src_cd = excluded.depth_src_cd,
               geom = excluded.geom,
               calculated_huc_12 = excluded.calculated_huc_12,
               nwis_host = excluded.nwis_host,
               db_no = excluded.db_no,
               site_web_cd = excluded.site_web_cd,
               monitoring_location_identifier = excluded.monitoring_location_identifier;
