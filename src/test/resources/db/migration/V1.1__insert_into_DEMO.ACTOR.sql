/*
 *  create test ACTOR records
 */

// -- jsmith
INSERT INTO DEMO.ACTOR (
  ACTOR_ID,
  USERNAME,
  NUMBER_OF_LOGINS,
  RECORD_VERSION,
  CREATED_BY,
  CREATED_TIMESTAMP
) VALUES (
  1,
  'jsmith',
  0,
  0,
  'UnitTest',
  SYSDATE
);

// -- jdoe
INSERT INTO DEMO.ACTOR (
  ACTOR_ID,
  USERNAME,
  NUMBER_OF_LOGINS,
  RECORD_VERSION,
  CREATED_BY,
  CREATED_TIMESTAMP
) VALUES (
  2,
  'jdoe',
  0,
  0,
  'UnitTest',
  SYSDATE
);