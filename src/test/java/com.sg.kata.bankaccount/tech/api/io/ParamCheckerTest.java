package org.account.manager.tech.api.io;

import org.account.manager.tech.api.exception.BadDataArgumentException;
import org.junit.Test;

import static org.account.manager.tech.api.exception.BadParameterErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class ParamCheckerTest {

    private final ParamChecker paramChecker = new ParamChecker();

    @Test
    public void should_throw_BadDataArgumentException_given_OperationRequest_with_Amount_null() {
        // Given --
        OperationRequest request = new OperationRequest();
        // When --
        BadDataArgumentException badDataArgumentException = catchThrowableOfType(() -> paramChecker.checkParams(request), BadDataArgumentException.class);
        // Then --
        assertThat(badDataArgumentException.getErrorCode()).isEqualTo(PARAM_NULL_OR_EMPTY);
        assertThat(badDataArgumentException.getMessage()).isEqualTo("Parameter Amount cant be null or empty");
    }

    @Test
    public void should_throw_BadDataArgumentException_given_OperationRequest_with_Amount_empty() {
        // Given --
        OperationRequest request = new OperationRequest();
        request.setAmount("");
        // When --
        BadDataArgumentException badDataArgumentException = catchThrowableOfType(() -> paramChecker.checkParams(request), BadDataArgumentException.class);
        // Then --
        assertThat(badDataArgumentException.getErrorCode()).isEqualTo(PARAM_NULL_OR_EMPTY);
        assertThat(badDataArgumentException.getMessage()).isEqualTo("Parameter Amount cant be null or empty");
    }

    @Test
    public void should_throw_BadDataArgumentException_given_OperationRequest_with_AccountNumber_null() {
        // Given --
        OperationRequest request = new OperationRequest();
        request.setAmount("10");
        // When --
        BadDataArgumentException badDataArgumentException = catchThrowableOfType(() -> paramChecker.checkParams(request), BadDataArgumentException.class);
        // Then --
        assertThat(badDataArgumentException.getErrorCode()).isEqualTo(PARAM_NULL_OR_EMPTY);
        assertThat(badDataArgumentException.getMessage()).isEqualTo("Parameter AccountNumber cant be null or empty");
    }

    @Test
    public void should_throw_BadDataArgumentException_given_OperationRequest_with_AccountNumber_empty() {
        // Given --
        OperationRequest request = new OperationRequest();
        request.setAmount("10");
        request.setAccountNumber("");
        // When --
        BadDataArgumentException badDataArgumentException = catchThrowableOfType(() -> paramChecker.checkParams(request), BadDataArgumentException.class);
        // Then --
        assertThat(badDataArgumentException.getErrorCode()).isEqualTo(PARAM_NULL_OR_EMPTY);
        assertThat(badDataArgumentException.getMessage()).isEqualTo("Parameter AccountNumber cant be null or empty");
    }

    @Test
    public void should_throw_BadDataArgumentException_given_OperationRequest_with_Amount_XXX() {
        // Given --
        OperationRequest request = new OperationRequest();
        request.setAmount("XXX");
        request.setAccountNumber("SG_12345645");
        // When --
        BadDataArgumentException badDataArgumentException = catchThrowableOfType(() -> paramChecker.checkParams(request), BadDataArgumentException.class);
        // Then --
        assertThat(badDataArgumentException.getErrorCode()).isEqualTo(PARAM_NUMBER_FORMAT_ERROR);
        assertThat(badDataArgumentException.getMessage()).isEqualTo("Parameter Amount should be a number");
    }

    @Test
    public void should_throw_BadDataArgumentException_given_OperationRequest_with_Amount_Negatif() {
        // Given --
        OperationRequest request = new OperationRequest();
        request.setAmount("-10");
        request.setAccountNumber("SG_12345645");
        // When --
        BadDataArgumentException badDataArgumentException = catchThrowableOfType(() -> paramChecker.checkParams(request), BadDataArgumentException.class);
        // Then --
        assertThat(badDataArgumentException.getErrorCode()).isEqualTo(PARAM_NEGATIVE);
        assertThat(badDataArgumentException.getMessage()).isEqualTo("Parameter Amount cant be negative");
    }
}