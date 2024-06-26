package org.icet.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.icet.pos.bo.custom.impl.AdminDashBoardBoImpl;
import org.icet.pos.bo.factory.BoFactory;
import org.icet.pos.bo.factory.BoType;
import org.icet.pos.model.EmployeeModel;
import org.icet.pos.bo.custom.impl.EmailSender;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.animation.PauseTransition;
import org.icet.pos.util.FormUtil;
import org.icet.pos.util.HibernateUtil;

@Slf4j
public class AdminDashBoardFormController implements Initializable {
    public Label lblTotalEmployees;
    public TextField txtId;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtPassword;
    public TextField txtSalary;
    public TextField txtAddress;
    public TextField txtContact;
    public TableView<EmployeeModel> tblEmployee;
    public TableColumn<EmployeeModel, String> colId;
    public TableColumn<EmployeeModel, String> colName;
    public TableColumn<EmployeeModel, String> colEmail;
    public TableColumn<EmployeeModel, String> colPasssword;
    public TableColumn<EmployeeModel, Double> colSalary;
    public TableColumn<EmployeeModel, String> colAddress;
    public TableColumn<EmployeeModel, String> colRole;
    public TableColumn<EmployeeModel, String> colContact;
    public JFXButton btnAddEmployee;
    public JFXButton btnUpdate;
    public JFXButton btnRemove;
    public Label lblNewEmpId;
    public JFXButton btnAddNewId;
    public Label lblNotify;

