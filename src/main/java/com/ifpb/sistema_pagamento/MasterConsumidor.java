package com.ifpb.sistema_pagamento;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Map;

public class MasterConsumidor {

    public static void main(String[] args) throws Exception {
        System.out.println("Inicio Master Consumidor");
        String FILA_BANCO_MASTER = "fila_banco_master";
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(FILA_BANCO_MASTER, true, false, false, (Map)null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody());
            String msgRetorno = "Pagamento master realizado com sucesso!";
            try {
                new MasterProdutor(msgRetorno);
            }catch (Exception e) {
                e.printStackTrace();
            }
        };
        channel.basicConsume(FILA_BANCO_MASTER, true, callback, (consumerTag) -> {});
        System.out.println("Fim Master Consumidor");
    }

}
