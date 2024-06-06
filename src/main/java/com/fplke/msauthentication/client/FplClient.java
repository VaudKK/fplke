package com.fplke.msauthentication.client;

import com.fplke.msauthentication.client.dto.response.FplEntryResponse;
import com.fplke.msauthentication.exceptions.TeamIdException;
import com.fplke.msauthentication.exceptions.TeamIdNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service("fplClient")
public class FplClient {

    @Value("${fpl.url.getEntry}")
    private String getEntryUrl;

    private final WebClient webClient;

    public FplClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public FplEntryResponse getFplClient(Long teamId) {
        return webClient
                .get()
                .uri(getEntryUrl+teamId+"/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onRawStatus(value -> value == 400,clientResponse -> Mono.error(new TeamIdNotFoundException("Invalid team Id")))
                .bodyToMono(FplEntryResponse.class)
                .doOnError(throwable -> log.error("Error while making request", throwable))
                .onErrorResume(throwable -> Mono.error(new TeamIdException("Could not verify team Id")))
                .block();
    }
}
