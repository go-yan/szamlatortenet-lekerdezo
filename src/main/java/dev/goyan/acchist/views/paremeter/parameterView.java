package dev.goyan.acchist.views.paremeter;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Paraméter")
@Route("parameter")
@Menu(order = 1, icon = LineAwesomeIconUrl.PENCIL_RULER_SOLID)
@Slf4j
public class parameterView extends Composite<VerticalLayout> {

    public parameterView() {
        log.info("parameterView");
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        TextField textField3 = new TextField();
        EmailField emailField = new EmailField();
        DatePicker datePicker = new DatePicker();
        DatePicker datePicker2 = new DatePicker();
        Button buttonPrimary = new Button();
        HorizontalLayout layoutRow = new HorizontalLayout();

        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(textField);
        formLayout2Col.add(textField2);
        formLayout2Col.add(textField3);
        formLayout2Col.add(emailField);
        formLayout2Col.add(datePicker);
        formLayout2Col.add(datePicker2);
        layoutColumn2.add(buttonPrimary);
        layoutColumn2.add(layoutRow);

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        getContent().setAlignItems(FlexComponent.Alignment.START);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Paraméterezés");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        textField.setLabel("GWB azonosító");
        textField2.setLabel("Adóazonosító");
        textField3.setLabel("Adószám");
        emailField.setLabel("Számlaszám");
        datePicker.setLabel("Kezdő dátum");
        datePicker.setWidth("min-content");
        datePicker.setRequiredIndicatorVisible(true);
        datePicker2.setLabel("Vége dátum");
        datePicker2.setWidth("min-content");
        datePicker2.setRequiredIndicatorVisible(true);
        buttonPrimary.setText("Keresés");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");

    }
}
