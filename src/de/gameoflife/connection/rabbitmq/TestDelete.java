/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.connection.rabbitmq;

import de.gameoflife.connection.rmi.GameHandler;

/**
 *
 * @author Daniel
 */
public class TestDelete {
    public static void main(String[] args){
        RabbitMQConnection con = new RabbitMQConnection();
        GameHandler.init();
        GameHandler handler = GameHandler.getInstance();
        int id=handler.getGameList(4).get(0).getGameId();
        System.out.println("id ist:"+id);
        con.deleteGame(4, id);
        handler.closeConnection();
        con.closeConnection();
    }
}
