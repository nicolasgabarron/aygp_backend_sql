package com.nicogbdev.aygp_backend_sql.recordatorio.infrastructure.repository;

import com.nicogbdev.aygp_backend_sql.recordatorio.domain.entity.Recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {
    List<Recordatorio> findAllByUsuario_IdOrderByFechaCreacionDesc(Long id);
    List<Recordatorio> findAllByUsuario_UsernameOrderByFechaCreacionDesc(String username);
    Optional<Recordatorio> findByIdAndUsuario_UsernameOrderByFechaCreacionDesc(Long id, String username);
}
