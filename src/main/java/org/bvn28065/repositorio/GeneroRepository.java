package org.bvn28065.repositorio;

import org.bvn28065.modelo.Genero;

import org.springframework.data.jpa.repository.JpaRepository;

// Solo una interfaz: Spring crea la clase que la implementa al arrancar.
// JpaRepository<Genero, Integer> = "repositorio de Genero, cuyo id es un Integer"
public interface GeneroRepository extends JpaRepository<Genero, Integer> {

}

