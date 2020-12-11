package pl.edu.agh.to.drugstore.security;

import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordManager {

    public PasswordManager(EntityManager em) {
        this.dao = new PersonDAO(em);
    }

    private final PersonDAO dao;

    public String encodePassword(String plaintext) {
        return Base64.getEncoder().encodeToString(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    public Person verifyAndGetPerson(String login, String password) {
        Person person = dao.findByLogin(login);
        if (person != null && person.getPassword().equals(encodePassword(password))) {
            return person;
        } else return null;
    }
}
