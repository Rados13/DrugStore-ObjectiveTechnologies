package pl.edu.agh.to.drugstore.command;

/**
 * Interfejs zawiera wszystkie niezbędne metody do zarządzania obiektem w interfejsie graficznym aplikacji.
 */
public interface Command {

    void execute();

    String getName();

    void undo();

    void redo();
}
