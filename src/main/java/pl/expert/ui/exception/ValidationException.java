package pl.expert.ui.exception;

import pl.expert.core.exception.ExpertSystemException;
import pl.expert.ui.dictionary.AddEditErrorCode;

public class ValidationException extends ExpertSystemException {

    AddEditErrorCode errorCode;

    public ValidationException(AddEditErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public AddEditErrorCode getErrorCode() {
        return errorCode;
    }
}
