package com.projeto.doacoes.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.doacoes.dto.DoacaoDTO;
import com.projeto.doacoes.dto.DoacaoRequestDTO;
import com.projeto.doacoes.entity.Doacao;
import com.projeto.doacoes.repository.DoacaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoacaoService {
    
    private final DoacaoRepository doacaoRepository;
    
    @Transactional
    public DoacaoDTO incluirDoacao(DoacaoRequestDTO requestDTO) {
        log.info("Incluindo nova doação para CPF: {}", requestDTO.getCpfDoador());
        
        validarDoacao(requestDTO);
        
        Doacao doacao = convertToEntity(requestDTO);
        Doacao doacaoSalva = doacaoRepository.save(doacao);
        
        log.info("Doação incluída com sucesso. ID: {}", doacaoSalva.getId());
        return convertToDTO(doacaoSalva);
    }
    
    @Transactional(readOnly = true)
    public List<DoacaoDTO> consultarTodasDoacoes() {
        log.info("Consultando todas as doações");
        List<Doacao> doacoes = doacaoRepository.findAllByOrderByDataDoacaoDesc();
        return doacoes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public DoacaoDTO consultarDoacaoPorId(Long id) {
        log.info("Consultando doação por ID: {}", id);
        Doacao doacao = doacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doação não encontrada com ID: " + id));
        return convertToDTO(doacao);
    }
    
    @Transactional(readOnly = true)
    public List<DoacaoDTO> consultarDoacoesPorCpf(String cpf) {
        log.info("Consultando doações por CPF: {}", cpf);
        List<Doacao> doacoes = doacaoRepository.findByCpfDoador(cpf);
        return doacoes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<DoacaoDTO> consultarDoacoesPorNome(String nome) {
        log.info("Consultando doações por nome: {}", nome);
        List<Doacao> doacoes = doacaoRepository.findByNomeDoadorContainingIgnoreCase(nome);
        return doacoes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void deletarDoacao(Long id) {
        log.info("Deletando doação com ID: {}", id);
        
        if (!doacaoRepository.existsById(id)) {
            throw new RuntimeException("Doação não encontrada com ID: " + id);
        }
        
        doacaoRepository.deleteById(id);
        log.info("Doação deletada com sucesso. ID: {}", id);
    }
    
    @Transactional(readOnly = true)
    public BigDecimal calcularTotalDoacoesPorCpf(String cpf) {
        log.info("Calculando total de doações para CPF: {}", cpf);
        return doacaoRepository.sumValorByCpfDoador(cpf)
                .orElse(BigDecimal.ZERO);
    }
    
    private void validarDoacao(DoacaoRequestDTO requestDTO) {
        if (requestDTO.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da doação deve ser maior que 0,00");
        }
        
        // Validação adicional de CPF se necessário
        if (!isValidCPF(requestDTO.getCpfDoador())) {
            throw new IllegalArgumentException("CPF inválido");
        }
    }
    
    private boolean isValidCPF(String cpf) {
        // Implementação básica - apenas verifica se tem 11 dígitos
        return cpf != null && cpf.matches("\\d{11}");
    }
    
    private Doacao convertToEntity(DoacaoRequestDTO requestDTO) {
        Doacao doacao = new Doacao();
        doacao.setNomeDoador(requestDTO.getNomeDoador());
        doacao.setCpfDoador(requestDTO.getCpfDoador());
        doacao.setValor(requestDTO.getValor());
        doacao.setDataDoacao(requestDTO.getDataDoacao() != null ? 
                requestDTO.getDataDoacao() : LocalDateTime.now());
        doacao.setEmailDoador(requestDTO.getEmailDoador());
        return doacao;
    }
    
    private DoacaoDTO convertToDTO(Doacao doacao) {
        DoacaoDTO dto = new DoacaoDTO();
        dto.setId(doacao.getId());
        dto.setNomeDoador(doacao.getNomeDoador());
        dto.setCpfDoador(doacao.getCpfDoador());
        dto.setValor(doacao.getValor());
        dto.setDataDoacao(doacao.getDataDoacao());
        dto.setEmailDoador(doacao.getEmailDoador());
        dto.setDataCriacao(doacao.getDataCriacao());
        dto.setDataAtualizacao(doacao.getDataAtualizacao());
        return dto;
    }
}