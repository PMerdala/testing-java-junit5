package pl.pmerdala.springframework.sfgpetclinic.fauxspring;

import pl.pmerdala.springframework.sfgpetclinic.model.PetType;

import java.text.ParseException;
import java.util.Locale;

public interface Formatter {
    String print(PetType petType, Locale locale);

    PetType parse(String text, Locale locale) throws ParseException;
}
