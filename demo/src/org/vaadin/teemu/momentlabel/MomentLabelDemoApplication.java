package org.vaadin.teemu.momentlabel;

import java.util.Calendar;
import java.util.Date;

import com.vaadin.Application;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class MomentLabelDemoApplication extends Application {

    @Override
    public void init() {
        Window mainWindow = new Window("MomentLabelDemoApplication");

        Panel demoPanel = new Panel(createLayout());
        demoPanel.setWidth("600px");

        VerticalLayout mainLayout = createLayout();
        mainLayout.addComponent(demoPanel);
        mainLayout.setComponentAlignment(demoPanel, Alignment.TOP_CENTER);
        mainWindow.setContent(mainLayout);

        demoPanel.addComponent(createHeading());
        demoPanel.addComponent(createDescription());

        demoPanel.addComponent(createSeparator());
        demoPanel.addComponent(createSimpleLabel());

        demoPanel.addComponent(createSeparator());
        demoPanel.addComponent(createSimpleXhtmlLabel());

        demoPanel.addComponent(createSeparator());
        demoPanel.addComponent(createChangeableLabel());
        setMainWindow(mainWindow);
        setTheme("momentlabeldemo");
    }

    private VerticalLayout createLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        return layout;
    }

    private Component createDescription() {
        Label description = new Label(
                "<p>MomentLabel add-on is a drop-in replacement for Vaadin Label that "
                        + "displays time ranges in a human-readable format, "
                        + "for example \"5 minutes ago\", or \"in 30 days\".</p>"
                        + "<p>MomentLabel implementation wraps the <a href=\"http://momentjs.com/\">Moment.js</a> "
                        + "Javascript library for generating the textual time representations client-side. "
                        + "The label content is also updated every minute, so you'll see the "
                        + "changes in real-time without any roundtrip to the server.</p>",
                Label.CONTENT_XHTML);
        return description;
    }

    private Component createSeparator() {
        return new Label("<hr />", Label.CONTENT_XHTML);
    }

    private Component createHeading() {
        Label heading = new Label("MomentLabel");
        heading.setStyleName("h1");
        return heading;
    }

    private Component createSimpleLabel() {
        VerticalLayout layout = createLayout();
        layout.addComponent(new MomentLabel(
                "This MomentLabel was created ${moment}."));
        layout.addComponent(new Label(
                "<small>Source code:</small><pre>"
                        + "new MomentLabel(\"This MomentLabel was created ${moment}.\")"
                        + "</pre>", Label.CONTENT_XHTML));
        return layout;
    }

    private Component createSimpleXhtmlLabel() {
        VerticalLayout layout = createLayout();
        layout.addComponent(new MomentLabel("This MomentLabel was "
                + "created <strong>${moment}</strong>.", Label.CONTENT_XHTML));
        layout.addComponent(new Label(
                "<small>Source code:</small><pre>"
                        + "new MomentLabel(\"This MomentLabel "
                        + "was \" +\n\t\"created &lt;strong&gt;${moment}&lt;/strong&gt;.\",\n\tLabel.CONTENT_XHTML)"
                        + "</pre>", Label.CONTENT_XHTML));
        return layout;
    }

    private Component createChangeableLabel() {
        VerticalLayout layout = createLayout();
        final MomentLabel label = new MomentLabel(
                "You selected a date that is ${moment}.");

        DateField dateField = new DateField("Select your moment");
        dateField.setImmediate(true);
        dateField.setResolution(DateField.RESOLUTION_MIN);
        dateField.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                label.setMoment((Date) event.getProperty().getValue());
            }
        });
        dateField.setValue(getChristmasDate());
        layout.addComponent(dateField);
        layout.addComponent(label);

        layout.addComponent(new Label(
                "<small>Source code:</small><pre>"
                        + "final MomentLabel label = new MomentLabel(\"You selected \"\n\t+ \"a date that is ${moment}.\");\n"
                        + "// ...\n"
                        + "dateField.addListener(new ValueChangeListener() {\n"
                        + "    public void valueChange(ValueChangeEvent event) {\n"
                        + "        label.setMoment((Date) event.getProperty().getValue());\n"
                        + "    }\n" + "});" + "</pre>", Label.CONTENT_XHTML));

        return layout;
    }

    private Date getChristmasDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 24);
        cal.set(Calendar.HOUR_OF_DAY, 20);
        cal.set(Calendar.MINUTE, 00);
        return cal.getTime();
    }
}
