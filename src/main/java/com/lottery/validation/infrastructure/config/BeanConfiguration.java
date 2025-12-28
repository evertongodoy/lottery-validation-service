package com.lottery.validation.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.lottery.validation.application.ports.input.FindLotteryInputPort;
import com.lottery.validation.application.ports.input.SaveLotteryInputPort;
import com.lottery.validation.application.ports.input.UserInputPort;
import com.lottery.validation.application.ports.output.FindLotteryOutputPort;
import com.lottery.validation.application.ports.output.SaveLotteryOutputPort;
import com.lottery.validation.application.ports.output.UserOutputPort;
import com.lottery.validation.application.usecases.lottery.FindLotteryUseCase;
import com.lottery.validation.application.usecases.lottery.SaveLotteryUseCase;
import com.lottery.validation.application.usecases.user.UserUseCase;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserInputPort createUserInputPort(UserOutputPort userOutputPort) {
        return new UserUseCase(userOutputPort);
    }

    @Bean
    public SaveLotteryInputPort saveLotteryInputPort(SaveLotteryOutputPort saveLotteryOutputPort, 
                                                      WebClient.Builder webClientBuilder) {
        return new SaveLotteryUseCase(saveLotteryOutputPort, webClientBuilder);
    }

    @Bean
    public FindLotteryInputPort findLotteryInputPort(FindLotteryOutputPort findLotteryOutputPort) {
        return new FindLotteryUseCase(findLotteryOutputPort);
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
