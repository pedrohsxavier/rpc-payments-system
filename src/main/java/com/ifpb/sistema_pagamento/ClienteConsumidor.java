package com.ifpb.sistema_pagamento;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Map;

public class ClienteConsumidor {

    public static void main(String[] args) throws Exception {
        System.out.println("Inicio Cliente Consumidor");
        String FILA_VISA_CLIENTE = "fila_visa_cliente";
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(FILA_VISA_CLIENTE, true, false, false, (Map)null);
        DeliverCallback callback = (consumerTag, delivery) -> {
          String msg = new String(delivery.getBody());
            System.out.println(msg);
        };
        channel.basicConsume(FILA_VISA_CLIENTE, true, callback, (consumerTag) -> {});
        System.out.println("Fim Cliente Consumidor");
    }

}
