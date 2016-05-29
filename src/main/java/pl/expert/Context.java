package pl.expert;

import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.ui.dictionary.EditView;


public class Context {
    private final static Context instance = new Context();

    private EditView currentEditView;
    private Knowledge knowledge;

    public static Context getInstance() {
        return instance;
    }

    public EditView getCurrentEditView() {
        return currentEditView;
    }

    public void setCurrentEditView(EditView currentEditView) {
        this.currentEditView = currentEditView;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }
}
