package com.projeto.doacoes.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.doacoes.dto.DoacaoDTO;
import com.projeto.doacoes.dto.DoacaoRequestDTO;
import com.projeto.doacoes.service.DoacaoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/doacoes")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class DoacaoController {
    
    private final DoacaoService doacaoService;
    
    @PostMapping
    public ResponseEntity<DoacaoDTO> incluirDoacao(@Valid @RequestBody DoacaoRequestDTO requestDTO) {
        log.info("Recebida solicitação para incluir doação");
        try {
            DoacaoDTO doacaoIncluida = doacaoService.incluirDoacao(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(doacaoIncluida);
        } catch (IllegalArgumentException e) {
            log.error("Erro de validação ao incluir doação: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro interno ao incluir doação: {}", e.getMessage());
            throw new RuntimeException("Erro interno do servidor", e);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<DoacaoDTO>> consultarTodasDoacoes() {
        log.info("Recebida solicitação para consultar todas as doações");
        try {
            List<DoacaoDTO> doacoes = doacaoService.consultarTodasDoacoes();
            return ResponseEntity.ok(doacoes);
        } catch (Exception e) {
            log.error("Erro ao consultar doações: {}", e.getMessage());
            throw new RuntimeException("Erro interno do servidor", e);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DoacaoDTO> consultarDoacaoPorId(@PathVariable Long id) {
        log.info("Recebida solicitação para consultar doação por ID: {}", id);
        try {
            DoacaoDTO doacao = doacaoService.consultarDoacaoPorId(id);
            return ResponseEntity.ok(doacao);
        } catch (RuntimeException e) {
            log.error("Doação não encontrada com ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Erro ao consultar doação por ID: {}", e.getMessage());
            throw new RuntimeException("Erro interno do servidor", e);
        }
    }
    
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<DoacaoDTO>> consultarDoacoesPorCpf(@PathVariable String cpf) {
        log.info("Recebida solicitação para consultar doações por CPF: {}", cpf);
        try {
            List<DoacaoDTO> doacoes = doacaoService.consultarDoacoesPorCpf(cpf);
            return ResponseEntity.ok(doacoes);
        } catch (Exception e) {
            log.error("Erro ao consultar doações por CPF: {}", e.getMessage());
            throw new RuntimeException("Erro interno do servidor", e);
        }
    }
    
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<DoacaoDTO>> consultarDoacoesPorNome(@PathVariable String nome) {
        log.info("Recebida solicitação para consultar doações por nome: {}", nome);
        try {
            List<DoacaoDTO> doacoes = doacaoService.consultarDoacoesPorNome(nome);
            return ResponseEntity.ok(doacoes);
        } catch (Exception e) {
            log.error("Erro ao consultar doações por nome: {}", e.getMessage());
            throw new RuntimeException("Erro interno do servidor", e);
        }
    }
    
    @GetMapping("/total/cpf/{cpf}")
    public ResponseEntity<BigDecimal> calcularTotalDoacoesPorCpf(@PathVariable String cpf) {
        log.info("Recebida solicitação para calcular total de doações por CPF: {}", cpf);
        try {
            BigDecimal total = doacaoService.calcularTotalDoacoesPorCpf(cpf);
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            log.error("Erro ao calcular total por CPF: {}", e.getMessage());
            throw new RuntimeException("Erro interno do servidor", e);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDoacao(@PathVariable Long id) {
        log.info("Recebida solicitação para deletar doação com ID: {}", id);
        try {
            doacaoService.deletarDoacao(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Doação não encontrada para deletar com ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Erro ao deletar doação: {}", e.getMessage());
            throw new RuntimeException("Erro interno do servidor", e);
        }
    }
}