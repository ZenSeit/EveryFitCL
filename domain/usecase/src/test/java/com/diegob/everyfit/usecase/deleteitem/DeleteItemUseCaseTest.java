package com.diegob.everyfit.usecase.deleteitem;

import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteItemUseCaseTest {

    @Mock
    ClothingItemRepository clothingItemRepository;

    DeleteItemUseCase deleteItemUseCase;

    @BeforeEach
    void init() {
        deleteItemUseCase = new DeleteItemUseCase(clothingItemRepository);
    }

    @Test
    @DisplayName("deleteItem")
    void apply() {

        Mockito.when(clothingItemRepository.deleteItem("1")).thenReturn(
                Mono.just("Item deleted")
        );

        var result = deleteItemUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextMatches(res -> res.equalsIgnoreCase("Item deleted"))
                .verifyComplete();

    }

    @Test
    @DisplayName("deleteItemEmpty")
    void applyEmpty() {

        Mockito.when(clothingItemRepository.deleteItem("1")).thenReturn(
                Mono.empty()
        );

        var result = deleteItemUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

    }


}