package com.dw.test.base;

import com.dw.test.rules.JdbiTestResourceRule;
import org.junit.Rule;

/**
 * Tests concerned with persistence should extend this class.
 * This class provides test cases with access to the DBI handle allowing them to
 * initialize DAO objects under test. E.g.:
 *
 *    jdbiResource.getHandle();
 *
 */
public class BasePersistenceTest{
  @Rule
  public JdbiTestResourceRule jdbiResource = new JdbiTestResourceRule();
}
