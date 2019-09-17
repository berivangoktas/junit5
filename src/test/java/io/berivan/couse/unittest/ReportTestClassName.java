package io.berivan.couse.unittest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TestExtension.class)
public class ReportTestClassName
{

    @DisplayName("Raporlama")
    @Tag("TagReport")
    @Test
    void testReportTestName() throws InterruptedException
    {
        Thread.sleep(5000);
        String x = "2";
        String y = "3";
        Assertions.assertFalse(x.equals(y));
    }

    @DisplayName("Raporlama")
    @Tag("TagReport")
    @Test
    void testReportTestName2() throws InterruptedException
    {
        Thread.sleep(5000);
        String x = "2";
        String y = "3";
        Assertions.assertTrue(x.equals(y));
    }
}
