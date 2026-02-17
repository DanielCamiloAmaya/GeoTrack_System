package com.PPOOII.Proyecto.Services;

import com.PPOOII.Proyecto.Config.MailgunProperties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MailgunService {

    private final WebClient mailgun;
    private final String domain;

    public MailgunService(WebClient mailgunWebClient, MailgunProperties props) {
        this.mailgun = mailgunWebClient;
        this.domain  = props.getDomain();
    }

    /**
     * Envía un correo de bienvenida usando la plantilla "welcome_hospital" de Mailgun,
     * rellenando las variables v:login, v:password y v:apikey.
     */
    public void sendWelcomeEmail(String to, String login, String password, String apikey) {
 // 1) cargar imagen del banner
    ClassPathResource banner = new ClassPathResource("banner.jpg");

    // 2) preparar datos del formulario (ahora usando <String, Object>)
    MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
    form.add("from",     "CliniTech Solutions <no-reply@" + domain + ">");
    form.add("to",       to);
    form.add("subject",  "Bienvenido a CliniTech Solutions");
    form.add("template", "welcome_template");
    form.add("v:login",    login);
    form.add("v:password", password);
    form.add("v:apikey",   apikey);

    // 3) banner como inline
    form.add("inline", banner);

    // 4) enviar el correo
    mailgun.post()
        .uri("/messages")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(form))
        .retrieve()
        .bodyToMono(String.class)
        .block();
    }
}
