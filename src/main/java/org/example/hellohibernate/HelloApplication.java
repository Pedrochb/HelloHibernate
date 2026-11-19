package org.example.hellohibernate;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HelloApplication extends Application {

    private SessionFactory sessionFactory;

    @Override
    public void start(Stage stage) {
        TableView<Equipo> tabla = new TableView<>();
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<Equipo, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Equipo, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Equipo, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        TableColumn<Equipo, Boolean> colDisponible = new TableColumn<>("Disponible");
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        tabla.getColumns().addAll(colId, colNombre, colCategoria, colDisponible);

        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            try (Session session = sessionFactory.openSession()) {
                List<Equipo> equipos = session.createQuery("FROM Equipo", Equipo.class).list();
                tabla.setItems(FXCollections.observableArrayList(equipos));
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "No se pudo conectar a la base de datos:" + e.getMessage()).showAndWait();
        }

        stage.setTitle("Equipos - JavaFX + Hibernate");
        stage.setScene(new Scene(new BorderPane(tabla), 700, 400));
        stage.show();
    }

    @Override
    public void stop() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}