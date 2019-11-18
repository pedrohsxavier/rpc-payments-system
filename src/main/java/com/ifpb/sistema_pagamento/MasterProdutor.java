package com.ifpb.sistema_pagamento;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class MasterProdutor {

    public MasterProdutor(String msg) {

        String FILA_MASTER_CLIENTE = "fila_master_cliente";
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");
        cf.setPort(5672);

        try {
            Connection connection = cf.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(FILA_MASTER_CLIENTE, true, false, false, null);
            channel.basicPublish("", FILA_MASTER_CLIENTE, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
