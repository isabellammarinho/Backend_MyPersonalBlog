package org.br.generation.blogpessoal.repository;
	

import java.util.List;

import org.br.generation.blogpessoal.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long>{
    
    public List <Tema> findAllByDescricaoContainingIgnoreCase (String descricao);
}



