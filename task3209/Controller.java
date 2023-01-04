package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.UndoListener;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view) {
        this.view = view;
    }

    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);

        view.init();
        controller.init();


    }

    public void exit() {
        System.exit(0);
    }

    public void init(){
        createNewDocument();
    }

    public HTMLDocument getDocument() {
        return document;
    }

    public void resetDocument() {
        UndoListener undo = view.getUndoListener();
        if (document != null) {
            document.removeUndoableEditListener(undo);
        }
        document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        document.addUndoableEditListener(undo);
        view.update();
    }

    public void setPlainText(String text) {
        resetDocument();

        try (StringReader sr = new StringReader(text)) {
            new HTMLEditorKit().read(sr, document, 0);
        } catch (IOException | BadLocationException e) {
            ExceptionHandler.log(e);
        }
    }
    
    public String getPlainText() {
        try (StringWriter sw = new StringWriter()) {
            new HTMLEditorKit().write(sw, document, 0 , document.getLength());
            return sw.toString();
        } catch (IOException | BadLocationException e) {
            ExceptionHandler.log(e);
        }
        return null;
    }

    public void createNewDocument() {
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML редактор");
        currentFile = null;
    }

    public void openDocument() {
    }

    public void saveDocument() {
    }

    public void saveDocumentAs() {
    }
}
