package com.PPOOII.Proyecto.Controller;

import java.util.List;

import com.PPOOII.Proyecto.Entities.Coordenadas;
import com.PPOOII.Proyecto.Services.CoordenadasServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Coordenadas", description = "Operaciones sobre coordenadas actuales")
@RestController
@RequestMapping("/Proyecto")
@CrossOrigin(origins = {"http://localhost:5501","http://127.0.0.1:5501"})
public class CoordenadasController {

    @Autowired
    @Qualifier("CoordenadasService")
    private CoordenadasServiceImpl coordenadaService;

    @Operation(
    summary = "Listar coordenadas",
    description = "Obtiene todas las coordenadas actuales de las personas"
    )
    @ApiResponse(responseCode = "200", description = "Listado de coordenadas exitoso")
    @GetMapping("/coordenadas")
    public List<Coordenadas> consultarAllCoordenadas() {
        return coordenadaService.consultarAllCoordenadas();
    }
}