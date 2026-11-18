package org.bvn28065.servicio;

import org.bvn28065.dto.GeneroResumen;
import org.bvn28065.modelo.Genero;
import org.bvn28065.repositorio.ArtistaRepository;
import org.bvn28065.repositorio.PistaRepository;
import org.bvn28065.repositorio.GeneroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeneroServiceImpl implements GeneroService{

    private final GeneroRepository generoRepository;
    private final PistaRepository pistaRepository;

    public GeneroServiceImpl(GeneroRepository generoRepository, PistaRepository pistaRepository) {
        this.generoRepository = generoRepository;
        this.pistaRepository = pistaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GeneroResumen> listar() {
        List<GeneroResumen> resultado = new ArrayList<>();

        for (Genero genero : generoRepository.findAllByOrderByNombre()) {
            long numero = pistaRepository.countByGeneroId(genero.getId());
            resultado.add(new GeneroResumen(genero.getId(), genero.getNombre(), numero));
        }

        return resultado;
    }
}
