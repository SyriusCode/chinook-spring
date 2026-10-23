package org.bvn28065.repositorio;

import org.bvn28065.modelo.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

}
