package org.bvn28065.repositorio;

import org.bvn28065.modelo.Artista;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository<Artista, Integer> {
}
