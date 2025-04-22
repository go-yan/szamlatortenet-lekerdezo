package hu.fg3ubd.fszamla.ui.view.paremeter;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import hu.fg3ubd.fszamla.data.dto.QueryParameters;
import hu.fg3ubd.fszamla.data.dto.QueryResult;
import hu.fg3ubd.fszamla.data.factory.UserNotificationFactory;
import hu.fg3ubd.fszamla.data.exception.ValidationException;
import hu.fg3ubd.fszamla.service.AccountHistoryService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import software.xdev.vaadin.grid_exporter.GridExporter;
import software.xdev.vaadin.grid_exporter.jasper.format.CsvFormat;
import software.xdev.vaadin.grid_exporter.jasper.format.PdfFormat;
import software.xdev.vaadin.grid_exporter.jasper.format.XlsxFormat;

import static hu.fg3ubd.fszamla.constants.ServiceConstants.WRONG_ACCOUNT_ID_FORMAT;
import static hu.fg3ubd.fszamla.constants.UiConstants.*;


import java.sql.SQLException;
import java.util.List;

@PageTitle("Paraméter")
@Route("parameter")
@Menu(order = 1, icon = LineAwesomeIconUrl.PENCIL_RULER_SOLID)
@Slf4j
public class ParameterView extends Composite<VerticalLayout> {

    private final H3 parameterText = new H3();
    private final TextField accountIdTextField = createAccountIdField();
    private final TextField taxNumberTextField = createTaxNumberField();
    private final DatePicker startDatePicker = createStartDatePicker();
    private final DatePicker endDatePicker = createEndDatePicker();
    private final Button searchButton = new Button();
    private final Button exportButton = new Button();
    private final Grid<QueryResult> queryResultGrid = new Grid<>(QueryResult.class);

    @Inject
    AccountHistoryService service;

    public ParameterView() {
        initializeLayout();
        configureComponents();
        setupEventListeners();
    }

    private void initializeLayout() {
        final VerticalLayout mainLayout = createMainLayout();
        final FormLayout formLayout = createFormLayout();
        final HorizontalLayout buttonLayout = createButtonLayout();

        formLayout.add(accountIdTextField, taxNumberTextField, startDatePicker, endDatePicker);
        buttonLayout.add(searchButton, exportButton);
        mainLayout.add(parameterText, formLayout, buttonLayout);

        getContent().add(mainLayout, queryResultGrid);
        configureRootLayout();
    }

    private void configureRootLayout() {
        getContent().setWidth(ATTRIBUTE_HUNDRED_PERCENT);
        getContent().getStyle().set(ATTRIBUTE_FLEX_GROW, ATTRIBUTE_ONE);
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        getContent().setAlignItems(FlexComponent.Alignment.START);
    }

