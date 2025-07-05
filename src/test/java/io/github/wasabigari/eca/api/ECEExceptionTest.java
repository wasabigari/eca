package io.github.wasabigari.eca.api;

import io.github.wasabigari.eca.annotation.ECE;
import io.github.wasabigari.eca.annotation.ECF;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ECEExceptionTest {
    @ECE(prefix = "SIGN_UP")
    public enum SignupErrorCode {
        @ECF(code = "0001", message = "Email Exist")
        EMAIL_EXIST,
        @ECF(code = "0002", message = "Email Unverified")
        UNVERIFIED_EMAIL
    }

    @Test
    void throw_EmailExist_ThrowECException() {
        ECException ecException = assertThrows(ECException.class, () -> {
            throw new ECException(SignupErrorCode.EMAIL_EXIST);
        });

        assertEquals("SIGN_UP_0001", ecException.getErrorCode().code(), "Exception ErrorCode Code should be SIGN_UP_0001");
        assertEquals("Email Exist", ecException.getErrorCode().message(), "Exception ErrorCode Message should be Email Exist");
    }
}
