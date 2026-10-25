package org.bvn28065.repositorio;

import org.bvn28065.modelo.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PlayListRepository extends JpaRepository<PlayList, Integer> {

    // Cada fila: {id, nombre, número de pistas}
    @Query("select p.id, p.nombre, count(t) from PlayList p left join p.pistas t "
            + "group by p.id, p.nombre order by p.id")
    List<Object[]> contarPistas();
}
