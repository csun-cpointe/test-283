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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.inject.spi.Extension;

import com.boozallen.aissemble.core.cdi.CdiContext;

import io.smallrye.reactive.messaging.providers.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.providers.connectors.ExecutionHolder;
import io.smallrye.reactive.messaging.providers.extension.ChannelProducer;
import io.smallrye.reactive.messaging.providers.extension.HealthCenter;
import io.smallrye.reactive.messaging.providers.impl.InternalChannelRegistry;
import io.smallrye.reactive.messaging.providers.MediatorFactory;
import io.smallrye.reactive.messaging.providers.extension.MediatorManager;
import io.smallrye.reactive.messaging.providers.wiring.Wiring;
import io.smallrye.reactive.messaging.providers.connectors.WorkerPoolRegistry; 


/**
 * Provides the set of CDI context values needed to run Cucumber tests.
 *
 * GENERATED STUB CODE - PLEASE ***DO*** MODIFY
 *
 * Originally generated from: templates/cucumber.test.cdi.context.java.vm 
 */
public class TestCdiContext implements CdiContext {

	@Override
	public List<Class<?>> getCdiClasses() {
		List<Class<?>> classes = new ArrayList<>();
		classes.add(InMemoryConnector.class);
		classes.add(ExecutionHolder.class);
		classes.add(ChannelProducer.class);
		classes.add(HealthCenter.class);
		classes.add(InternalChannelRegistry.class);
		classes.add(MediatorFactory.class);
		classes.add(MediatorManager.class);
		classes.add(Wiring.class);
		classes.add(WorkerPoolRegistry.class); 
		return classes;
	}

	@Override
	public List<Extension> getExtensions() {
		return Collections.emptyList();
	}

}
