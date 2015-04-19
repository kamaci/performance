package com.kamaci.performance.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Marker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Logging filter class.
 *
 * @author Furkan KAMACI
 */
public class AlgorithmOutputFilter extends Filter<ILoggingEvent> {
    private static final Pattern MARKER_NAME_PATTERN = Pattern.compile("ALGORITHM\\.(\\w+)");

    /**
     * Checks whether event has a marker for algorithm or not to decide logging.
     *
     * @param event The event to decide upon.
     */
    @Override
    public FilterReply decide(ILoggingEvent event) {
        Marker marker = event.getMarker();
        if (marker != null) {
            Matcher m = MARKER_NAME_PATTERN.matcher(marker.getName());
            if (m.matches()) {
                return FilterReply.ACCEPT;
            }
        }
        return FilterReply.DENY;
    }
}
