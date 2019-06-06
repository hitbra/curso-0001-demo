package com.test.demo.jms;

import com.test.demo.controller.StockController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JmsReceiver {

    public static final Logger LOG = LoggerFactory.getLogger(JmsReceiver.class);

    private StockController stockController;

    public JmsReceiver(StockController stockController) {
        this.stockController = stockController;
    }

    @JmsListener(destination = "${endpoints.jms.queues.stock}", containerFactory = "jsaFactory")
    @Transactional
    public void receiveMessage(JmsResponse response) {
        LOG.info("- - - - - - - - - Start of receiveMessage - - - - - - - - -");

        if (response == null) {
            LOG.info("Response message is null");
            return;
        }

        stockController.processStock(response.getId(), response.getAction(), response.getValue(), 0);

        LOG.info("- - - - - - - - - End of receiveMessage - - - - - - - - -");
    }
}
