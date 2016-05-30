package com.ittekikun.plugin.itkcore.logger;

import java.util.logging.*;

public class LogFilter implements Filter
{
    private String prefix;

    public LogFilter(String prefix)
    {
        this.prefix = prefix;
    }

    public boolean isLoggable(LogRecord logRecord)
    {
        logRecord.setMessage(prefix + logRecord.getMessage());
        return true;
    }
}