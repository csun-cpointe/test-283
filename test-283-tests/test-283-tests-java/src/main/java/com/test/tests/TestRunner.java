package com.test.tests;

/*-
 * #%L
 * test-283::Tests::Java
 * %%
 * Copyright (C) 2021 Booz Allen
 * %%
 * All Rights Reserved. You may not copy, reproduce, distribute, publish, display, 
 * execute, modify, create derivative works of, transmit, sell or offer for resale, 
 * or in any way exploit any part of this solution without Booz Allen Hamilton’s 
 * express written permission.
 * #L%
 */

import io.cucumber.core.cli.Main;

public class TestRunner {
    public static void main(String[] args) throws Throwable {
        String[] defaultArgs = new String[] {
            "./specifications",
            "--glue",
            "com.test.tests"
        };

        Main.main(defaultArgs);
    }
}
