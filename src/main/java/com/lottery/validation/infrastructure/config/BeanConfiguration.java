package com.lottery.validation.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.lottery.validation.application.ports.input.FindLotteryInputPort;
import com.lottery.validation.application.ports.input.FindMyDrawInputPort;
import com.lottery.validation.application.ports.input.FindTopLotteryInputPort;
import com.lottery.validation.application.ports.input.SaveLotteryInputPort;
import com.lottery.validation.application.ports.input.SendVerifiedUserDrawInputPort;
import com.lottery.validation.application.ports.input.SimulateLotteryDrawInputPort;
import com.lottery.validation.application.ports.input.UserDrawInputPort;
import com.lottery.validation.application.ports.input.UserInputPort;
import com.lottery.validation.application.ports.input.VerifyUserDrawInputPort;
import com.lottery.validation.application.ports.output.FindLotteryOutputPort;
import com.lottery.validation.application.ports.output.FindMyDrawOutputPort;
import com.lottery.validation.application.ports.output.FindTopLotteryOutputPort;
import com.lottery.validation.application.ports.output.SaveLotteryOutputPort;
import com.lottery.validation.application.ports.output.SendVerifiedUserDrawOutputPort;
import com.lottery.validation.application.ports.output.SimulateLotteryDrawOutputPort;
import com.lottery.validation.application.ports.output.UserDrawOutputPort;
import com.lottery.validation.application.ports.output.UserOutputPort;
import com.lottery.validation.application.ports.output.VerifyUserDrawOutputPort;
import com.lottery.validation.application.usecases.lottery.FindLotteryUseCase;
import com.lottery.validation.application.usecases.lottery.FindTopLotteryUseCase;
import com.lottery.validation.application.usecases.lottery.SaveLotteryUseCase;
import com.lottery.validation.application.usecases.lottery.SimulateLotteryDrawUseCase;
import com.lottery.validation.application.usecases.user.FindMyDrawUseCase;
import com.lottery.validation.application.usecases.user.SendVerifiedUserDrawUseCase;
import com.lottery.validation.application.usecases.user.UserDrawUseCase;
import com.lottery.validation.application.usecases.user.UserUseCase;
import com.lottery.validation.application.usecases.user.VerifyUserDrawUseCase;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserInputPort createUserInputPort(UserOutputPort userOutputPort) {
        return new UserUseCase(userOutputPort);
    }

    @Bean
    public UserDrawInputPort userDrawInputPort(UserDrawOutputPort userDrawOutputPort, UserOutputPort userOutputPort) {
        return new UserDrawUseCase(userDrawOutputPort, userOutputPort);
    }

    @Bean
    public FindMyDrawInputPort findMyDrawInputPort(FindMyDrawOutputPort findMyDrawOutputPort, UserOutputPort userOutputPort) {
        return new FindMyDrawUseCase(findMyDrawOutputPort, userOutputPort);
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
    public FindTopLotteryInputPort findTopLotteryInputPort(FindTopLotteryOutputPort findTopLotteryOutputPort) {
        return new FindTopLotteryUseCase(findTopLotteryOutputPort);
    }

    @Bean
    public SimulateLotteryDrawInputPort simulateLotteryDrawInputPort(SimulateLotteryDrawOutputPort simulateLotteryDrawOutputPort) {
        return new SimulateLotteryDrawUseCase(simulateLotteryDrawOutputPort);
    }

    @Bean
    public VerifyUserDrawInputPort verifyUserDrawInputPort(VerifyUserDrawOutputPort verifyUserDrawOutputPort) {
        return new VerifyUserDrawUseCase(verifyUserDrawOutputPort);
    }

    @Bean
    public SendVerifiedUserDrawInputPort sendVerifiedUserDrawInputPort(
            SendVerifiedUserDrawOutputPort sendVerifiedUserDrawOutputPort,
            UserDrawOutputPort userDrawOutputPort,
            UserOutputPort userOutputPort,
            WebClient.Builder webClientBuilder) {
        return new SendVerifiedUserDrawUseCase(sendVerifiedUserDrawOutputPort, userDrawOutputPort, userOutputPort, webClientBuilder);
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
