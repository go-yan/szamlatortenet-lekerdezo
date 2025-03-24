package dev.goyan.acchist.views.query;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.goyan.acchist.data.GridEntry;
import lombok.extern.slf4j.Slf4j;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.grid.Grid;

@PageTitle("Lekérdezés")
@Route("query")
@Menu(order = 2, icon = LineAwesomeIconUrl.USER)
@Uses(Icon.class)
@Slf4j
public class queryView extends Composite<VerticalLayout> {

    public queryView() {
        log.info("queryView");
        H3 h3 = new H3();
        H4 h4 = new H4();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Grid<GridEntry> basicGrid = new Grid<>(GridEntry.class);

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        h3.setText("Eredmények");
        h3.setWidth("max-content");
        h4.setText("Feldolgozások");
        h4.setWidth("max-content");
        layoutColumn2.setSizeFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        basicGrid.setWidth("100%");
        //setGridSampleData(basicGrid);
        getContent().add(h3);
        getContent().add(h4);
        getContent().add(layoutColumn2);
        layoutColumn2.add(basicGrid);
    }
}
