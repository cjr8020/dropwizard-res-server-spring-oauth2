package com.dw.test.rules;

import com.dw.test.util.TestPropertiesFileLoader;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Adding this rule to your JUnit test case will execute the TestPropertiesFileLoader
 * ensuring that your test is properly configured.
 * Therefore, all unit tests should use this rule.
 *
 * @see "test-properties-file" in maven-surefire-plugin configuration of your POM.
 */
public class TestPropertyLoaderRule implements TestRule {

  @Override
  public Statement apply(final Statement statement, final Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        TestPropertiesFileLoader.load();
        statement.evaluate();
      }
    };
  }
}