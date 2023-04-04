package com.diegob.everyfit.usecase.getitembyid;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
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
class GetItemByIdUseCaseTest {

    @Mock
    ClothingItemRepository clothingItemRepository;

    GetItemByIdUseCase getItemByIdUseCase;

    @BeforeEach
    void init() {
        getItemByIdUseCase = new GetItemByIdUseCase(clothingItemRepository);
    }

    @Test
    @DisplayName("getItemById")
    void apply() {

        Mockito.when(clothingItemRepository.getItemById("1")).thenReturn(DataMocks.itemById());

        var result = getItemByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextMatches(item -> item.getId().equals("1"))
                .verifyComplete();

        Mockito.verify(clothingItemRepository, Mockito.times(1)).getItemById("1");
    }

    @Test
    @DisplayName("getItemByIdEmpty")
    void applyEmpty() {

        Mockito.when(clothingItemRepository.getItemById("1")).thenReturn(DataMocks.itemByIdEmpty());

        var result = getItemByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(clothingItemRepository, Mockito.times(1)).getItemById("1");
    }
}