package io.github.wasabigari.eca.api;

import io.github.wasabigari.eca.util.ECUtil;

public class ECException extends RuntimeException{
    private ErrorCode errorCode;

    public <E extends Enum<E>> ECException(E ece) {
        ECUtil.getErrorCode(ece).ifPresent(e -> this.errorCode = e);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
