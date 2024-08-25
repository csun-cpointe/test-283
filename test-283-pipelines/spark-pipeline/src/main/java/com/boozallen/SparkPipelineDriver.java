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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.CDI;

import com.boozallen.pipeline.PipelineBase;

/**
 * Configures the steps needed to run a Spark-based implementation for SparkPipeline.
 *
 * This pipeline serves the following purpose: ${pipeline.description}.
 *
 * Please **DO** modify with your customizations, as appropriate.
 *
 * Originally generated from: templates/pipeline.driver.impl.java.vm 
 */
public class SparkPipelineDriver extends SparkPipelineBaseDriver {

  private static final Logger logger = LoggerFactory.getLogger(SparkPipelineDriver.class);
  
  public static void main(String[] args) {
    logger.info("STARTED: {} driver", "SparkPipeline");
    SparkPipelineBaseDriver.main(args);

    PipelineBase.getInstance().recordPipelineLineageStartEvent();


    final SimpleStep simpleStep = CDI.current().select(SimpleStep.class, new Any.Literal()).get();
    simpleStep.executeStep();
    PipelineBase.getInstance().recordPipelineLineageCompleteEvent();
  }
}
