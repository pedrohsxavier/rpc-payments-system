package com.ifpb.sistema_pagamento;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Map;

public class VisaConsumidor {

    public static void main(String[] args) throws Exception {
        System.out.println("Inicio Visa Consumidor");
        String FILA_BANCO_VISA = "fila_banco_visa";
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(FILA_BANCO_VISA, true, false, false, (Map)null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody());
            String msgRetorno = "Pagamento visa realizado com sucesso!";
            try {
                new VisaProdutor(msgRetorno);
            }catch (Exception e) {
                e.printStackTrace();
            }
        };
        channel.basicConsume(FILA_BANCO_VISA, true, callback, (consumerTag) -> {});
        System.out.println("Fim Visa Consumidor");
    }

}
