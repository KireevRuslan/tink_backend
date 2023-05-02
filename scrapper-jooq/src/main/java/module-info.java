module com.example.scrapperjooq {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jooq.meta;
    requires org.jooq.codegen;


    opens com.example.scrapperjooq to javafx.fxml;
    exports com.example.scrapperjooq;
}