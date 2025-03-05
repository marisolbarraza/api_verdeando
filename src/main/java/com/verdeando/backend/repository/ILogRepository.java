package com.verdeando.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verdeando.backend.model.Log;
import java.util.List;
import com.verdeando.backend.model.TipoAccion;


public interface ILogRepository extends JpaRepository<Log, String> {
    List<Log> findByUsuarioId(String idUsuario);
    List<Log> findByTipoAccion(TipoAccion tipoAccion);
    List<Log> findByUsuarioIdAndTipoAccion(String IdUsuario, TipoAccion tipoAccion);
}
