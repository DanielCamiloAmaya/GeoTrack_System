package com.PPOOII.Proyecto.Services;

import com.PPOOII.Proyecto.Config.RabbitMQConfig;
import com.PPOOII.Proyecto.Entities.Persona;
import com.PPOOII.Proyecto.Entities.Usuario;
import com.PPOOII.Proyecto.Model.BienvenidaMensaje;
import com.PPOOII.Proyecto.Repository.PersonaRepository;
import com.PPOOII.Proyecto.Repository.UsuarioRepository;
import com.PPOOII.Proyecto.Services.Interfaces.IPersonaService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonaServiceImpl implements IPersonaService {

    private final PersonaRepository      personaRepository;
    private final UsuarioRepository      usuarioRepository;
    private final CoordenadasServiceImpl coordenadasService;
    private final RabbitTemplate         rabbitTemplate;

    public PersonaServiceImpl(PersonaRepository personaRepository,
                            UsuarioRepository usuarioRepository,
                            CoordenadasServiceImpl coordenadasService,
                            RabbitTemplate rabbitTemplate) {
        this.personaRepository  = personaRepository;
        this.usuarioRepository  = usuarioRepository;
        this.coordenadasService = coordenadasService;
        this.rabbitTemplate     = rabbitTemplate;
    }

    @Override
    public Persona crearPersona(Persona persona) {
        // 1) Guarda la persona
        Persona p = personaRepository.save(persona);

        // 2) Crea el Usuario asociado
        Usuario u = new Usuario();
        u.setPersona(p);

        String login    = p.getPnombre() + p.getPapellido().charAt(0) + p.getId();
        String password = UUID.randomUUID().toString().substring(0, 8);
        String apikey   = UUID.randomUUID().toString();

        u.setLogin(login);
        u.setPassword(password);
        u.setApikey(apikey);
        usuarioRepository.save(u);
        p.setUsuario(u);

        // 3) Publica el mensaje de bienvenida en RabbitMQ
        BienvenidaMensaje msg = new BienvenidaMensaje(
            p.getEmail(), login, password, apikey
        );
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            RabbitMQConfig.ROUTING,
            msg
        );

        return p;
    }

    @Override
    public Persona actualizarPersona(Persona persona) {
        if (persona.getId() == null) {
            return null;
        }

        Optional<Persona> opt = personaRepository.findById(persona.getId());
        if (opt.isEmpty()) {
            return null;
        }

        Persona anterior = opt.get();
        persona.setUsuario(anterior.getUsuario());
        Persona actualizada = personaRepository.save(persona);

        // Si cambió la ubicación, actualiza sus coordenadas
        if (!actualizada.getUbicacion().equals(anterior.getUbicacion())) {
            try {
                coordenadasService.actualizarCoordenadaPersona(actualizada);
            } catch (IOException | InterruptedException e) {
                System.err.println("Error al actualizar coordenadas: " + e.getMessage());
            }
        }

        return actualizada;
    }

    @Override
    public boolean eliminarPersona(Long id) {
        try {
            usuarioRepository.deleteById(id);
            personaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Persona> listarPersonas() {
        return personaRepository.findAll();
    }

    @Override
    public Persona buscarPorId(Long id) {
        return personaRepository.findById(id).orElse(null);
    }

    @Override
    public Persona buscarPorIdentificacion(int identificacion) {
        return personaRepository.findByIdentificacion(identificacion);
    }

    @Override
    public List<Persona> buscarPorEdad(int edad) {
        return personaRepository.findByEdad(edad);
    }

    @Override
    public List<Persona> buscarPorPrimerApellido(String papellido) {
        return personaRepository.findByPapellido(papellido);
    }

    @Override
    public List<Persona> buscarPorPrimerNombre(String pnombre) {
        return personaRepository.findByPnombre(pnombre);
    }
}


