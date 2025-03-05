package com.verdeando.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.verdeando.backend.model.Log;
import com.verdeando.backend.model.TipoAccion;
import com.verdeando.backend.service.LogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/logs")
    public ResponseEntity<List<Log>> listarLogs() {
        return ResponseEntity.ok(logService.listarLogs());
    }

    @GetMapping("/logs/usuario/{idUsuario}")
    public ResponseEntity<List<Log>> buscarLogsPorUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(logService.buscarLogsPorUsuario(idUsuario));
    }

    @GetMapping("/logs/tipoAccion/{tipoAccion}")
    public ResponseEntity<List<Log>> buscarLogsPorTipoAccion(@PathVariable TipoAccion tipoAccion) {
        return ResponseEntity.ok(logService.buscarLogsPorTipoAccion(tipoAccion));
    }

    // @PostMapping("/logs")
    // public ResponseEntity<Log> guardarLog(Log log) {
    //     return ResponseEntity.ok(logService.guardarLog(log));
    // }
}
