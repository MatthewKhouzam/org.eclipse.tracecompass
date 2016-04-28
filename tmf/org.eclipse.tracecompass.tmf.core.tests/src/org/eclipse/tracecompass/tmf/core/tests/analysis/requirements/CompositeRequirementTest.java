/*******************************************************************************
 * Copyright (c) 2016 École Polytechnique de Montréal
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.tracecompass.tmf.core.tests.analysis.requirements;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.tracecompass.tmf.core.analysis.requirements.TmfAnalysisRequirement;
import org.eclipse.tracecompass.tmf.core.analysis.requirements.TmfAnalysisRequirement.PriorityLevel;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;
import org.eclipse.tracecompass.tmf.tests.stubs.trace.TmfTraceStub;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import org.eclipse.tracecompass.tmf.core.analysis.requirements.TmfCompositeAnalysisRequirement;

/**
 * Test the {@link TmfCompositeAnalysisRequirement} class
 *
 * @author Geneviève Bastien
 */
public class CompositeRequirementTest {

    private static final @NonNull TmfAnalysisRequirement FALSE_REQ1 = new TmfAnalysisRequirement(Collections.EMPTY_SET, PriorityLevel.MANDATORY) {
        @Override
        public boolean test(ITmfTrace trace) {
            return false;
        }
    };

    private static final @NonNull TmfAnalysisRequirement FALSE_REQ2 = new TmfAnalysisRequirement(Collections.EMPTY_SET, PriorityLevel.MANDATORY) {
        @Override
        public boolean test(ITmfTrace trace) {
            return false;
        }
    };

    private static final @NonNull TmfAnalysisRequirement TRUE_REQ1 = new TmfAnalysisRequirement(Collections.EMPTY_SET, PriorityLevel.MANDATORY) {
        @Override
        public boolean test(ITmfTrace trace) {
            return true;
        }
    };

    private static final @NonNull TmfAnalysisRequirement TRUE_REQ2 = new TmfAnalysisRequirement(Collections.EMPTY_SET, PriorityLevel.MANDATORY) {
        @Override
        public boolean test(ITmfTrace trace) {
            return true;
        }
    };

    private ITmfTrace fTrace;

    /**
     * Setup a trace to be used in tests
     */
    @Before
    public void setupTrace() {
        fTrace = new TmfTraceStub();
    }

    /**
     * Test composite requirement with {@link PriorityLevel#MANDATORY} level
     */
    @Test
    public void testMandatory() {
        ITmfTrace trace = fTrace;
        assertNotNull(trace);

        TmfAnalysisRequirement req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(TRUE_REQ1), PriorityLevel.MANDATORY);
        assertTrue(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(TRUE_REQ1, TRUE_REQ2), PriorityLevel.MANDATORY);
        assertTrue(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(FALSE_REQ1), PriorityLevel.MANDATORY);
        assertFalse(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(FALSE_REQ1, TRUE_REQ1), PriorityLevel.MANDATORY);
        assertFalse(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(FALSE_REQ1, FALSE_REQ2), PriorityLevel.MANDATORY);
        assertFalse(req.test(trace));
    }

    /**
     * Test composite requirement with {@link PriorityLevel#AT_LEAST_ONE} level
     */
    @Test
    public void testAtLeastOne() {
        ITmfTrace trace = fTrace;
        assertNotNull(trace);

        TmfAnalysisRequirement req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(TRUE_REQ1), PriorityLevel.AT_LEAST_ONE);
        assertTrue(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(TRUE_REQ1, TRUE_REQ2), PriorityLevel.AT_LEAST_ONE);
        assertTrue(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(FALSE_REQ1), PriorityLevel.AT_LEAST_ONE);
        assertFalse(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(FALSE_REQ1, TRUE_REQ1), PriorityLevel.AT_LEAST_ONE);
        assertTrue(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(FALSE_REQ1, FALSE_REQ2), PriorityLevel.AT_LEAST_ONE);
        assertFalse(req.test(trace));
    }

    /**
     * Test composite requirement with {@link PriorityLevel#ALL_OR_NOTHING} level
     */
    @Test
    public void testAllOrNothing() {
        ITmfTrace trace = fTrace;
        assertNotNull(trace);

        TmfAnalysisRequirement req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(TRUE_REQ1), PriorityLevel.ALL_OR_NOTHING);
        assertTrue(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(TRUE_REQ1, TRUE_REQ2), PriorityLevel.ALL_OR_NOTHING);
        assertTrue(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(FALSE_REQ1), PriorityLevel.ALL_OR_NOTHING);
        assertTrue(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(FALSE_REQ1, TRUE_REQ1), PriorityLevel.ALL_OR_NOTHING);
        assertFalse(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(FALSE_REQ1, FALSE_REQ2), PriorityLevel.ALL_OR_NOTHING);
        assertTrue(req.test(trace));
    }

    /**
     * Test composite requirement with {@link PriorityLevel#OPTIONAL} level
     */
    @Test
    public void testOptional() {
        ITmfTrace trace = fTrace;
        assertNotNull(trace);

        TmfAnalysisRequirement req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(TRUE_REQ1), PriorityLevel.OPTIONAL);
        assertTrue(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(TRUE_REQ1, TRUE_REQ2), PriorityLevel.OPTIONAL);
        assertTrue(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(FALSE_REQ1), PriorityLevel.OPTIONAL);
        assertTrue(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(FALSE_REQ1, TRUE_REQ1), PriorityLevel.OPTIONAL);
        assertTrue(req.test(trace));

        req = new TmfCompositeAnalysisRequirement(ImmutableSet.of(FALSE_REQ1, FALSE_REQ2), PriorityLevel.OPTIONAL);
        assertTrue(req.test(trace));
    }

}