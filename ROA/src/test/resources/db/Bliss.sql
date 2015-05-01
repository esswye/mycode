--  You can define multiple statements here or lump them all into one.
--  The advantages of multiple smaller statements is that they can be clearer whereas one large statement might be more
--  efficient. However, clarity is better than efficiency in this case.

/*
select
    POLNO as BlissPolicyNo,
    PSTART_DATE as BlissStartDate,
    PSTATUS as BlissStatus
from
    TA201
where
    POLNO = '[POLICYNO]';

select
    CLIENT_NAME as BlissClientName,
    CDOB as BlissDateOfBirth
from
    TA309
where POLNO = '[POLICYNO]';
*/


select
    TA201.POLNO as BlissPolicyNo,
    PSTART_DATE as BlissStartDate,
    PSTATUS as BlissStatus,
    CLIENT_NAME as BlissClientName,
    CDOB as BlissDateOfBirth
from
    TA201, TA309
where
    POLNO = '[POLICYNO]' and
    TA201.POLNO = TA309.POLNO
