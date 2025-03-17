package com.profile.utils;

import com.profile.documents.EnderecoDocument;
import com.profile.exceptions.ProfileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    private Util util;

    @BeforeEach
    void setUp() {
        util = new Util();
    }

    @Test
    void testParseObjectToJson_Success() {
        EnderecoDocument obj = EnderecoDocument.builder()
                .cep("12345-678")
                .logradouro("Rua Teste")
                .bairro("Centro")
                .localidade("Cidade Teste")
                .uf("SP")
                .build();
        String json = util.parseObjectToJson(obj);
        assertNotNull(json);
        assertTrue(json.contains("12345-678"));
        assertTrue(json.contains("Rua Teste"));
    }

    @Test
    void testParseObjectToJson_Exception() {
        assertThrows(ProfileException.class, () -> util.parseObjectToJson(new Object()));
    }

    @Test
    void testParseStringToObject_Success() {
        String json = "{\"cep\":\"12345-678\",\"logradouro\":\"Rua Teste\",\"bairro\":\"Centro\",\"localidade\":\"Cidade Teste\",\"uf\":\"SP\"}";
        EnderecoDocument obj = util.parseStringToObject(json, EnderecoDocument.class);
        assertNotNull(obj);
        assertEquals("12345-678", obj.getCep());
        assertEquals("Rua Teste", obj.getLogradouro());
    }

    @Test
    void testParseStringToObject_Exception() {
        String invalidJson = "{\"cep\":\"12345-678\",\"logradouro\":\"Rua Teste\",\"bairro\":}";
        assertThrows(ProfileException.class, () -> util.parseStringToObject(invalidJson, EnderecoDocument.class));
    }
}