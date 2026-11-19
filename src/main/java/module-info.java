module org.example.hellohibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.sql;
    requires java.naming;


    opens org.example.hellohibernate to javafx.base, javafx.fxml, org.hibernate.orm.core;
    exports org.example.hellohibernate;
}