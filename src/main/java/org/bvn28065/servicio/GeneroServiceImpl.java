package org.bvn28065.servicio;

import org.bvn28065.modelo.Artista;
import org.bvn28065.modelo.Genero;
import org.bvn28065.repositorio.ArtistaRepository;
import org.bvn28065.repositorio.GeneroRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GeneroServiceImpl implements GeneroService{

    private final GeneroRepository generoRepository;

    public GeneroServiceImpl(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    @Override
    public List<Genero> listar() {
        return generoRepository.findAllByOrderByNombre();
    }
}
