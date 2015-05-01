/*
  We're defining two SQL statements here, rather then the single on that we used in teh Bliss.sql example.
 */


select
    E_MEMBERNO as CompassMemberNo,
    E_PSTART_DATE as CompassStartDate,
    E_PSTATUS as CompassStatus
from
    COMPASS_1
where
    E_MEMBERNO = '[POLICYNO]';

select
    E_CLIENT_NAME as CompassClientName,
    E_CDOB as CompassDateOfBirth
from
    COMPASS_2
where E_MEMBERNO = '[POLICYNO]';