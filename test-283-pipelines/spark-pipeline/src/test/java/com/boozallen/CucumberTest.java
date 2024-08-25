package com.boozallen;

/*-
 * #%L
 * test-283::Pipelines::Spark Pipeline
 * %%
 * Copyright (C) 2021 Booz Allen
 * %%
 * All Rights Reserved. You may not copy, reproduce, distribute, publish, display, 
 * execute, modify, create derivative works of, transmit, sell or offer for resale, 
 * or in any way exploit any part of this solution without Booz Allen Hamilton’s 
 * express written permission.
 * #L%
 */

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Configures JUnit to pick up and run Cucumber tests.
 *
 * GENERATED STUB CODE - PLEASE ***DO*** MODIFY
 *
 * Originally generated from: templates/cucumber.surefire.harness.java.vm 
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/specifications",
        plugin = {"json:target/cucumber-reports/cucumber.json", "com.boozallen.SparkTestHarness"},
        tags = "not @manual")
public class CucumberTest {

}
