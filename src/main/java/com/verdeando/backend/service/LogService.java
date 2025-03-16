package com.verdeando.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verdeando.backend.dtos.LogDTO;
import com.verdeando.backend.dtos.UsuarioDTO;
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
    
    public List<LogDTO> listarLogs() {
        List<Log> logs = logRepository.findAll();
        return logs.stream().map(this::convertirALogDTO).collect(Collectors.toList());
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

    public List<LogDTO> buscarLogsPorUsuario(String idUsuario) {
        List<Log> logs =logRepository.findByUsuarioId(idUsuario);
        return logs.stream().map(this::convertirALogDTO).collect(Collectors.toList());
    }

    public List<LogDTO> buscarLogsPorTipoAccion(TipoAccion tipoAccion) {
        List<Log> logs = logRepository.findByTipoAccion(tipoAccion);
        return logs.stream().map(this::convertirALogDTO).collect(Collectors.toList());
    }

    private LogDTO convertirALogDTO(Log log){
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .id(log.getUsuario().getId())
                .nombre(log.getUsuario().getNombre())
                .apellido(log.getUsuario().getApellido())
                .email(log.getUsuario().getEmail())
                .build();

        // Convertir Log a LogDTO
        return LogDTO.builder()
                .idLog(log.getIdLog())
                .tipoAccion(log.getTipoAccion())
                .usuario(usuarioDTO)
                .detalle(log.getDetalle())
                .fechaAccion(log.getFechaAccion().toString())
                .build();
    }

}
