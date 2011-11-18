package org.vaadin.teemu.momentlabel.client.ui;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Simplified wrapper class for the moment object of <a
 * href="http://momentjs.com/">Moment.js</a>.
 */
public final class Moment extends JavaScriptObject {

    protected Moment() {
        // the required protected constructor
    }

    /**
     * Instantiates a new Moment based on the given Unix timestamp.
     * 
     * @param unixTimestamp
     *            the Unix timestamp representation of this Moment.
     * @return a new Moment instance.
     */
    public static Moment create(long unixTimestamp) {
        // long is not allowed in JSNI methods -> use String
        return create(Long.toString(unixTimestamp));
    }

    // Disable Eclipse code formatter for JSNI methods.
    // @formatter:off
    
    private static native Moment create(String unixTimestamp) /*-{
        return $wnd.moment(new Number(unixTimestamp));
    }-*/;

    public native String fromNow() /*-{
        return this.fromNow();
    }-*/;
    
    public native String from(Moment moment) /*-{
        return this.from(moment);
    }-*/;

    // @formatter:on
}
