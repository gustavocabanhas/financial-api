package com.gustavo.financial_api.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import com.gustavo.financial_api.dto.NotificationLogDTO;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

@Component
public class NotificationClient {

    private final RestTemplate restTemplate;

    // URL do seu Notification Service que está rodando na porta 8080
    private final String URL = "http://localhost:8080/api/notifications";

    public NotificationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void solicitarNotificacao(String email, String mensagem) {
        try {
            // Criamos o objeto que o Notification Service espera receber
            Map<String, Object> body = new HashMap<>();
            body.put("recipient", email);
            body.put("message", mensagem);

            // Faz o POST para o outro projeto
            restTemplate.postForObject(URL, body, String.class);

            System.out.println(">>> [INTEGRAÇÃO] Notificação enviada com sucesso para: " + email);
        } catch (Exception e) {
            // Se o outro projeto estiver desligado, o financeiro não trava, apenas avisa o erro
            System.err.println(">>> [ERRO INTEGRAÇÃO] Falha ao conectar com Notification Service: " + e.getMessage());
        }

    }

    public List<NotificationLogDTO> buscarHistoricoNotificacoes() {
        try {
            String url = "http://localhost:8080/api/notifications";
            NotificationLogDTO[] historico = restTemplate.getForObject(url, NotificationLogDTO[].class);

            if (historico != null) {
                return Arrays.asList(historico);
            }
            return Collections.emptyList();
        } catch (Exception e) {
            System.out.println("Erro ao buscar notificações: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}