    private static final String EMAIL_PATTERN =
            "^[\\w+&*-]+(?:\\.[\\w+&*-]+)*@(?:[\\w-]+\\.)+[A-Za-z]{2,7}$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private static final String ID_PATTERN = "E\\d{4}";
    private static final Pattern idPattern = Pattern.compile(ID_PATTERN);
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    private static final String SALARY_PATTERN = "\\d+";
    private static final Pattern salaryPattern = Pattern.compile(SALARY_PATTERN);
    private static final String PHONE_PATTERN = "^0\\d{9}$";
    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);

    private final AdminDashBoardBoImpl controller = BoFactory.getInstance().getBo(BoType.ADMIN_DASHBOARD);
    public JFXButton btnReport;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblNewEmpId.setText(controller.getNewEmployeeId());
        txtId.setText(lblNewEmpId.getText());
        lblTotalEmployees.setText(String.format("%03d", controller.getTotalEmployees() - 1));
        btnAddEmployee.setDisable(true);
        btnUpdate.setDisable(true);

        txtId.setOnKeyReleased(this::empIdKeyReleasedOnAction);
        txtName.setOnKeyReleased(this::txtNameKeyReleasedOnAction);
        txtEmail.setOnKeyReleased(this::txtEmailKeyReleasedOnAction);
        txtPassword.setOnKeyReleased(this::txtPasswordKeyReleasedOnAction);
        txtSalary.setOnKeyReleased(this::txtSalaryKeyReleasedOnAction);
        txtAddress.setOnKeyReleased(this::txtAddressKeyReleasedOnAction);
        txtContact.setOnKeyReleased(this::txtContactKeyReleasedOnAction);

        // Table properties
        try {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colPasssword.setCellValueFactory(new PropertyValueFactory<>("password"));
            colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

            loadEmployeeTable();
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }

    private void loadEmployeeTable() {
        ObservableList<EmployeeModel> list;
        list = controller.getAllEmployees();
        tblEmployee.setItems(list);
    }

    public void empIdKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validateId()) {
            showNotification("Invalid ID: ID must follow the format E0000");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    public void txtNameKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validateName()) {
            showNotification("Invalid Name: Name cannot be empty");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    public void txtEmailKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validateEmail()) {
            showNotification("Invalid Email: Email format is incorrect");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    public void txtPasswordKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validatePassword()) {
            showNotification("Invalid Password: Password must be at least 8 characters long and include numbers, letters, and symbols");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    public void txtSalaryKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validateSalary()) {
            showNotification("Invalid Salary: Salary must contain only numbers");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    public void txtAddressKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validateAddress()) {
            showNotification("Invalid Address: Address cannot be empty");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    public void txtContactKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validatePhone()) {
            showNotification("Invalid Phone: Phone number must be 10 digits and start with 0");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    private void checkAllFieldsValid() {
        boolean isIdValid = validateId();
        boolean isNameValid = validateName();
        boolean isEmailValid = validateEmail();
        boolean isPasswordValid = validatePassword();
        boolean isSalaryValid = validateSalary();
        boolean isAddressValid = validateAddress();
        boolean isPhoneValid = validatePhone();

        boolean allFieldsValid = isIdValid && isNameValid && isEmailValid &&
                isPasswordValid && isSalaryValid && isAddressValid && isPhoneValid;

        btnAddEmployee.setDisable(!allFieldsValid);
        btnUpdate.setDisable(!allFieldsValid);

        if (allFieldsValid) {
            lblNotify.setText("");
        }
    }

    private boolean validateId() {
        String id = txtId.getText();
        return id != null && idPattern.matcher(id).matches();
    }

    private boolean validateName() {
        String name = txtName.getText().trim();
        return !name.isEmpty();
    }

    private boolean validateEmail() {
        String email = txtEmail.getText();
        return email != null && emailPattern.matcher(email).matches();
    }

    private boolean validatePassword() {
        String password = txtPassword.getText();
        return password != null && passwordPattern.matcher(password).matches();
    }

    private boolean validateSalary() {
        String salary = txtSalary.getText();
        return salary != null && salaryPattern.matcher(salary).matches();
    }

    private boolean validateAddress() {
        String address = txtAddress.getText().trim();
        return !address.isEmpty();
    }

    private boolean validatePhone() {
        String phone = txtContact.getText();
        return phone != null && phonePattern.matcher(phone).matches();
    }

    private void showNotification(String message) {
        lblNotify.setText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> lblNotify.setText(""));
        delay.play();
    }

    private void clearNotification() {
        lblNotify.setText("");
    }

    public void btnAddNewIdOnAction(ActionEvent actionEvent) {
        txtId.setText(lblNewEmpId.getText());
    }

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) {
        Double salary = Double.parseDouble(txtSalary.getText());
        EmployeeModel newEmployee =
                new EmployeeModel(txtId.getText(), txtName.getText(), txtEmail.getText(), txtPassword.getText(),
                        "Employee", salary, txtAddress.getText(), txtContact.getText());
        controller.persist(newEmployee);

        // Email content
        String subject = "Welcome to the Company!";
        String message = String.format(
                "Dear %s,\n\nWelcome to the company! Here are your details:\n\n" +
                        "Email: %s\nPassword: %s\nSalary: %s\n\nBest regards,\nCompany Owner",
                txtName.getText(), txtEmail.getText(), txtPassword.getText(), txtSalary.getText());

        // Send email
        EmailSender.sendEmail(txtEmail.getText(), message, subject);

        // Update UI
        lblNewEmpId.setText(controller.getNewEmployeeId());
        txtId.setText(lblNewEmpId.getText());
        lblTotalEmployees.setText(String.format("%03d", controller.getTotalEmployees() - 1));
        clearFields();
        btnAddEmployee.setDisable(true);
        loadEmployeeTable();
    }

    private void clearFields() {
        txtName.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtSalary.clear();
        txtAddress.clear();
        txtContact.clear();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtEmail.getText().isEmpty() ||
                txtPassword.getText().isEmpty() || txtSalary.getText().isEmpty() ||
                txtAddress.getText().isEmpty() || txtContact.getText().isEmpty()) {
            btnUpdate.setDisable(true);
            showNotification("All fields must be filled");
            return;
        }

        Double salary = Double.parseDouble(txtSalary.getText());
        EmployeeModel updatedEmployee = new EmployeeModel(txtId.getText(), txtName.getText(), txtEmail.getText(),
                txtPassword.getText(), "Employee", salary, txtAddress.getText(), txtContact.getText());

        controller.updateEmployee(updatedEmployee);
        loadEmployeeTable();
        clearFields();
        showNotification("Employee updated successfully");
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        if (id == null || id.isEmpty()) {
            showNotification("ID cannot be empty");
            return;
        }

        controller.removeEmployeeById(id);
        loadEmployeeTable();
        clearFields();
        lblNewEmpId.setText(controller.getNewEmployeeId());
        lblTotalEmployees.setText(String.format("%03d", controller.getTotalEmployees() - 1));
        showNotification("Employee removed successfully");
    }

    public void btnLogOurOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        FormUtil.switchScene(stage, "loginForm.fxml");
    }

    public void btnSearchEmployeeOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        EmployeeModel employee = controller.findEmployeeById(id);
        if (employee != null) {
            txtName.setText(employee.getName());
            txtEmail.setText(employee.getEmail());
            txtPassword.setText(employee.getPassword());
            txtSalary.setText(employee.getSalary().toString());
            txtAddress.setText(employee.getAddress());
            txtContact.setText(employee.getPhoneNumber());
            showNotification("Employee found and data loaded");
        } else {
            showNotification("Employee not found");
            clearFields(); // Clear fields if employee not found
        }
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
//        try {
//            JasperDesign design = JRXmlLoader.load("src/main/resources/reports/employee report.jrxml");
//            JasperReport jasperReport = JasperCompileManager.compileReport(design);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, HibernateUtil.getSession());
//            JasperViewer.viewReport(jasperPrint,false);
//        } catch (JRException e) {
//            throw new RuntimeException(e);
//        }
    }
}
