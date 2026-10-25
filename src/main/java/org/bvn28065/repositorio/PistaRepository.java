package org.bvn28065.repositorio;

import org.bvn28065.modelo.Pista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PistaRepository extends JpaRepository<Pista, Integer> {

    long countByGeneroId(Integer generoId);

    long countByAlbumId(Integer albumId);

    // Top20 = solo las 20 primeras
    List<Pista> findTop20ByGeneroIdOrderByNombre(Integer generoId);

    List<Pista> findByAlbumIdOrderById(Integer albumId);

    // sum() devuelve NULL si el género no tiene pistas: por eso el resultado es un Optional
    @Query("select sum(p.precioUnitario) from Pista p where p.genero.id = :generoId")
    Optional<BigDecimal> sumarPrecios(@Param("generoId") Integer generoId);
}
