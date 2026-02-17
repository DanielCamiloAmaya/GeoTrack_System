package com.PPOOII.Proyecto.Services;

import com.PPOOII.Proyecto.Config.RabbitMQConfig;
import com.PPOOII.Proyecto.Model.BienvenidaMensaje;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BienvenidaListener {

    private final MailgunService mailgunService;

    public BienvenidaListener(MailgunService mailgunService) {
        this.mailgunService = mailgunService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void recibeYEnvia(BienvenidaMensaje msg) {
        // este método se dispara cuando llegue un BienvenidaMensaje a la cola
        mailgunService.sendWelcomeEmail(
            msg.getTo(),
            msg.getLogin(),
            msg.getPassword(),
            msg.getApikey()
        );
    }
}
