package com.projeto.doacoes.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoacaoRequestDTO {
    
    @NotBlank(message = "Nome do doador é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nomeDoador;
    
    @NotBlank(message = "CPF do doador é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos")
    private String cpfDoador;
    
    @NotNull(message = "Valor da doação é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor da doação deve ser maior que 0,00")
    @Digits(integer = 8, fraction = 2, message = "Valor deve ter no máximo 8 dígitos inteiros e 2 decimais")
    private BigDecimal valor;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataDoacao;
    
    @Email(message = "Email deve ter um formato válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String emailDoador;
}