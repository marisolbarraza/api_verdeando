package com.verdeando.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verdeando.backend.model.Log;
import com.verdeando.backend.model.TipoAccion;
import com.verdeando.backend.model.Usuario;
import com.verdeando.backend.repository.ILogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {

    @Autowired
    private ILogRepository logRepository;
    
    public List<Log> listarLogs() {
        return logRepository.findAll();
    }

    public Log guardarLog(Usuario usuario, TipoAccion tipoAccion, String detalle) {
        Log log = Log.builder()
                .fechaAccion(LocalDateTime.now())
                .usuario(usuario)
                .tipoAccion(tipoAccion)
                .detalle(detalle)
                .build();
        return logRepository.save(log);
    }

    public List<Log> buscarLogsPorUsuario(String idUsuario) {
        return logRepository.findByUsuarioId(idUsuario);
    }

    public List<Log> buscarLogsPorTipoAccion(TipoAccion tipoAccion) {
        return logRepository.findByTipoAccion(tipoAccion);
    }



}
