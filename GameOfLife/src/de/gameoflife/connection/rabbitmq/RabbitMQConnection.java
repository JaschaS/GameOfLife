/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.connection.rabbitmq;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Daniel
 */
public class RabbitMQConnection extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Button btn = new Button();
        btn.setText("Connect to RabbitMQ");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                ConnectionFactory factory= new ConnectionFactory();
                factory.setUsername("vsys");
                factory.setPassword("vsys");
                factory.setVirtualHost("vsyshost");
                factory.setHost("143.93.91.73");
                factory.setPort(5672);
                
                try {
                    Connection con= factory.newConnection();
                    if(con.isOpen()){
                        System.out.println("Connected");
                    } else{
                        System.out.println("Not Connected");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(RabbitMQConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
