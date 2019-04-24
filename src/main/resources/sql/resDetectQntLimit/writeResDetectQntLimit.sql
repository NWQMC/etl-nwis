insert 
  into r_detect_qnt_lmt_swap_nwis(
  	data_source_id, 
  	data_source, 
  	station_id, 
  	site_id, 
  	event_date, 
  	activity, 
  	analytical_method,
    characteristic_name, 
    characteristic_type, 
    sample_media, 
    organization, 
    site_type, 
    huc, 
    governmental_unit_code,
    geom,
    project_id, 
    assemblage_sampled_name, 
    sample_tissue_taxonomic_name, 
    activity_id,
    result_id, 
    organization_name, 
    detection_limit_id, 
    detection_limit, 
    detection_limit_unit, 
    detection_limit_desc)
select
       data_source_id,
       data_source,
       station_id,
       site_id,
       event_date,
       activity,
       analytical_method,
       characteristic_name,
       characteristic_type,
       sample_media,
       organization,
       site_type,
       huc,
       governmental_unit_code,
       geom, 
       project_id,
       assemblage_sampled_name,
       sample_tissue_taxonomic_name,
       activity_id,
       result_id,
       organization_name,
       result_id detection_limit_id,
       detection_limit,
       detection_limit_unit,
       detection_limit_desc
  from result_swap_nwis
 where detection_limit is not null or
       detection_limit_unit is not null or
       detection_limit_desc is not null;