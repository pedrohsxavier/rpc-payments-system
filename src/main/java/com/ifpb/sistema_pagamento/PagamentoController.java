package com.ifpb.sistema_pagamento;

import com.rabbitmq.client.*;
import com.rabbitmq.tools.json.JSONWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class PagamentoController {

    @ResponseBody
    public String index() {
        return "index";
    }

    @CrossOrigin
    @RequestMapping("/pagamentoVisa")
    @ResponseBody
    public String pagagamentoVisa(RedirectAttributes ra) {
        System.out.println("Inicio controller pagamento");
        String FILA_CLIENTE_BANCO = "fila_cliente_banco";
        String FILA_VISA_CLIENTE = "fila_visa_cliente";

        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");
        cf.setPort(5672);

        String mensagem = "Fazendo pagamento pela visa.";

        try {
            Connection connection = cf.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(FILA_CLIENTE_BANCO, true, false, false, null);
            channel.basicPublish("", FILA_CLIENTE_BANCO, MessageProperties.PERSISTENT_TEXT_PLAIN, mensagem.getBytes());

            DeliverCallback callback = (consumerTag, delivery) -> {
                String msg = new String(delivery.getBody());
                System.out.println(msg);
            };
            channel.basicConsume(FILA_VISA_CLIENTE, true, callback, (consumerTag) -> {});
        }catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("Fim controller pagamento");
        JSONWriter jsonWriter = new JSONWriter();
        return jsonWriter.write("Pagamento realizado pela visa!");
        //ModelAndView mav = new ModelAndView("redirect:/");
        //mav.addObject("msg", mensagem);
        //ra.addFlashAttribute("msg", mensagem);
        //return mav;
    }

    @CrossOrigin
    @RequestMapping("/pagamentoMaster")
    @ResponseBody
    public String pagamentoMaster(RedirectAttributes ra) {
        System.out.println("Inicio controller pagamento");
        String FILA_CLIENTE_BANCO = "fila_cliente_banco";
        String FILA_MASTER_CLIENTE = "fila_master_cliente";

        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");
        cf.setPort(5672);

        String mensagem = "Fazendo pagamento pela master.";

        try {
            Connection connection = cf.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(FILA_CLIENTE_BANCO, true, false, false, null);
            channel.basicPublish("", FILA_CLIENTE_BANCO, MessageProperties.PERSISTENT_TEXT_PLAIN, mensagem.getBytes());

            DeliverCallback callback = (consumerTag, delivery) -> {
                String msg = new String(delivery.getBody());
                System.out.println(msg);
            };
            channel.basicConsume(FILA_MASTER_CLIENTE, true, callback, (consumerTag) -> {});
        }catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("Fim controller pagamento");
        JSONWriter jsonWriter = new JSONWriter();
        return jsonWriter.write("Pagamento realizado pela master!");
        //ModelAndView mav = new ModelAndView("redirect:/");
        //mav.addObject("msg", mensagem);
        //ra.addFlashAttribute("msg", mensagem);
        //return mav;
    }

}
