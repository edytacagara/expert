package pl.expert.ui.dictionary;

public enum AddEditErrorCode {

    BRAK_WARUNKOW_REGULY("Brak warunków reguły"),
    BRAK_WNIOSKU_REGULY("Brak wniosków reguły"),
    BRAK_ARGUMENTU_MODELU("Brak argumentu modelu"),
    BRAK_OPERATOROW_MODELU("Brak operatorów modelu"),
    BRAK_WARTOSCI_MODELU("Brak wartości modelu"),
    BRAK_WNIOSKU_MODELU("Brak wniosku modelu"),
    BRAK_OGRANICZEN("Brak ograniczeń");

    String validationErrorCode;

    private AddEditErrorCode(String validationErrorCode) {
        this.validationErrorCode = validationErrorCode;
    }

    public String getValidationErrorCode() {
        return validationErrorCode;
    }

}
