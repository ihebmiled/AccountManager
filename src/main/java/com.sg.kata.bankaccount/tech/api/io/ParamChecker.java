package org.account.manager.tech.api.io;

import org.account.manager.tech.api.exception.BadDataArgumentException;
import org.springframework.stereotype.Service;

import static org.account.manager.tech.api.exception.BadParameterErrorCode.*;
import static org.springframework.util.StringUtils.isEmpty;

@Service
public class ParamChecker {

    public void checkParams(OperationRequest operationRequest) {
        if (isEmpty(operationRequest.getAmount())) {
            throw new BadDataArgumentException(PARAM_NULL_OR_EMPTY, "Parameter Amount cant be null or empty");
        }
        if (isEmpty(operationRequest.getAccountNumber())) {
            throw new BadDataArgumentException(PARAM_NULL_OR_EMPTY, "Parameter AccountNumber cant be null or empty");
        }
        checkAmountNumberPositive(operationRequest);
    }

    private void checkAmountNumberPositive(OperationRequest operationRequest) {
        try {
            float amountFloat = Float.parseFloat(operationRequest.getAmount());
            if (amountFloat < 0) {
                throw new BadDataArgumentException(PARAM_NEGATIVE, "Parameter Amount cant be negative");
            }
        } catch (NumberFormatException ex) {
            throw new BadDataArgumentException(PARAM_NUMBER_FORMAT_ERROR, "Parameter Amount should be a number");
        }
    }
}
