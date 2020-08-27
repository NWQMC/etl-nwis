-- This function creates a geometry from a longitude (x) latitude (y) point specified
-- in Degrees, Minutes, Seconds (DMS). The dms format is numeric, negative values indicated by
-- a leading -  Optional values are denoted by []
-- longitude: [-][D]DDMMSS.SSSS  2 or 3 digits for Degrees, 2 digits for Minutes, 2 digits for Seconds
-- latitude: [-]DDMMSS[.SSSS]  2 digits for Degrees, 2 digits for Minutes, 2 digits for Seconds
-- Seconds can include a fractional value, on e or more decimal places. No practical limit.
-- Since results are rounded to 7 decimals, values beyond 2-3 decimal points do not impact the output.
CREATE OR REPLACE FUNCTION dms_to_geometry(long_va varchar, lat_va varchar)
RETURNS geometry
LANGUAGE plpgsql IMMUTABLE
AS $$
DECLARE
   degStart        int; -- position of first digit in ddmmss string.
   degLen          int;
   integerPartLen  int;
   decimalPos      int;

   degrees     numeric;
   minutes     numeric;
   seconds     numeric;

   dmsLongRegEx   text := '-?[0-9]{6,7}(.[0-9]+)?';
   dmsLatRegEx    text := '-?[0-9]{6}(.[0-9]+)?';

   degDecimalLat   numeric;
   degDecimalLong  numeric;
BEGIN
   if long_va not similar to dmsLongRegEx then
     return null;
   end if;
   if lat_va not similar to dmsLatRegEx then
     return null;
   end if;

   -- calculate longitude, numeric part before decimal can be 2 or 3 digits
   degStart := position('-' in long_va) + 1;
   decimalPos :=  position('.' in long_va);
   if decimalPos > 0 then
     integerPartLen = decimalPos - degStart;
   else
     integerPartLen = length(long_va) - (degStart - 1);
   end if;
   if integerPartLen = 6 then
        degLen := 2;
   else
        degLen := 3;
   end if;
   degrees := substring(long_va from degStart for degLen)::numeric;
   minutes := substring(long_va from (degStart + degLen) for 2)::numeric;
   seconds := substring(long_va from (degStart + degLen + 2))::numeric;
   degDecimalLong := degrees + (minutes/60.0) + (seconds/3600.0);
   If degStart = 1 Then
       -- make negative (flip input positive to negative)
       degDecimalLong := degDecimalLong * (-1.0);
   end if;
   if decimalPos > 0 then
     degDecimalLong := round(degDecimalLong, 7);
   else
     degDecimalLong := round(degDecimalLong, 2);
   end if;

   -- calculate latitude, numeric part before decimal is 2 digits
   degStart := position('-' in lat_va) + 1;
   degLen := 2;
   decimalPos := position('.' in lat_va);
   degrees := substring(lat_va from degStart for degLen)::numeric;
   minutes := substring(lat_va from (degStart + degLen) for 2)::numeric;
   seconds := substring(lat_va from (degStart + degLen + 2))::numeric;
   degDecimalLat := degrees + (minutes/60.0) + (seconds/3600.0);
   if degStart = 2 then
       -- keep sign same as input
       degDecimalLat := degDecimalLat * (-1.0);
   end if;
   if decimalPos > 0 then
     degDecimalLat := round(degDecimalLat, 7);
   else
     degDecimalLat := round(degDecimalLat, 2);
   end if;

   return st_SetSrid(st_MakePoint(degDecimalLong, degDecimalLat), 4269);
END
$$;
