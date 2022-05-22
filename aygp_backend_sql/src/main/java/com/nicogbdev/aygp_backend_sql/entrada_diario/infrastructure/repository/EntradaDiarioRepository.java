package com.nicogbdev.aygp_backend_sql.entrada_diario.infrastructure.repository;

import com.nicogbdev.aygp_backend_sql.entrada_diario.domain.entity.EntradaDiario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntradaDiarioRepository extends JpaRepository<EntradaDiario, Long> {
    List<EntradaDiario> findAllByUsuario_Id(Long id);
    List<EntradaDiario> findAllByUsuario_Username(String username);
    Optional<EntradaDiario> findByIdAndUsuario_Username(Long id, String username);
}
