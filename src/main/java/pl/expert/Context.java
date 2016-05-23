package pl.expert;

import pl.expert.ui.dictionary.EditView;


public class Context {
    private final static Context instance = new Context();
    
    private EditView currentEditView;
    
    public static Context getInstance() {
        return instance;
    }

    public EditView getCurrentEditView() {
        return currentEditView;
    }

    public void setCurrentEditView(EditView currentEditView) {
        this.currentEditView = currentEditView;
    }
}
