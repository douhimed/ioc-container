package org.adex;

class BeanContainerException extends RuntimeException {

    public BeanContainerException(BeanIoCExceptionReason reason) {
        super(reason.getReason());
    }

    public BeanContainerException(BeanIoCExceptionReason reason, Throwable cause) {
        super(reason.getReason(), cause);
    }

    public enum BeanIoCExceptionReason {
        NOT_A_BEAN("Not a bean");

        private String reason;

        BeanIoCExceptionReason(String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }
    }
}
