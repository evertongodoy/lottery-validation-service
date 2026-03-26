package com.lottery.validation.infrastructure.adapters.input.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ServerWebExchange;

import com.lottery.validation.application.ports.input.SimulateLotteryDrawInputPort;
import com.lottery.validation.domain.enums.LotteryType;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequestMapping("/web")
public class LotteryWebController {

    private final SimulateLotteryDrawInputPort simulateLotteryDrawInputPort;

    public LotteryWebController(SimulateLotteryDrawInputPort simulateLotteryDrawInputPort) {
        this.simulateLotteryDrawInputPort = simulateLotteryDrawInputPort;
    }

    @GetMapping({"", "/", "/home"})
    public Mono<String> home(Model model, ServerWebExchange exchange) {
        log.info("[home] Acessando página inicial");
        
        // Verificar se há token JWT no header (para controlar menu)
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        boolean isAuthenticated = authHeader != null && authHeader.startsWith("Bearer ");
        
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("pageTitle", "Lottery Statistics");
        
        return Mono.just("lottery/home");
    }

    @GetMapping("/simulate")
    public Mono<String> simulatePage(Model model, ServerWebExchange exchange) {
        log.info("[simulatePage] Acessando página de simulação");
        
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        boolean isAuthenticated = authHeader != null && authHeader.startsWith("Bearer ");
        
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("pageTitle", "Simulação de Loteria");
        model.addAttribute("lotteryTypes", LotteryType.values());
        
        return Mono.just("lottery/simulate");
    }

    @PostMapping("/simulate/{lotteryType}")
    public Mono<String> simulateLottery(
            @PathVariable LotteryType lotteryType,
            @RequestParam("numbers") List<Integer> numbers,
            Model model,
            ServerWebExchange exchange) {
        
        log.info("[simulateLottery] Simulando loteria | lotteryType={}, numbers={}", lotteryType, numbers);
        
        try {
            var simulateDTO = simulateLotteryDrawInputPort.simulateLotteryDraw(lotteryType, numbers);
            
            model.addAttribute("lotteryType", lotteryType);
            model.addAttribute("selectedNumbers", numbers);
            model.addAttribute("matches", simulateDTO.getMatches());
            model.addAttribute("hasResults", true);
            
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            boolean isAuthenticated = authHeader != null && authHeader.startsWith("Bearer ");
            model.addAttribute("isAuthenticated", isAuthenticated);
            model.addAttribute("pageTitle", "Resultado da Simulação");
            
            return Mono.just("lottery/simulate-result");
            
        } catch (Exception e) {
            log.error("[simulateLottery] Erro ao simular loteria", e);
            model.addAttribute("error", "Erro ao processar simulação: " + e.getMessage());
            model.addAttribute("hasResults", false);
            return Mono.just("lottery/simulate");
        }
    }
}
