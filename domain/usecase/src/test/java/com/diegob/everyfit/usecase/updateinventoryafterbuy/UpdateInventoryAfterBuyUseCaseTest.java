package com.diegob.everyfit.usecase.updateinventoryafterbuy;

import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import com.diegob.everyfit.usecase.DataMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateInventoryAfterBuyUseCaseTest {

    @Mock
    ClothingItemRepository clothingItemRepository;

    UpdateInventoryAfterBuyUseCase updateInventoryAfterBuyUseCase;

    @BeforeEach
    void init() {
        updateInventoryAfterBuyUseCase = new UpdateInventoryAfterBuyUseCase(clothingItemRepository);
    }

    @Test
    @DisplayName("updateInventoryAfterBuy")
    void apply() {

        Mockito.when(clothingItemRepository.updateInventoryAfterBuy("itemId",1)).thenReturn(
                DataMocks.itemById()
        );

        var result = updateInventoryAfterBuyUseCase.apply("itemId",1);

        StepVerifier.create(result)
                .expectNextMatches(item -> item.getQuantity() == 1)
                .verifyComplete();


    }

    @Test
    @DisplayName("updateInventoryAfterBuy_Fail")
    void apply_Fail() {

        Mockito.when(clothingItemRepository.updateInventoryAfterBuy("itemId", 1)).thenReturn(
                DataMocks.itemById()
        );

        var result = updateInventoryAfterBuyUseCase.apply("itemId", 1);

        StepVerifier.create(result)
                .expectNextMatches(item -> item.getQuantity() != 2)
                .verifyComplete();

    }


}