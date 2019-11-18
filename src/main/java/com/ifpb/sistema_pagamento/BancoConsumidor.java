package com.ifpb.sistema_pagamento;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Map;

public class BancoConsumidor {

    public static void main(String[] args) throws Exception {
        System.out.println("Inicio Banco Consumidor");
        String FILA_CLIENTE_BANCO = "fila_cliente_banco";
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(FILA_CLIENTE_BANCO, true, false, false, (Map)null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody());
            try {
                new BancoProdutor(msg);
            }catch (Exception e) {
                e.printStackTrace();
            }
        };
        channel.basicConsume(FILA_CLIENTE_BANCO, true, callback, (consumerTag) -> {});
        System.out.println("Fim Banco Consumidor");
    }

}
