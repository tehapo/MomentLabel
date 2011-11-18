package org.vaadin.teemu.momentlabel.client.ui;

import com.google.gwt.user.client.Timer;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VLabel;

/**
 * Client-side implementation of MomentLabel add-on.
 * 
 * @see Moment
 */
public class VMomentLabel extends VLabel {

    public static final String ATTR_MOMENT_TIMESTAMP = "moment";
    public static final String ATTR_MODE = "mode";
    public static final String MOMENT_TAG = "${moment}";

    private static final String MOMENT_TAG_REGEXP = "\\$\\{moment\\}";
    private static final int MINUTE_IN_MILLIS = 1000 * 60;

    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-momentlabel";

    /** The client side widget identifier */
    protected String paintableId;

    /** Reference to the server connection object. */
    protected ApplicationConnection client;

    private Moment moment;
    private String unformattedText;
    private Timer updateTimer;
    private boolean textMode = true;

    /**
     * Called whenever an update is received from the server
     */
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        super.updateFromUIDL(uidl, client);
        addStyleName(CLASSNAME);

        // Save reference to server connection object to be able to send
        // user interaction later
        this.client = client;

        // Save the client side identifier (paintable id) for the widget
        paintableId = uidl.getId();

        // initialization
        initMode(uidl);
        storeUnformattedText();
        createMoment(uidl);

        // replace and schedule repeated updating
        replaceMomentTag();
        scheduleTimer();
    }

    private void createMoment(UIDL uidl) {
        if (uidl.hasAttribute(ATTR_MOMENT_TIMESTAMP)) {
            moment = Moment
                    .create(uidl.getLongAttribute(ATTR_MOMENT_TIMESTAMP));
        }
    }

    private void initMode(UIDL uidl) {
        if (uidl.hasAttribute("mode")) {
            textMode = uidl.getStringAttribute("mode").equals("text");
        }
    }

    private void storeUnformattedText() {
        // store the unformatted version in an instance variable
        if (textMode) {
            unformattedText = getText();
        } else {
            unformattedText = getHTML();
        }
    }

    private void replaceMomentTag() {
        if (unformattedText != null && unformattedText.contains(MOMENT_TAG)) {
            String replacement = unformattedText.replaceAll(MOMENT_TAG_REGEXP,
                    moment.fromNow());

            if (textMode) {
                setText(replacement);
            } else {
                setHTML(replacement);
            }
        }
    }

    private void scheduleTimer() {
        if (updateTimer == null) {
            // update the moment tag every minute
            updateTimer = new Timer() {
                @Override
                public void run() {
                    replaceMomentTag();
                }
            };
            updateTimer.scheduleRepeating(MINUTE_IN_MILLIS);
        }
    }
}
