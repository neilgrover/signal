package ca.i4s.foodguide.model;

import lombok.Getter;

public enum Gender {

    MALE("M"),
    FEMALE("F");

    @Getter
    private String symbol;

    Gender(String symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return symbol;
    }

    public static Gender valueOfSymbol(String symbol) {
        Gender gender;
        switch (symbol.toUpperCase()) {
            case "M":
                gender = Gender.MALE;
                break;
            case "F":
                gender = Gender.FEMALE;
                break;
            default:
                gender = Gender.valueOf(symbol.toUpperCase());
        }
        return gender;
    }
}
