package pl.edu.agh.to.drugstore.command;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandRegistry {

	private ObservableList<Command> commandStack = FXCollections
			.observableArrayList();

	private ObservableList<Command> redoStack = FXCollections
			.observableArrayList();

	public void executeCommand(Command command) {
		command.execute();
		commandStack.add(command);
	}

	public void redo() {
		if (!redoStack.isEmpty()){
			Command command = redoStack.remove(redoStack.size() - 1);
			command.redo();
			commandStack.add(command);
		}
	}

	public void undo() {
		if (!commandStack.isEmpty()){
			Command command = commandStack.remove(commandStack.size() - 1);
			command.undo();
			redoStack.add(command);
		}
	}

	public ObservableList<Command> getCommandStack() {
		return commandStack;
	}
}
