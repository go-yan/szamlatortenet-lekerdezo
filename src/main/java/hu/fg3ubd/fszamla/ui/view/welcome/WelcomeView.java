package hu.fg3ubd.fszamla.ui.view.welcome;

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

import static hu.fg3ubd.fszamla.constants.UiConstants.*;

@PageTitle("Kezdőoldal")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.PENCIL_RULER_SOLID)
@Slf4j
public class WelcomeView extends Composite<VerticalLayout> {

    private final H1 welcomeText = createWelcomeText();
    private final Button redirectButton = createRedirectButton();
    private final VerticalLayout centeredContent = createCenteredLayout();
    private final VerticalLayout mainContent = createMainLayout();

    public WelcomeView() {
        initializeLayout();
    }

    private void initializeLayout() {
        centeredContent.add(welcomeText, redirectButton);
        mainContent.add(centeredContent);
        mainContent.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, centeredContent);

        getContent().add(mainContent);
        configureRootLayout();
    }

    private void configureRootLayout() {
        getContent().setWidth(ATTRIBUTE_HUNDRED_PERCENT);
        getContent().getStyle().set(ATTRIBUTE_FLEX_GROW, ATTRIBUTE_ONE);
        getContent().setSizeFull();
    }

    private VerticalLayout createMainLayout() {
        VerticalLayout mainLayout = new VerticalLayout();

        mainLayout.setWidth(ATTRIBUTE_HUNDRED_PERCENT);
        mainLayout.getStyle().set(ATTRIBUTE_FLEX_GROW, ATTRIBUTE_ONE);
        mainLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        mainLayout.setAlignSelf(FlexComponent.Alignment.CENTER);
        mainLayout.setSizeFull();

        return mainLayout;
    }

    private VerticalLayout createCenteredLayout() {
        VerticalLayout centeredContent = new VerticalLayout();

        centeredContent.setWidth(ATTRIBUTE_HUG);

        return centeredContent;
    }

    private Button createRedirectButton() {
        Button parameterViewButton = new Button();

        parameterViewButton.setText(ATTRIBUTE_NEW_QUERY);
        parameterViewButton.addClickListener(event -> UI.getCurrent().navigate(ATTRIBUTE_PARAMETER_PAGE));
        parameterViewButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return parameterViewButton;
    }

    private H1 createWelcomeText() {
        H1 welcomeText = new H1();

        welcomeText.setText(ATTRIBUTE_WELCOME_TEXT);
        welcomeText.setWidth(ATTRIBUTE_WELCOME_TEXT_WIDTH);

        return welcomeText;
    }
}
