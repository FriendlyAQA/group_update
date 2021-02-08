package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.IBottomButtons;
import com.friendly.aqa.entities.ILeft;
import com.friendly.aqa.entities.Table;
import com.friendly.aqa.entities.TopMenu;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.friendly.aqa.entities.BottomButtons.*;

public class FileManagementPage extends BasePage {
    String fileName;
    int tableSize;

    @FindBy(id = "fuFWF")
    private WebElement fileInput;

    @FindBy(id = "txtVersion")
    private WebElement versionInput;

    @FindBy(id = "cbNewest")
    private WebElement newest;

    public FileManagementPage assertMainPageIsDisplayed() {
        assertElementsAreEnabled(manufacturerComboBox, modelComboBox, selectUploadFileTypeComboBox);
        return this;
    }

    public FileManagementPage selectManufacturer() {
        return (FileManagementPage) super.selectManufacturer(getDevice("tr181")[0]);
    }

    public FileManagementPage selectManufacturer(String protocol) {
        return (FileManagementPage) super.selectManufacturer(getDevice(protocol)[0]);
    }

    public FileManagementPage selectModel() {
        return (FileManagementPage) super.selectModel(getDevice("tr181")[1]);
    }

    public FileManagementPage selectModel(String protocol) {
        return (FileManagementPage) super.selectModel(getDevice(protocol)[1]);
    }

    public FileManagementPage assertNewestCbIsDisabled() {
        if (newest.isEnabled()) {
            throw new AssertionError("Unexpected 'Newest' checkbox state!\nExpected: disabled; actual: enabled");
        }
        return this;
    }

    public FileManagementPage topMenu(TopMenu value) {
        return (FileManagementPage) super.topMenu(value);
    }

    public FileManagementPage selectFileType(String type) {
        selectComboBox(selectUploadFileTypeComboBox, type);
        return this;
    }

    public FileManagementPage newest() {
        newest.click();
        return this;
    }

    public FileManagementPage deleteIfExists(String fileName) {
        this.fileName = fileName;
        if (elementIsPresent("tblParameters")) {
            Table table = getMainTable();
            if (table.contains(fileName)) {
                delete(table);
            }
        }
        return this;
    }

    public void deleteAndValidateAbsence() {
        Table table = getMainTable();
        fileName = table.getCellText(1, "Name");
        delete(table);
        table = getMainTable();
        if (table.contains(fileName)) {
            throw new AssertionError("Deleted file is still present in table!");
        }
    }

    private void delete(Table table) {
        selectItem(table, fileName, 1);
        bottomMenu(DELETE);
        okButtonPopUp();
        waitForUpdate();
    }

    public void assertBackupsAreDeleted() {
        Table table = getMainTable();
        List<Integer> list = table.getRowsWithText("LWM2M PSK Credentials");
        list.addAll(table.getRowsWithText("LWM2M Resource Definition"));
        for (int i: list){
            if (table.getCellText(i, "State").equals("Exists")){
                throw new AssertionError("Backup file '" + table.getCellText(i, "Name") +
                        "' has 'EXIST' state after deleting!");
            }
        }
    }

    public FileManagementPage selectFile() {
        String inputText = new File("import/" + fileName).getAbsolutePath();
        fileInput.sendKeys(inputText);
        executeScript("ValidateFile();");
        return this;
    }

    public FileManagementPage inputVersion() {
        int version = (int) (Math.random() * 10);
        inputText(versionInput, String.valueOf(version));
        return this;
    }

    public void validateFilePresence() {
        if (!getTable("tblParameters").contains(fileName)) {
            throw new AssertionError("Target file name not found on paage!");
        }
    }

    public void validateFiltering(String column) {
        WebElement comboBox = column.startsWith("Manuf") ? manufacturerComboBox : column.startsWith("Model") ? modelComboBox : selectUploadFileTypeComboBox;
        validateFiltering(comboBox, column);
    }

    public void validateFiltering(WebElement comboBox, String columnName) {
        List<String> optionList = getOptionList(comboBox);
        optionList.remove("All");
        for (String option : optionList) {
            selectComboBox(comboBox, option);
            waitForUpdate();
            if (elementIsAbsent("tblParameters")) {
                continue;
            }
            Set<String> itemSet = new HashSet<>(Arrays.asList(getMainTable().getColumn(columnName)));
            if (itemSet.size() > 1 || (itemSet.size() == 1 && !itemSet.iterator().next().equals(option))) {
                throw new AssertionError("Column '" + columnName + "' has unexpected content!\n"
                        + "Expected: " + option + ", but found: " + itemSet);
            }
        }
    }

    public FileManagementPage saveTableSize() {
        tableSize = elementIsPresent("tblParameters") ? getMainTable().getTableSize()[0] : 0;
        return this;
    }

    public void assertViewIsReset() {
        int currentTableSize = elementIsPresent("tblParameters") ? getMainTable().getTableSize()[0] : 0;
        WebElement[] cbs = {manufacturerComboBox, modelComboBox, selectUploadFileTypeComboBox};
        boolean _default = true;
        for (WebElement cb : cbs) {
            if (!getSelectedOption(cb).equals("All")) {
                _default = false;
                break;
            }
        }
        if (!_default || currentTableSize == tableSize) {
            throw new AssertionError("It seems like view has not been reset!");
        }
    }

    @Override
    public FileManagementPage resetView() {
        return (FileManagementPage) super.resetView();
    }

    @Override
    public String getMainTableId() {
        return "tblParameters";
    }

    @Override
    public FileManagementPage leftMenu(ILeft item) {
        return (FileManagementPage) super.leftMenu(item);
    }

    @Override
    public FileManagementPage bottomMenu(IBottomButtons button) {
        clickBottomButton(button);
        return this;
    }

    @Override
    public FileManagementPage okButtonPopUp() {
        return (FileManagementPage) super.okButtonPopUp();
    }

    public enum Left implements ILeft {
        FILE_MANAGEMENT("File Management"), ADD("Add");
        private final String value;

        Left(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
