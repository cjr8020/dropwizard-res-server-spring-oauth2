package com.dw.test.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads the configuration file for your project's test environment.
 * The filename is conveyed via ${test-properties-file} system variable set in your project/module's POM file.
 *
 * @see "properties" section of your POM.
 * For instance:
 * <!-- test dependencies -->
 *   <test-properties-file>/test.properties</test-properties-file>
 *
 * NOTE: the load() method attempts to be smart and skip loading the file if the file has already been loaded.
 * It is looking for "is.loaded" property.
 *
 */
public class TestPropertiesFileLoader {

  private static final String surefireTestFileProperty = "test-properties-file";

  public static void load() throws Throwable {
    final Properties testProperties = System.getProperties();
    if (testProperties.getProperty("is.loaded") == null) {
      if (System.getProperty(surefireTestFileProperty) == null) {
        throw new FileNotFoundException(
            new StringBuffer("TestPropertiesFileLoader ERROR: test-properties-file may not be setup or doesn't exist. ")
                .append("Please check the maven-surefire-plugin configuration in your POM.")
                .toString()
        );
      }
      try (
          InputStream in = TestPropertiesFileLoader.class.getClass().getResourceAsStream(System.getProperty(surefireTestFileProperty));
      ) {
        testProperties.load(in);
        in.close();
      }
    }
  }

}
