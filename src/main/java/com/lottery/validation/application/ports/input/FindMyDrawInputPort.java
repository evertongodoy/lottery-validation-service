package com.lottery.validation.application.ports.input;

import com.lottery.validation.application.dto.FindMyDrawRequestDTO;
import com.lottery.validation.application.dto.FindMyDrawResponseDTO;

public interface FindMyDrawInputPort {
    
    FindMyDrawResponseDTO findMyDraw(FindMyDrawRequestDTO findMyDrawRequestDTO);
    
}
