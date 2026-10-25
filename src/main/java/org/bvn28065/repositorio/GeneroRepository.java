package org.bvn28065.repositorio;

import org.bvn28065.modelo.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

// Solo una interfaz: Spring crea la clase que la implementa al arrancar.
// JpaRepository<Genero, Integer> = "repositorio de Genero, cuyo id es un Integer"
public interface GeneroRepository extends JpaRepository<Genero, Integer> {

    // Método derivado: Spring lee el nombre y escribe la consulta
    List<Genero> findAllByOrderByNombre();

    // @Query con JPQL: cada fila es un Object[] {nombre, número de pistas}
    @Query("select g.nombre, count(p) from Genero g left join Pista p on p.genero = g "
            + "group by g.id, g.nombre order by g.nombre")
    List<Object[]> contarPistas();
}

