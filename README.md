# Error Code Annotation (ECA)

Enum is a natural way to manage Error Code and to avoid large Enum that contain all
Error Code of project. We often split to multiple Error Code Enum, which cause boilerplate
code such as getter and setter as we can extend Enum to abstract Class.

```java
public interface ErrorCode {
    String getCode();
    String getMessage();
}

public enum SignupErrorCode implements ErrorCode {
    EMAIL_EXIST("0001", "Email Exist");
    
    private final String code;
    private final String message;
    
    SignupErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}

public enum LoginErrorCode implements ErrorCode {
    EMAIL_NOT_FOUND("0001", "Email Not Found");
    
    private final String code;
    private final String message;
    
    LoginErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
```

This project aid to solve this boilerplate by using annotation.

```java
@ECE(prefix = "SIGN_UP")
public enum SignupErrorCode {
    @ECF(code = "0001", message = "Email Exist")
    EMAIL_EXIST,
    @ECF(code = "0002", message = "Email Unverified")
    UNVERIFIED_EMAIL
}
```

In this example, `ECE` annotation use to mark enum as Error Code. To define code and
message of each Error Code use `ECF` annotation.

```java
import io.github.wasabigari.eca.api.ECException;

public class Test {
    public void test() throws ECException {
        throw new ECException(SignupErrorCode.Email_EXIST);
    }
    
    public void handler() {
        try {
            test();
        } catch (ECException e) {
            System.err.println("Code: " + e.getErrorCode().code() + ", Message: " + e.getErrorCode().message());
        }
    }
}
```