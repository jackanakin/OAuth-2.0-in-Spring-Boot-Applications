package br.dev.kuhn.webapp.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import br.dev.kuhn.webapp.response.AlbumRest;

@Controller
public class AlbumController {

    @Autowired
    OAuth2AuthorizedClientService oClientService;

    // @Autowired
	// RestTemplate restTemplate;
    
    @Autowired
	WebClient webClient;

    @GetMapping("/albums")
    public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal, Authentication authentication1) {
        Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();

        // OAuth2AuthenticationToken oAuth2Token = OAuth2AuthenticationToken.class.cast( authentication2 );
        // OAuth2AuthorizedClient loadAuthorizedClient = oClientService.loadAuthorizedClient( 
        //     oAuth2Token.getAuthorizedClientRegistrationId(), 
        //     oAuth2Token.getName() );

        // String tokenValue = loadAuthorizedClient.getAccessToken().getTokenValue();

        String resourceUrl = "http://localhost:8082/albums";
        // HttpHeaders headers = new HttpHeaders();
		// headers.add("Authorization", "Bearer " + tokenValue);
        
        // HttpEntity<List<AlbumRest>> entity = new HttpEntity<>(headers);
		// ResponseEntity<List<AlbumRest>> responseEntity =  restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<List<AlbumRest>>() {});
		// List<AlbumRest> albums = responseEntity.getBody();

        List<AlbumRest> albums = webClient.get().uri(resourceUrl).retrieve().bodyToMono(new ParameterizedTypeReference<List<AlbumRest>>(){}).block();

        model.addAttribute("albums", albums );

        return "albums";
    }
}
