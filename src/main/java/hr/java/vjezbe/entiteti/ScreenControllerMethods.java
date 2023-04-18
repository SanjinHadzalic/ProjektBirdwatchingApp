package hr.java.vjezbe.entiteti;


import javafx.fxml.FXML;

public abstract class ScreenControllerMethods {
    public abstract void filterValue();
    public abstract void onClickShowRow();
    public abstract void clearSelection();
    public abstract void deleteSelectedRowValue() throws Exception;
}
