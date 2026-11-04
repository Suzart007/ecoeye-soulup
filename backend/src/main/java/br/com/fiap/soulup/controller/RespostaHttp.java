package br.com.fiap.soulup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/** Ajuda os controllers a responder 201 Created com o cabecalho Location. */
final class RespostaHttp {

    private RespostaHttp() {
    }

    static <T> ResponseEntity<T> criado(String recurso, Long id, T corpo) {
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(recurso + "/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).body(corpo);
    }
}
