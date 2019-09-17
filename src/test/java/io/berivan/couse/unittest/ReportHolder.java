package io.berivan.couse.unittest;

import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.concurrent.ConcurrentHashMap;

public class ReportHolder
{
    private static ConcurrentHashMap<ExtensionContext, Report> reports =
            new ConcurrentHashMap<>();
    public static Report getReport(ExtensionContext extensionContext)
    {
        Report report = reports.get(extensionContext);
        if (report == null)
        {
            report = new Report();
            reports.put(extensionContext, report);
        }
        return report;
    }
}
