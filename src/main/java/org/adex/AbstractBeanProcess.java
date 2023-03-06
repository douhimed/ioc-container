package org.adex;

import java.util.Objects;

abstract class AbstractBeanProcess {

    protected AbstractBeanProcess nextProcessor;

    public void setNextProcessor(AbstractBeanProcess nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public void initProcessing() {
        this.process();
        if(Objects.nonNull(nextProcessor))
            nextProcessor.initProcessing();
    }

    protected abstract void process();
}
