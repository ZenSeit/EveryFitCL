package com.diegob.everyfit.usecase.modifyquantity;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import com.diegob.everyfit.usecase.DataMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ModifyQuantityUseCaseTest {

    @Mock
    ClothingItemRepository clothingItemRepository;

    ModifyQuantityUseCase modifyQuantityUseCase;

    @BeforeEach
    void init() {
        modifyQuantityUseCase = new ModifyQuantityUseCase(clothingItemRepository);
    }

    @Test
    @DisplayName("modifyQuantity")
    void apply() {

        Mockito.when(clothingItemRepository.modifyQuantity("item-id",1)).thenReturn(
                DataMocks.itemById()
        );

        var result = modifyQuantityUseCase.apply("item-id",1);

        StepVerifier.create(result)
                .expectNextMatches(item -> item.getQuantity() == 1)
                .verifyComplete();

    }

    @Test
    @DisplayName("modifyQuantity_Fail")
    void apply_Fail() {

        Mockito.when(clothingItemRepository.modifyQuantity("item-id",1)).thenReturn(
                DataMocks.itemById()
        );

        var result = modifyQuantityUseCase.apply("item-id",1);

        StepVerifier.create(result)
                .expectNextMatches(item -> item.getQuantity() != 2)
                .verifyComplete();

    }
}