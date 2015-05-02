/*
  We're defining two SQL statements here, rather than the single statement that we used in the Bliss.sql example.
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