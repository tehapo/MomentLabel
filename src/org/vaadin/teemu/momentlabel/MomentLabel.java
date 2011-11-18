package org.vaadin.teemu.momentlabel;

import java.util.Date;

import org.vaadin.teemu.momentlabel.client.ui.VMomentLabel;

import com.vaadin.data.Property;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.Label;

/**
 * MomentLabel add-on is a drop-in replacement for Vaadin {@link Label} that
 * displays time ranges in a human-readable format, for example "5 minutes ago",
 * or "in 30 days". The label content is automatically updated every minute, so
 * you'll see the changes in real-time as time passes without any roundtrip to
 * the server. The client-side implementation is based on <a
 * href="http://momentjs.com/">Moment.js</a><br />
 * <br />
 * 
 * Use a tag in format "{@code $moment}" as a placeholder for the text to be
 * replaced with the human-readable time representation. <br />
 * <br />
 * 
 * Example usage:<br />
 * <code>
 * new MomentLabel("This MomentLabel was created ${moment}.")
   </code>
 */
@SuppressWarnings("serial")
@com.vaadin.ui.ClientWidget(org.vaadin.teemu.momentlabel.client.ui.VMomentLabel.class)
public class MomentLabel extends Label {

    // use the current date as the default
    protected Date momentDate = new Date();

    /**
     * The default constructor sets the content to "{@code $moment}" displaying
     * only the human-readable time representation.
     */
    public MomentLabel() {
        super(VMomentLabel.MOMENT_TAG);
    }

    public MomentLabel(Property contentSource) {
        super(contentSource);
    }

    public MomentLabel(String content) {
        super(content);
    }

    public MomentLabel(Property contentSource, int contentMode) {
        super(contentSource, contentMode);
    }

    public MomentLabel(String content, int contentMode) {
        super(content, contentMode);
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        // add the Unix timestamp of the moment
        target.addAttribute(VMomentLabel.ATTR_MOMENT_TIMESTAMP,
                momentDate.getTime());
    }

    /**
     * Sets the moment to which the current time is compared when rendering this
     * MomentLabel.
     * 
     * @param date
     *            the moment to display.
     */
    public void setMoment(Date date) {
        this.momentDate = date;
        requestRepaint();
    }

    /**
     * Returns the moment to which the current time is compared when rendering
     * this MomentLabel.
     * 
     * @return the moment to display.
     */
    public Date getMoment() {
        return momentDate;
    }
}
