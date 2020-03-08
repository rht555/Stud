package com.rht.shpringbootwork.controller;

import com.rht.shpringbootwork.dto.AccessrokenDTO;
import com.rht.shpringbootwork.dto.GitHubUser;
import com.rht.shpringbootwork.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Authorize {
    @Autowired
    private GitHubProvider gitHubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clieentSecret;
    @Value("${github.redirect.url}")
    private String clientUrl;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,@RequestParam(name = "state") String state){
        AccessrokenDTO accessrokenDTO = new AccessrokenDTO();
        accessrokenDTO.setClient_id(clientId);
        accessrokenDTO.setClient_secret(clieentSecret);
        accessrokenDTO.setCode(code);
        accessrokenDTO.setRedirect_uri(clientUrl);
        accessrokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessrokenDTO);
        GitHubUser user = gitHubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
