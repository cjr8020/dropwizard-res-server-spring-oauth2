package com.dw.test.base;

import com.dw.test.rules.TestPropertyLoaderRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aggregates common functionality for JUnit tests
 */
public class BaseTest {

  private static Logger log = LoggerFactory.getLogger(BaseTest.class);

  @ClassRule
  public static final TestPropertyLoaderRule configurator = new TestPropertyLoaderRule();

  @Rule
  public TestWatcher testWatcher = new TestWatcher() {
    @Override
    public void starting(Description description) {
      log.info(" *** Executing Test: {} ...", description.getDisplayName());
    }

    @Override
    public void skipped(org.junit.AssumptionViolatedException exception, Description description) {
      log.info(" -- Test {} was skipped...", description.getDisplayName());
    }
  };

}