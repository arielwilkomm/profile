package com.profile.documents;

import com.profile.records.viacep.EnderecoRecord;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "enderecos")
@DynamicUpdate
public class EnderecoDocument implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @Id
    @Indexed
    private String cep;
    private String logradouro;
    private String complemento;
    private String unidade;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

    public EnderecoDocument(EnderecoRecord enderecoRecord) {
        this.cep = enderecoRecord.cep();
        this.logradouro = enderecoRecord.logradouro();
        this.complemento = enderecoRecord.complemento();
        this.unidade = enderecoRecord.unidade();
        this.bairro = enderecoRecord.bairro();
        this.localidade = enderecoRecord.localidade();
        this.uf = enderecoRecord.uf();
        this.estado = enderecoRecord.estado();
        this.regiao = enderecoRecord.regiao();
        this.ibge = enderecoRecord.ibge();
        this.gia = enderecoRecord.gia();
        this.ddd = enderecoRecord.ddd();
        this.siafi = enderecoRecord.siafi();
    }
}