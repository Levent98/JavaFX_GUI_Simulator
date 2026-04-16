package com.example.deneme;
//sw senkrone testi junit5+latch.countdown
//duzeltme yapildi
import com.example.deneme.ControllerClasses.InventoryCellState;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryCellBasedDefinitionControllerTest {

    private InventoryCellBasedDefinitionController controller;
    private InventoryCellState inventorydata;

    @BeforeAll
    static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> latch.countDown());
        latch.await(5, TimeUnit.SECONDS);
    }

    @BeforeEach
    void setUp() {
        controller = new InventoryCellBasedDefinitionController();
        inventorydata = new InventoryCellState(1);
        controller.setData(inventorydata, 3);
        AMTB_Sim_Controller.getAktifAmtbIdList().clear();
    }

    @Test
    void testOnSave() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                controller.categoryCB.setValue("CHAFF");
                controller.muhimmatParcaCB.setValue("TEK");
                controller.dolulukDurumuCB.setValue("DOLU");

                controller.onSavebuttonInventoryClicked(null);

                assertEquals("CHAFF", inventorydata.getCategory().toString());
                assertEquals("TEK", inventorydata.getNumberofPieces().toString());
                assertEquals("DOLU", inventorydata.getDolulukDurumu().toString());
                assertTrue(AMTB_Sim_Controller.getAktifAmtbIdList().contains(AMTB_Sim_Controller.getAmtbNo()));
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Test zaman aşımına uğradı");
    }
}
