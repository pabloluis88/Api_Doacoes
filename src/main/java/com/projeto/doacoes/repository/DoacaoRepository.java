package com.projeto.doacoes.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projeto.doacoes.entity.Doacao;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, Long> {
    
    // Buscar doações por CPF do doador
    List<Doacao> findByCpfDoador(String cpfDoador);
    
    // Buscar doações por nome do doador (case insensitive)
    List<Doacao> findByNomeDoadorContainingIgnoreCase(String nomeDoador);
    
    // Buscar doações por período
    @Query("SELECT d FROM Doacao d WHERE d.dataDoacao BETWEEN :dataInicio AND :dataFim")
    List<Doacao> findByPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                               @Param("dataFim") LocalDateTime dataFim);
    
    // Buscar doações por valor mínimo
    List<Doacao> findByValorGreaterThanEqual(BigDecimal valorMinimo);
    
    // Verificar se existe doação por CPF
    boolean existsByCpfDoador(String cpfDoador);
    
    // Buscar doações ordenadas por data (mais recentes primeiro)
    List<Doacao> findAllByOrderByDataDoacaoDesc();
    
    // Somar total de doações de um CPF
    @Query("SELECT SUM(d.valor) FROM Doacao d WHERE d.cpfDoador = :cpfDoador")
    Optional<BigDecimal> sumValorByCpfDoador(@Param("cpfDoador") String cpfDoador);
}
