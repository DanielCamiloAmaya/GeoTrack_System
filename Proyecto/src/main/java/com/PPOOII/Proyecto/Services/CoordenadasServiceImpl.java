package com.PPOOII.Proyecto.Services;

import com.PPOOII.Proyecto.ApisGoogleMaps.Geocoder;
import com.PPOOII.Proyecto.Entities.Coordenadas;
import com.PPOOII.Proyecto.Entities.CoordenadasHistoricas;
import com.PPOOII.Proyecto.Entities.Persona;
import com.PPOOII.Proyecto.Repository.CoordenadasHistoricasRepository;
import com.PPOOII.Proyecto.Repository.CoordenadasRepository;
import com.PPOOII.Proyecto.Repository.PersonaRepository;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service("CoordenadasService")
public class CoordenadasServiceImpl {

    private final CoordenadasRepository              actualRepo;
    private final CoordenadasHistoricasRepository    histRepo;
    private final PersonaRepository                  personaRepo;
    private final Geocoder                           geocoder;

    public CoordenadasServiceImpl(CoordenadasRepository actualRepo,
                                CoordenadasHistoricasRepository histRepo,
                                PersonaRepository personaRepo,
                                Geocoder geocoder) {
        this.actualRepo  = actualRepo;
        this.histRepo    = histRepo;
        this.personaRepo = personaRepo;
        this.geocoder    = geocoder;
    }
    public List<Coordenadas> consultarAllCoordenadas() {
        return actualRepo.findAll();
    }

    /**
     * Cada 3 minutos refresca coordenadas:
     *  - antes de sobreescribir en `coordenadas`, guarda la antigua en el histórico.
     */
    public void actualizarCoordenadas() {
        List<Persona> personas = personaRepo.findAll();
        for (Persona persona : personas) {
            String dir = persona.getUbicacion();
            if (dir != null && !dir.isBlank()) {
                try {
                    String latLng = geocoder.getLatLng(dir);
                    if (latLng == null) continue;

                    String[] p = latLng.split(",");
                    Double lat = Double.valueOf(p[0]), lng = Double.valueOf(p[1]);

                    Optional<Coordenadas> optActual = actualRepo.findByPersonaId(persona.getId());
                    if (optActual.isPresent()) {
                        // 1) histórico <- registro viejo
                        Coordenadas vieja = optActual.get();
                        CoordenadasHistoricas h = new CoordenadasHistoricas();
                        h.setPersona(vieja.getPersona());
                        h.setNombrePersona(vieja.getNombrePersona());
                        h.setLatitud(vieja.getLatitud());
                        h.setLongitud(vieja.getLongitud());
                        histRepo.save(h);

                        // 2) actualizar tabla “actual”
                        vieja.setLatitud(lat);
                        vieja.setLongitud(lng);
                        actualRepo.save(vieja);

                    } else {
                        // no existía: insertamos nueva “actual”
                        Coordenadas nueva = new Coordenadas();
                        nueva.setPersona(persona);
                        nueva.setNombrePersona(persona.getPnombre() + " " + persona.getPapellido());
                        nueva.setLatitud(lat);
                        nueva.setLongitud(lng);
                        actualRepo.save(nueva);
                    }

                } catch (IOException | InterruptedException ex) {
                    System.err.println("Error al geocodificar “" + dir + "”: " + ex.getMessage());
                }
            }
        }
    }
    public void actualizarCoordenadaPersona(Persona persona) throws IOException, InterruptedException {
        String dir = persona.getUbicacion();
        if (dir != null && !dir.isBlank()) {
            String latLng = geocoder.getLatLng(dir);
            if (latLng == null) return;
    
            String[] p = latLng.split(",");
            Double lat = Double.valueOf(p[0]), lng = Double.valueOf(p[1]);
    
            Optional<Coordenadas> optActual = actualRepo.findByPersonaId(persona.getId());
            if (optActual.isPresent()) {
                Coordenadas vieja = optActual.get();
    
                CoordenadasHistoricas h = new CoordenadasHistoricas();
                h.setPersona(vieja.getPersona());
                h.setNombrePersona(vieja.getNombrePersona());
                h.setLatitud(vieja.getLatitud());
                h.setLongitud(vieja.getLongitud());
                histRepo.save(h);
    
                vieja.setLatitud(lat);
                vieja.setLongitud(lng);
                actualRepo.save(vieja);
            } else {
                Coordenadas nueva = new Coordenadas();
                nueva.setPersona(persona);
                nueva.setNombrePersona(persona.getPnombre() + " " + persona.getPapellido());
                nueva.setLatitud(lat);
                nueva.setLongitud(lng);
                actualRepo.save(nueva);
            }
        }
    }
}

    
    

