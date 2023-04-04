package com.diegob.everyfit.usecase.getallitems;

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
class GetAllItemsUseCaseTest {

    @Mock
    ClothingItemRepository clothingItemRepository;

    GetAllItemsUseCase getAllItemsUseCase;

    @BeforeEach
    void init() {
        getAllItemsUseCase = new GetAllItemsUseCase(clothingItemRepository);
    }

    @Test
    @DisplayName("getAllItems")
    void get() {

        Mockito.when(clothingItemRepository.getAllItems()).thenReturn(DataMocks.allClothingItem());

        var result = getAllItemsUseCase.get();

        StepVerifier.create(result)
                .expectNextMatches(clothingItem -> clothingItem.getId().equals("1"))
                .expectNextMatches(clothingItem -> clothingItem.getId().equals("2"))
                .verifyComplete();

        Mockito.verify(clothingItemRepository, Mockito.times(1)).getAllItems();

    }

    @Test
    @DisplayName("getAllItemsEmpty")
    void getEmpty() {

        Mockito.when(clothingItemRepository.getAllItems()).thenReturn(DataMocks.allClothingItemEmpty());

        var result = getAllItemsUseCase.get();

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(clothingItemRepository, Mockito.times(1)).getAllItems();

    }
}