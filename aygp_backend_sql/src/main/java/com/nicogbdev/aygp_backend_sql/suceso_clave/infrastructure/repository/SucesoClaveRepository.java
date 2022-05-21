package com.nicogbdev.aygp_backend_sql.suceso_clave.infrastructure.repository;

import com.nicogbdev.aygp_backend_sql.suceso_clave.domain.entity.SucesoClave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SucesoClaveRepository extends JpaRepository<SucesoClave, Long> {
    List<SucesoClave> findAllByUsuario_Id(Long id);
    List<SucesoClave> findAllByUsuario_Username(String username);
    SucesoClave findByIdAndUsuario_Username(Long id, String username);
}
