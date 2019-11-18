package com.ifpb.sistema_pagamento;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class BancoProdutor {
    public BancoProdutor(String msg) {
        String FILA_BANCO_VISA = "fila_banco_visa";
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");
        cf.setPort(5672);

        try {
            Connection connection = cf.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(FILA_BANCO_VISA, true, false, false, null);
            channel.basicPublish("", FILA_BANCO_VISA, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
