package at.fhbfi.tourism.noetourismapp.ui;

import at.fhbfi.tourism.noetourismapp.model.Hotel;
import at.fhbfi.tourism.noetourismapp.service.HotelService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

@Route("")
@Component
public class HotelView extends VerticalLayout {

    private final HotelService hotelService;
    private final Grid<Hotel> grid = new Grid<>(Hotel.class, false);
    private final TextField searchField = new TextField("🔍 Suche nach Namen");

    public HotelView(HotelService hotelService) {
        this.hotelService = hotelService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.START);

        configureSearchField();
        configureGrid();

        add(new HorizontalLayout(searchField, createToolbar()), grid);
        refreshGrid();
    }

    private void configureSearchField() {
        searchField.setPlaceholder("z. B. Hotel Sissi");
        searchField.setClearButtonVisible(true);
        searchField.setWidth("300px");
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> refreshGrid());
    }

    private void configureGrid() {
        grid.addColumn(Hotel::getName).setHeader("Name").setSortable(true);
        grid.addColumn(Hotel::getStars).setHeader("Sterne");
        grid.addColumn(Hotel::getBranches).setHeader("Betriebe");
        grid.addColumn(Hotel::getRooms).setHeader("Zimmer");
        grid.addColumn(Hotel::getBeds).setHeader("Betten");
        grid.setSizeFull();
    }

    private HorizontalLayout createToolbar() {
        Button add = new Button("➕ Hotel hinzufügen", e -> openDialog(null));
        Button edit = new Button("✏️ Bearbeiten", e -> {
            Hotel selected = grid.asSingleSelect().getValue();
            if (selected != null) {
                openDialog(selected);
            } else {
                showError("Bitte ein Hotel auswählen.");
            }
        });

        Button delete = new Button("🗑️ Löschen", e -> {
            Hotel selected = grid.asSingleSelect().getValue();
            if (selected != null) {
                hotelService.deleteHotelById(selected.getId());
                refreshGrid();
                Notification.show("Hotel gelöscht.");
            } else {
                showError("Bitte ein Hotel auswählen.");
            }
        });

        add.getStyle().set("margin-right", "10px");
        edit.getStyle().set("margin-right", "10px");

        return new HorizontalLayout(add, edit, delete);
    }

    private void openDialog(Hotel hotel) {
        boolean isEdit = hotel != null;
        Dialog dialog = new Dialog();

        TextField name = new TextField("🏨 Name");
        IntegerField stars = new IntegerField("⭐ Sterne (1-5)");
        IntegerField branches = new IntegerField("🏢 Betriebe");
        IntegerField rooms = new IntegerField("🛏️ Zimmer");
        IntegerField beds = new IntegerField("🛌 Betten");

        stars.setMin(1); stars.setMax(5);
        branches.setMin(1); branches.setMax(1000);
        rooms.setMin(1); rooms.setMax(2000);
        beds.setMin(1); beds.setMax(10000);

        name.setWidthFull(); stars.setWidth("150px");
        branches.setWidth("150px"); rooms.setWidth("150px"); beds.setWidth("150px");

        if (isEdit) {
            name.setValue(hotel.getName());
            stars.setValue(hotel.getStars());
            branches.setValue(hotel.getBranches());
            rooms.setValue(hotel.getRooms());
            beds.setValue(hotel.getBeds());
        }

        Button save = new Button("💾 Speichern", e -> {
            if (name.isEmpty() || stars.isEmpty() || branches.isEmpty() || rooms.isEmpty() || beds.isEmpty()) {
                showError("Alle Felder müssen ausgefüllt werden!");
                return;
            }

            if (stars.getValue() < 1 || stars.getValue() > 5) {
                showError("Sterne müssen zwischen 1 und 5 liegen!");
                return;
            }

            Hotel h = isEdit ? hotel : new Hotel();
            h.setName(name.getValue());
            h.setStars(stars.getValue());
            h.setBranches(branches.getValue());
            h.setRooms(rooms.getValue());
            h.setBeds(beds.getValue());

            hotelService.saveHotel(h);
            refreshGrid();
            dialog.close();
        });

        Button cancel = new Button("❌ Abbrechen", e -> dialog.close());
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        buttons.setSpacing(true);

        dialog.add(new VerticalLayout(name, stars, branches, rooms, beds, buttons));
        dialog.setWidth("500px");
        dialog.open();
    }

    private void refreshGrid() {
        String filter = searchField.getValue();
        if (filter == null || filter.isEmpty()) {
            grid.setItems(hotelService.getAllHotels());
        } else {
            grid.setItems(hotelService.getAllHotels()
                    .stream()
                    .filter(h -> h.getName().toLowerCase().contains(filter.toLowerCase()))
                    .toList());
        }
    }

    private void showError(String message) {
        Notification notification = Notification.show(message, 4000, Notification.Position.MIDDLE);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
