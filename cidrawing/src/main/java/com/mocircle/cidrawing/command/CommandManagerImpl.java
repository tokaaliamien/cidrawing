package com.mocircle.cidrawing.command;

import com.mocircle.android.logging.CircleLog;

import java.util.List;
import java.util.Stack;

public class CommandManagerImpl implements CommandManager {

    private static final String TAG = "CommandManagerImpl";

    private Stack<DrawingCommand> undoCommandList = new Stack<>();
    private Stack<DrawingCommand> redoCommandList = new Stack<>();

    private String boardId;

    public CommandManagerImpl(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public void executeCommand(DrawingCommand command) {
        CircleLog.d(TAG, "Execute command: " + command.getClass().getSimpleName());
        command.setDrawingBoardId(boardId);
        command.doCommand();
        undoCommandList.push(command);
        redoCommandList.clear();
    }

    @Override
    public List<DrawingCommand> getCommandHistory() {
        return undoCommandList;
    }

    @Override
    public void undo() {
        if (!undoCommandList.isEmpty()) {
            DrawingCommand command = undoCommandList.pop();
            if (command != null) {
                command.undoCommand();
                redoCommandList.push(command);
            }
        }
    }

    @Override
    public void undoToCommand(DrawingCommand command) {

    }

    @Override
    public void redo() {
        if (!redoCommandList.isEmpty()) {
            DrawingCommand command = redoCommandList.pop();
            if (command != null) {
                command.redoCommand();
                undoCommandList.push(command);
            }
        }
    }

    @Override
    public void redoToCommand(DrawingCommand command) {

    }


}