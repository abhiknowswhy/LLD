# Text Editor

## Problem Statement
Design a text editor that supports basic text operations (insert, delete, cursor movement), undo/redo functionality, clipboard operations (cut, copy, paste), and find/replace.

## Requirements
- Text buffer with cursor position tracking
- Insert and delete text at cursor
- Cursor movement (left, right, to position, to start/end)
- Undo/redo using Command pattern
- Clipboard operations (cut, copy, paste) with selection support
- Find and replace functionality
- Line and column tracking

## Class Diagram

```mermaid
classDiagram
    class TextEditor {
        -TextBuffer buffer
        -CommandHistory commandHistory
        -Clipboard clipboard
        +insert(String text) void
        +delete(int count) void
        +moveCursor(int position) void
        +undo() void
        +redo() void
        +select(int start, int end) void
        +cut() void
        +copy() void
        +paste() void
        +find(String query) List~Integer~
        +replace(String query, String replacement) int
    }

    class TextBuffer {
        -StringBuilder content
        -int cursorPosition
        -int selectionStart
        -int selectionEnd
        +insert(int position, String text) void
        +delete(int start, int end) void
        +getText() String
        +getSelectedText() String
        +setCursor(int position) void
        +getLine() int
        +getColumn() int
    }

    class Command {
        <<interface>>
        +execute() void
        +undo() void
    }

    class InsertCommand {
        -TextBuffer buffer
        -int position
        -String text
    }

    class DeleteCommand {
        -TextBuffer buffer
        -int start
        -int end
        -String deletedText
    }

    class CommandHistory {
        -Deque~Command~ undoStack
        -Deque~Command~ redoStack
        +execute(Command) void
        +undo() void
        +redo() void
    }

    class Clipboard {
        -String content
        +copy(String text) void
        +paste() String
    }

    TextEditor --> TextBuffer : uses
    TextEditor --> CommandHistory : uses
    TextEditor --> Clipboard : uses
    CommandHistory --> Command : manages
    InsertCommand ..|> Command
    DeleteCommand ..|> Command
    InsertCommand --> TextBuffer : modifies
    DeleteCommand --> TextBuffer : modifies
```

> **Note:** This project is currently a stub. The class diagram above represents a suggested design for implementation.

## Potential Discussion Points
- How to implement syntax highlighting with the Visitor pattern?
- How to support multiple cursors?
- How to implement collaborative editing (operational transformation)?
- How to add macro recording and playback?
- How to efficiently handle large files (rope data structure)?
