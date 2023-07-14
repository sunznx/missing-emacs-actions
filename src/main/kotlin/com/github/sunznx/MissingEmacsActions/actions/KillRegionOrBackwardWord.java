package com.github.sunznx.MissingEmacsActions.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ide.CopyPasteManager;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;

public class KillRegionOrBackwardWord extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Editor editor = (Editor) event.getDataContext().getData(String.valueOf(Editor.class));
        if (editor == null) {
            return;
        }

        SelectionModel selectionModel = editor.getSelectionModel();
        String text = editor.getDocument().getText();

        int start;
        int end;
        if (selectionModel.hasSelection()) {
            start = selectionModel.getSelectionStart();
            end = selectionModel.getSelectionEnd();
            selectionModel.removeSelection();
        } else {
            CaretModel caretModel = editor.getCaretModel();
            int offset = caretModel.getOffset();
            start = offset;
            end = offset;

            while (start > 0 && Character.isSpaceChar(start-1)) {
                start--;
            }
            while (start > 0 && !Character.isSpaceChar(start-1)) {
                start--;
            }
        }

        String selectedText = text.substring(start, end);
        CopyPasteManager.getInstance().setContents(new StringSelection(selectedText));
        editor.getDocument().deleteString(start, end);
    }
}
