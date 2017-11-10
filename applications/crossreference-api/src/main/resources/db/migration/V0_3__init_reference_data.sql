INSERT INTO REFERENCE VALUES ('1', (select REFERENCE_TYPE_ID from REFERENCE_TYPE where REFERENCE_TYPE = 'WebProfile'), 'test123', 'master123');
commit;