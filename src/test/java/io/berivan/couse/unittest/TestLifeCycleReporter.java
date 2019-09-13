package io.berivan.couse.unittest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

import java.time.LocalDateTime;

public interface TestLifeCycleReporter
{

    @BeforeEach
    default void reportStart(TestInfo info, TestReporter reporter)
    {

        reporter.publishEntry("Start Time", LocalDateTime.now().toString());
        reporter.publishEntry("Start", info.getTestMethod().get().getName());

    }


    @AfterEach
    default void reportEnd(TestInfo info, TestReporter reporter)
    {

        reporter.publishEntry("End Time", LocalDateTime.now().toString());
        reporter.publishEntry("End", info.getTestMethod().get().getName());

    }

}
