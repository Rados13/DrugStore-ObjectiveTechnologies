package pl.edu.agh.to.drugstore.command;

public interface Command {

	void execute();

	String getName();

	void undo();

	void redo();
}
