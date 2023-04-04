package com.diegob.everyfit.usecase.registeritem;

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
class RegisterItemUseCaseTest {

    @Mock
    ClothingItemRepository clothingItemRepository;

    RegisterItemUseCase registerItemUseCase;

    @BeforeEach
    void init() {
        registerItemUseCase = new RegisterItemUseCase(clothingItemRepository);
    }

    @Test
    @DisplayName("registerItem")
    void apply() {

        Mockito.when(clothingItemRepository.registerItem(ArgumentMatchers.any(ClothingItem.class))).thenReturn(DataMocks.itemById());

        var result = registerItemUseCase.apply(DataMocks.rawItem());

        StepVerifier.create(result)
                .expectNextMatches(item -> item.getId().equals("1"))
                .verifyComplete();
        Mockito.verify(clothingItemRepository).registerItem(ArgumentMatchers.any(ClothingItem.class));

    }

    //Add a test when it fails from a throw exception
}