package com.PPOOII.Proyecto.Component;

import com.PPOOII.Proyecto.Services.CoordenadasServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final CoordenadasServiceImpl coordenadasService;

    public ScheduledTasks(CoordenadasServiceImpl coordenadasService) {
        this.coordenadasService = coordenadasService;
    }

    // Ejecutar cada 3 minutos
    @Scheduled(cron = "0 */3 * * * ?") 
    public void tareaActualizarUbicaciones() {
        System.out.println("Verificando y actualizando coordenadas...");
        coordenadasService.actualizarCoordenadas();
    }
}
