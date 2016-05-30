package pl.expert.ui.dictionary;

public enum AddEditErrorCode {

    BRAK_WARUNKOW_REGULY("Brak warunków reguły"),
    BRAK_WNIOSKU_REGULY("Brak wniosków reguły"),
    BRAK_ARGUMENTU_MODELU("Brak argumentu modelu"),
    BRAK_OPERATOROW_MODELU("Brak operatorów modelu"),
    ZLE_OPERATORY_MODELU("Niepoprawny zestaw operatorów"),
    TAKIE_SAME_OPERATORY_MODELU("Operatory nie mogą być takie same"),
    ZA_DUZO_OPERATOROW_MODELU("Zbyt duża liczba operatorów"),
    BRAK_WARTOSCI_MODELU("Brak wartości modelu"),
    ZLE_WARTOSCI_MODELU("Błędne wartości (tylko liczby)"),
    TAKIE_SAME_WARTOSCI_MODELU("Wartości nie mogą być takie same"),
    ZA_DUZO_WARTOSCI_MODELU("Zbyt duża liczba wartości)"),
    NIEZGODNA_LICZBA_OPERATOROW_I_WARTOSCI("Niezgoda liczba operatorów i wartości"),
    BRAK_WNIOSKU_MODELU("Brak wniosku modelu"),
    BRAK_OGRANICZEN("Brak ograniczeń"),
    ZA_MALO_OGRANICZEN("Zbyt mała liczba ograniczeń (co najmniej dwa)");

    String validationErrorCode;

    private AddEditErrorCode(String validationErrorCode) {
        this.validationErrorCode = validationErrorCode;
    }

    public String getValidationErrorCode() {
        return validationErrorCode;
    }

}
