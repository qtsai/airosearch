package nl.tsai.airosearch;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String message;
    private final int status;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
