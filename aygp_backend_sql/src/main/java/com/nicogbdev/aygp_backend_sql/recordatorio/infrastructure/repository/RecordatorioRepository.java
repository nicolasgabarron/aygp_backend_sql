package com.nicogbdev.aygp_backend_sql.recordatorio.infrastructure.repository;

import com.nicogbdev.aygp_backend_sql.recordatorio.domain.entity.Recordatorio;
import com.nicogbdev.aygp_backend_sql.suceso_clave.domain.entity.SucesoClave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {
    List<Recordatorio> findAllByUsuario_Id(Long id);
    List<Recordatorio> findAllByUsuario_Username(String username);
    Recordatorio findByIdAndUsuario_Username(Long id, String username);
}
