package com.projeto.doacoes.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome_doador", nullable = false, length = 100)
    private String nomeDoador;
    
    @Column(name = "cpf_doador", nullable = false, length = 11)
    private String cpfDoador;
    
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;
    
    @Column(name = "data_doacao", nullable = false)
    private LocalDateTime dataDoacao;
    
    @Column(name = "email_doador", length = 100)
    private String emailDoador;
    
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        if (this.dataDoacao == null) {
            this.dataDoacao = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}