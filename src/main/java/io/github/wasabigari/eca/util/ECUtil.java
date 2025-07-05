package io.github.wasabigari.eca.util;

import io.github.wasabigari.eca.api.ErrorCode;
import io.github.wasabigari.eca.annotation.ECE;
import io.github.wasabigari.eca.annotation.ECF;

import java.lang.reflect.Field;
import java.util.Optional;

public class ECUtil {
    public static <E extends Enum<E>> Optional<ErrorCode> getErrorCode(E eceType) {
        try {
            Class<E> eceTypeClass = eceType.getDeclaringClass();
            if (!eceTypeClass.isAnnotationPresent(ECE.class)) {
                return Optional.empty();
            }

            ECE ece = eceTypeClass.getAnnotation(ECE.class);
            String prefix = ece.prefix();

            Field field = eceTypeClass.getField(eceType.name());
            if (field.isAnnotationPresent(ECF.class)) {
                ECF errorCodeMeta = field.getAnnotation(ECF.class);
                return Optional.of(eceToErrorCode(errorCodeMeta, prefix));
            }
        } catch (NoSuchFieldException e) {
            System.err.println("Error: Enum constant field not found - " + eceType.name() + ": " + e.getMessage());
        }
        return Optional.empty();
    }

    private static ErrorCode eceToErrorCode(ECF errorCodeMeta, String prefix) {
        String code = errorCodeMeta.code();

        if (prefix != null && !prefix.isEmpty()) {
            code = String.format("%s_%s", prefix, code);
        }

        return new ErrorCode(code, errorCodeMeta.message());
    }
}
