package com.boozallen.cdi;

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
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.jboss.weld.environment.se.WeldContainer;

import com.boozallen.aissemble.core.cdi.CdiContainer;
import com.boozallen.aissemble.core.cdi.CdiContext;
import com.boozallen.aissemble.alerting.core.cdi.AlertingCdiContext;
import com.boozallen.aissemble.data.lineage.cdi.DataLineageCdiSelector;

/**
 * Factory that creates the proper CDI Context for this series of pipelines.
 *
 * Please **DO** modify with your customizations, as appropriate.
 *
 * Originally generated from: templates/cdi.container.factory.java.vm
 */
public final class CdiContainerFactory {

    private CdiContainerFactory() {
        // private construct to prevent instantiation of all static class
    }

    /**
     * Creates a new WeldContainer with the set of {@link CdiContext}
     * implementations needed for these pipelines.
     *
     * @return Weld Container instance
     */
    public static WeldContainer getCdiContainer() {
        return getCdiContainer(null);
    }

    /**
     * Creates a new WeldContainer with the set of {@link CdiContext}
     * implementations needed for these pipelines with the ability to add in
     * additional contexts in an ad-hoc fashion.
     *
     * @param additionalContexts
     * @return Weld Container instance
     */
    public static WeldContainer getCdiContainer(List<CdiContext> additionalContexts) {
        List<CdiContext> contexts = getContexts();
        if (CollectionUtils.isNotEmpty(additionalContexts)) {
            contexts.addAll(additionalContexts);
        }
        return CdiContainer.create(contexts);
    }

    protected static List<CdiContext> getContexts() {
        List<CdiContext> contexts = new ArrayList<>();
        contexts.add(new PipelinesCdiContext());
            contexts.add(new AlertingCdiContext());
            contexts.add(DataLineageCdiSelector.getDataLineageCdiContext());

        return contexts;
    }

}