    private VerticalLayout createMainLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth(ATTRIBUTE_HUNDRED_PERCENT);
        layout.setMaxWidth(ATTRIBUTE_800_PX);
        layout.setHeight(ATTRIBUTE_MIN_CONTENT);
        return layout;
    }

    private FormLayout createFormLayout() {
        FormLayout layout = new FormLayout();
        layout.setWidth(ATTRIBUTE_HUNDRED_PERCENT);
        return layout;
    }

    private HorizontalLayout createButtonLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassName(ATTRIBUTE_BUTTON_ROW);
        layout.setWidth(ATTRIBUTE_HUNDRED_PERCENT);
        layout.getStyle().set(ATTRIBUTE_FLEX_GROW, ATTRIBUTE_ONE);
        return layout;
    }

    private TextField createAccountIdField() {
        TextField field = new TextField();
        field.setAllowedCharPattern(ATTRIBUTE_CHAR_PATTERN_DECIMAL);
        field.setMinLength(16);
        field.setMaxLength(16);
        field.setLabel(ATTRIBUTE_ACCOUNT_ID);
        field.setI18n(new TextField.TextFieldI18n()
                .setPatternErrorMessage(WRONG_ACCOUNT_ID_FORMAT)
                .setMinLengthErrorMessage(ATTRIBUTE_INVALID_ACCOUNT_ID_LENGTH)
                .setMaxLengthErrorMessage(ATTRIBUTE_INVALID_ACCOUNT_ID_LENGTH));
        return field;
    }

    private TextField createTaxNumberField() {
        TextField field = new TextField();
        field.setAllowedCharPattern(ATTRIBUTE_CHAR_PATTERN_DECIMAL);
        field.setMinLength(10);
        field.setMaxLength(10);
        field.setLabel(ATTRIBUTE_TAX_ID);
        field.setI18n(new TextField.TextFieldI18n()
                .setPatternErrorMessage(ATTRIBUTE_INVALID_TAX_ID_FORMAT)
                .setMinLengthErrorMessage(ATTRIBUTE_INVALID_TAX_ID_LENGTH)
                .setMaxLengthErrorMessage(ATTRIBUTE_INVALID_TAX_ID_LENGTH));
        return field;
    }

    private DatePicker createStartDatePicker() {
        DatePicker picker = new DatePicker();
        picker.setWidth(ATTRIBUTE_MIN_CONTENT);
        picker.setLabel(ATTRIBUTE_START_DATE);
        picker.setRequiredIndicatorVisible(true);
        picker.setI18n(new DatePicker.DatePickerI18n().setRequiredErrorMessage(ATTRIBUTE_MANDATORY_START_DATE));
        return picker;
    }

    private DatePicker createEndDatePicker() {
        DatePicker picker = new DatePicker();
        picker.setWidth(ATTRIBUTE_MIN_CONTENT);
        picker.setLabel(ATTRIBUTE_END_DATE);
        picker.setRequiredIndicatorVisible(true);
        picker.setI18n(new DatePicker.DatePickerI18n().setRequiredErrorMessage(ATTRIBUTE_MANDATORY_END_DATE));
        return picker;
    }

    private void configureComponents() {
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.setText(ATTRIBUTE_SEARCH_TEXT);
        exportButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        exportButton.setVisible(false);
        exportButton.setText(ATTRIBUTE_EXPORT_TEXT);
        parameterText.setWidth(ATTRIBUTE_HUNDRED_PERCENT);
        parameterText.setText(ATTRIBUTE_PARAMETER_TEXT);
    }

    private void setupEventListeners() {
        searchButton.addClickListener(event -> performSearch());
        exportButton.addClickListener(event -> setupGridExporter());
    }

    private void performSearch() {
        try {
            validateInputs();
            final QueryParameters params = buildQueryParams();
            final List<QueryResult> results = service.getAccHist(params);

            handleSearchResults(results);
        } catch (ValidationException | SQLException e) {
            handleErrors(e);
        }
    }

    private void validateInputs() throws ValidationException {
        if (endDatePicker.getValue() != null &&
                endDatePicker.getValue().isBefore(startDatePicker.getValue())) {
            throw new ValidationException(ATTRIBUTE_INVALID_DATES);
        }
    }

    private QueryParameters buildQueryParams() {
        return QueryParameters.builder()
                .accountId(accountIdTextField.getOptionalValue().orElse(null))
                .taxNumber(taxNumberTextField.getOptionalValue().orElse(null))
                .startDate(startDatePicker.getValue())
                .endDate(endDatePicker.getValue())
                .build();
    }

    private void handleSearchResults(List<QueryResult> results) {
        if (!results.isEmpty()) {
            queryResultGrid.setItems(results);
            exportButton.setVisible(true);
        } else {
            UserNotificationFactory.createDatabaseErrorNotification(
                    ATTRIBUTE_NOT_FOUND).open();
            exportButton.setVisible(false);
        }
    }

    private void setupGridExporter() {
        GridExporter.newWithDefaults(queryResultGrid)
                .withAvailableFormats(List.of(new XlsxFormat(), new CsvFormat(), new PdfFormat()))
                .open();
    }

    private void handleErrors(Exception e) {
        if (e instanceof ValidationException) {
            UserNotificationFactory.createValidationExceptionNotification((ValidationException) e).open();
        } else if (e instanceof SQLException) {
            log.error(ATTRIBUTE_DATABASE_ERROR_LOG, e);
            UserNotificationFactory.createDatabaseErrorNotification(ATTRIBUTE_DATABASE_ERROR_USER_MESSAGE)
                    .open();
        }
    }
}
