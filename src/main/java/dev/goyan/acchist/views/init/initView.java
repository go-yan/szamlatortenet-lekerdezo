package dev.goyan.acchist.views.init;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Kezdőoldal")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.PENCIL_RULER_SOLID)
@Slf4j
public class initView extends Composite<VerticalLayout> {

    public initView() {

        log.info("init view");

        //Creating components
        VerticalLayout mainLayout = new VerticalLayout();
        VerticalLayout centeredContent = new VerticalLayout();
        H1 h1 = new H1("Üdvözöljük!");

        Button parameterViewButton = new Button("Indítson új keresést", event -> UI.getCurrent().navigate("parameter"));
        Button queryViewButton = new Button("Nézze meg korábbi kereséseit", event -> UI.getCurrent().navigate("query"));

        getContent().add(mainLayout);
        mainLayout.add(centeredContent);
        centeredContent.add(h1);
        centeredContent.add(parameterViewButton);
        centeredContent.add(queryViewButton);

        parameterViewButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        queryViewButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        h1.setWidth("154px");
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setSizeFull();
        mainLayout.setWidth("100%");
        mainLayout.getStyle().set("flex-grow", "1");
        mainLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        mainLayout.setSizeFull();
        mainLayout.setAlignSelf(FlexComponent.Alignment.CENTER);
        centeredContent.setWidth("hug");
        // valamiért itt meg kell adni, különben gatya xd
        mainLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, centeredContent);
    }
}
