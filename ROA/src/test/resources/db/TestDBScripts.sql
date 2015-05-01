/* 
  Create an in memory table and populate it with some records.
*/

create memory table MY_TABLE (
    YO_POLICY varchar(10),
    YO_NAME varchar(50),
    YO_UDDERNAME varchar(50),
    YO_DOB date,
    YO_STATUS integer
);

insert into MY_TABLE (YO_POLICY, YO_NAME, YO_UDDERNAME, YO_DOB, YO_STATUS) values ('1000', 'John', 'Murphy', Date '1970-01-29', 99);
insert into MY_TABLE (YO_POLICY, YO_NAME, YO_UDDERNAME, YO_DOB, YO_STATUS) values ('1002', 'Pat', 'Jones', Date '1990-12-31', 77);
insert into MY_TABLE (YO_POLICY, YO_NAME, YO_UDDERNAME, YO_DOB, YO_STATUS) values ('1003', 'Mary', 'Walsh', Date '1966-11-20', 66);
insert into MY_TABLE (YO_POLICY, YO_NAME, YO_UDDERNAME, YO_DOB, YO_STATUS) values ('1004', 'Tom', 'Kearney', Date '1962-04-07', 88);
insert into MY_TABLE (YO_POLICY, YO_NAME, YO_UDDERNAME, YO_DOB, YO_STATUS) values ('1005', 'Tom', 'Crean', Date '1889-01-01', 91);
insert into MY_TABLE (YO_POLICY, YO_NAME, YO_UDDERNAME, YO_DOB, YO_STATUS) values ('1006', 'Sinead', 'O''Conner', Date '1989-09-09', 42);


/*
  Create another table and populate it 
*/
create memory table POLICY_TABLE (
    PolicyNumber varchar(10),
    PolicyStatus varchar(50),
    PolicyEndDate date
);

insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1000', 'Live',    Date '2020-01-29');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1001', 'PaidUp',  Date '2021-02-28');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1002', 'Live',    Date '2022-03-29');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1003', 'PaidUp',  Date '2023-04-29');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1004', 'Live',    Date '2024-05-29');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1005', 'Live',    Date '2025-06-29');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1006', 'PaidUp',  Date '2026-07-29');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1007', 'Live',    Date '2027-08-29');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1008', 'PaidUp',  Date '2028-09-29');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1009', 'Live',    Date '2029-10-29');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1010', 'Live',    Date '2030-11-29');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1011', 'Live',    Date '2031-12-29');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1012', 'PaidUp',  Date '2032-01-29');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1013', 'Live',    Date '2033-02-28');
insert into POLICY_TABLE (PolicyNumber, PolicyStatus, PolicyEndDate) values ('1014', 'PaidUp',  Date '2034-03-29');


/*
Create dummy BLISS tables and populate them.
 */

create memory table TA201 (
    POLNO varchar(10),
    PSTART_DATE date,
    PSTATUS integer
);

insert into TA201 (POLNO, PSTART_DATE, PSTATUS) values ('1234567A', Date '2000-01-29', 1);
insert into TA201 (POLNO, PSTART_DATE, PSTATUS) values ('2345678B', Date '2001-01-29', 2);
insert into TA201 (POLNO, PSTART_DATE, PSTATUS) values ('3456789C', Date '2002-01-29', 3);


create memory table TA309 (
    POLNO varchar(10),
    CLIENT_NAME varchar(30),
    CDOB date
);

insert into TA309 (POLNO, CLIENT_NAME, CDOB) values ('1234567A', 'John Smith', Date '1960-01-01');
insert into TA309 (POLNO, CLIENT_NAME, CDOB) values ('2345678B', 'Pat Murphy', Date '1970-01-01');
insert into TA309 (POLNO, CLIENT_NAME, CDOB) values ('3456789C', 'Larry Duggan', Date '1980-01-01');



/*
Create dummy COMPASS tables and populate them.
 */

create memory table COMPASS_1(
    E_MEMBERNO varchar(10),
    E_PSTART_DATE date,
    E_PSTATUS integer
);

insert into COMPASS_1 (E_MEMBERNO, E_PSTART_DATE, E_PSTATUS) values ('98765432', Date '2005-01-29', 1);
insert into COMPASS_1 (E_MEMBERNO, E_PSTART_DATE, E_PSTATUS) values ('87654321', Date '2006-01-29', 2);
insert into COMPASS_1 (E_MEMBERNO, E_PSTART_DATE, E_PSTATUS) values ('76543210', Date '2007-01-29', 3);


create memory table COMPASS_2 (
    E_MEMBERNO varchar(10),
    E_CLIENT_NAME varchar(30),
    E_CDOB date
);

insert into COMPASS_2 (E_MEMBERNO, E_CLIENT_NAME, E_CDOB) values ('98765432', 'Mary Smith', Date '1965-02-02');
insert into COMPASS_2 (E_MEMBERNO, E_CLIENT_NAME, E_CDOB) values ('87654321', 'Joan Murphy', Date '1975-02-02');
insert into COMPASS_2 (E_MEMBERNO, E_CLIENT_NAME, E_CDOB) values ('76543210', 'Anne Duggan', Date '1985-02-02');
