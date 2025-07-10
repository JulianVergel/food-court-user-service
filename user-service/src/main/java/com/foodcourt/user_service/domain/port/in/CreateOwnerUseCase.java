package com.foodcourt.user_service.domain.port.in;

import com.foodcourt.user_service.application.command.CreateOwnerCommand;
import com.foodcourt.user_service.application.response.OwnerResponse;

public interface CreateOwnerUseCase {
    OwnerResponse createOwner(CreateOwnerCommand command);
}
