package com.example.deneme;
import com.example.deneme.AMTBStateController;
import com.example.deneme.AMTB_Sim_Controller;
import com.example.deneme.ControllerClasses.AMTBState;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class AMTBStateControllerTest {

    private AMTBStateController controller;
    private AMTBState state;

    @BeforeAll
    static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS); // Toolkit başlatılana kadar bekle
    }

    @BeforeEach
    void setUp() {
        controller = new AMTBStateController();
        state = new AMTBState(1);
        controller.setData(state);
        AMTB_Sim_Controller.getAktifAmtbIdList().clear();
    }

    @Test
    void testOnSaveAMTBModAktif() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                controller.AmtbDurumBilgiCB.setValue("AKTIF");
                controller.AmtbModSecimCB.setValue("AMTB_MODE_ALIVE");
                controller.JettisonStateCB.setValue("JETTISON_STATE_RUNNING");

                controller.onSaveButtonClicked(null);

                assertEquals("AKTIF", state.getStateInfo().toString());
                assertEquals("AMTB_MODE_ALIVE", state.getAmtbMode().toString());
                assertEquals("JETTISON_STATE_RUNNING", state.getJettisonState().toString());
                assertTrue(AMTB_Sim_Controller.getAktifAmtbIdList().contains(AMTBStateController.amtbNo));
            } finally {
                latch.countDown();
            }
        });

        // UI thread bitene kadar bekle
        assertTrue(latch.await(5, TimeUnit.SECONDS), "Test zaman aşımına uğradı");
    }

    @Test
    void testOnSaveAMTBModPasif() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                controller.AmtbDurumBilgiCB.setValue("PASIF");
                controller.AmtbModSecimCB.setValue("AMTB_MODE_SBY");
                controller.JettisonStateCB.setValue("JETTISON_STATE_RUNNING");

                controller.onSaveButtonClicked(null);

                assertEquals("PASIF", state.getStateInfo().toString());
                assertEquals("AMTB_MODE_OFF", state.getAmtbMode().toString());
                assertEquals("JETTISON_STATE_NOT_STARTED", state.getJettisonState().toString());
                assertFalse(AMTB_Sim_Controller.getAktifAmtbIdList().contains(AMTBStateController.amtbNo));
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Test zaman aşımına uğradı");
    }
}
