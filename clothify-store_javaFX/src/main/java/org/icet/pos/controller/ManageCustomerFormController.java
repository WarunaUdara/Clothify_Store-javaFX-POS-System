package org.icet.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.icet.pos.bo.custom.impl.EmailSender;
import org.icet.pos.bo.custom.impl.ManageCustomerBoImpl;
import org.icet.pos.bo.factory.BoFactory;
import org.icet.pos.bo.factory.BoType;
import org.icet.pos.model.CustomerModel;
import org.icet.pos.model.EmployeeModel;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Slf4j
public class ManageCustomerFormController implements Initializable {

    public TableView tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colAddress;
    public TableColumn colContact;
    public Label lblTotalCustomerss;
    public JFXButton btnReport;
    public JFXButton btnManageProducts;
    public JFXButton btnManageSuppliers;
    public JFXButton btnManageCustomers;
    public JFXButton btnManageOrders;
    public JFXButton btnPlaceOrder;
    public Label lblEmpName;
    public TextField txtId;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtAddress;
    public Label lblNotify;
    public TextField txtContact;
    public JFXButton btnUpdate;
    public JFXButton btnRemove;
    public Label lblNewCustId;
    public JFXButton btnAddNewId;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtPostalCode;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public JFXButton btnAddCustomer;

    private static final String ID_PATTERN = "C\\d{4}";
    private static final Pattern idPattern = Pattern.compile(ID_PATTERN);
    private static final String EMAIL_PATTERN =
            "^[\\w+&*-]+(?:\\.[\\w+&*-]+)*@(?:[\\w-]+\\.)+[A-Za-z]{2,7}$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private static final String PHONE_PATTERN = "^0\\d{9}$";
    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);

    private ManageCustomerBoImpl bo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblNewCustId.setText(bo.getNewCustomerId());
        txtId.setText(lblNewCustId.getText());
        lblTotalCustomerss.setText(String.format("%06d", bo.getTotalCustomers()));
        btnAddCustomer.setDisable(true);
        btnUpdate.setDisable(true);

        txtId.setOnKeyReleased(this::empIdKeyReleasedOnAction);
        txtName.setOnKeyReleased(this::txtNameKeyReleasedOnAction);
        txtEmail.setOnKeyReleased(this::txtEmailKeyReleasedOnAction);
        txtCity.setOnKeyReleased(this::txtCityKeyReleasedOnAction);
        txtProvince.setOnKeyReleased(this::txtProvinceKeyReleasedOnAction);
        txtPostalCode.setOnKeyReleased(this::txtPostalCodeKeyReleasedOnAction);
        txtAddress.setOnKeyReleased(this::txtAddressKeyReleasedOnAction);
        txtContact.setOnKeyReleased(this::txtContactKeyReleasedOnAction);

        // Table properties
        try {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
            colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
            colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

            loadEmployeeTable();
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }

    private void loadEmployeeTable() {
        ObservableList<CustomerModel> list = bo.getAllCustomers();
        tblCustomer.setItems(list);
    }

    public void btnManageProductsOnAction(ActionEvent actionEvent) {
    }

    public void btnManageSuppliersOnAction(ActionEvent actionEvent) {
    }

    public void btnManageCustomersOnAction(ActionEvent actionEvent) {
    }

    public void btnManageOrdersOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
    }

    public void btnLogOurOnAction(ActionEvent actionEvent) {
    }

    public void empIdKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validateId()) {
            showNotification("Invalid ID: ID must follow the format C0000");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    private void showNotification(String message) {
        lblNotify.setText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> lblNotify.setText(""));
        delay.play();
    }

    private void clearNotification() {
        lblNotify.setText("");
    }

    private void checkAllFieldsValid() {
        boolean isIdValid = validateId();
        boolean isNameValid = validateName();
        boolean isEmailValid = validateEmail();
        boolean isCityValid = validateCity();
        boolean isProvinceValid = validateProvince();
        boolean isPostalCodeValid = validatePostalCode();
        boolean isAddressValid = validateAddress();
        boolean isPhoneValid = validatePhone();

        boolean allFieldsValid = isIdValid && isNameValid && isEmailValid &&
                isPostalCodeValid && isCityValid && isProvinceValid && isAddressValid && isPhoneValid;

        btnAddCustomer.setDisable(!allFieldsValid);
        btnUpdate.setDisable(!allFieldsValid);

        if (allFieldsValid) {
            lblNotify.setText("");
        }
    }

    private boolean validateName() {
        String name = txtName.getText().trim();
        return !name.isEmpty();
    }

    private boolean validateEmail() {
        String email = txtEmail.getText();
        return email != null && emailPattern.matcher(email).matches();
    }

    private boolean validatePhone() {
        String phone = txtContact.getText();
        return phone != null && phonePattern.matcher(phone).matches();
    }

    private boolean validateAddress() {
        String address = txtAddress.getText().trim();
        return !address.isEmpty();
    }

    private boolean validateCity() {
        String city = txtCity.getText().trim();
        return !city.isEmpty();
    }

    private boolean validateProvince() {
        String province = txtProvince.getText().trim();
        return !province.isEmpty();
    }

    private boolean validatePostalCode() {
        String postalCode = txtPostalCode.getText().trim();
        return !postalCode.isEmpty();
    }

    private boolean validateId() {
        String id = txtId.getText();
        return id != null && idPattern.matcher(id).matches();
    }

    public void txtNameKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validateName()) {
            showNotification("Invalid Name: Name must not be empty");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    public void txtEmailKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validateEmail()) {
            showNotification("Invalid Email: Email must be valid");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    public void txtAddressKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validateAddress()) {
            showNotification("Invalid Address: Address must not be empty");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    public void txtContactKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validatePhone()) {
            showNotification("Invalid Contact: Contact must follow the format 0XXXXXXXXX");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    public void txtCityKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validateCity()) {
            showNotification("Invalid City: City must not be empty");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    public void txtProvinceKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validateProvince()) {
            showNotification("Invalid Province: Province must not be empty");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    public void txtPostalCodeKeyReleasedOnAction(KeyEvent keyEvent) {
        if (!validatePostalCode()) {
            showNotification("Invalid Postal Code: Postal Code must not be empty");
        } else {
            clearNotification();
        }
        checkAllFieldsValid();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtCity.getText().isEmpty() ||
                txtProvince.getText().isEmpty() || txtPostalCode.getText().isEmpty() || txtEmail.getText().isEmpty() || txtContact.getText().isEmpty()) {
            btnUpdate.setDisable(true);
            showNotification("All fields must be filled");
            return;
        }

        CustomerModel updatedCustomer = new CustomerModel(txtId.getText(), txtName.getText(), txtAddress.getText(), txtCity.getText(),
                txtProvince.getText(), txtPostalCode.getText(), txtEmail.getText(), txtContact.getText());

        bo.updateCustomer(updatedCustomer);
        loadEmployeeTable();
        clearFields();
        showNotification("Customer updated successfully");

        // Email content
        String subject = "Your Details Have Been Updated, " + txtName.getText() + "!";

        String message = String.format("""
            Dear %s,

            We wanted to let you know that your details have been successfully updated in our system. Here are your updated details:

            Customer ID: %s
            Name: %s
            Email: %s
            Address: %s, %s, %s, %s
            Contact Number: %s

            If you did not request this update or if any of the above information is incorrect, please contact us immediately.

            Thank you for keeping your information up to date. We look forward to continuing to serve you.

            Best regards,
            The Clothify Store Team
            """,
                txtName.getText(),
                txtId.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText(),
                txtContact.getText()
        );

        // Send email
        EmailSender.sendEmail(txtEmail.getText(), message, subject);
    }


    public void btnRemoveOnAction(ActionEvent actionEvent) {
    }

    public void btnAddNewIdOnAction(ActionEvent actionEvent) {
        txtId.setText(lblNewCustId.getText());
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) {
        CustomerModel customerModel =
                new CustomerModel(txtId.getText(), txtName.getText(), txtAddress.getText(), txtCity.getText(),
                        txtProvince.getText(), txtPostalCode.getText(), txtEmail.getText(), txtContact.getText());
        bo.persist(customerModel);

        // Email content
        String subject = "Welcome to Our Store, " + txtName.getText() + "!";

        String message = String.format("""
        Dear %s,

        Welcome to Clothify Store!

        We are thrilled to have you as a part of our community. Here are your details:

        Customer ID: %s
        Name: %s
        Email: %s
        Address: %s, %s, %s, %s
        Contact Number: %s

        Thank you for choosing us. We look forward to serving you.

        Best regards,
        The Clothify Store Team
        """,
                txtName.getText(),
                txtId.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText(),
                txtContact.getText()
        );

        // Send email
        EmailSender.sendEmail(txtEmail.getText(), message, subject);

        // Update UI
        lblNewCustId.setText(bo.getNewCustomerId());
        txtId.setText(lblNewCustId.getText());
        lblTotalCustomerss.setText(String.format("%06d", bo.getTotalCustomers()));
        clearFields();
        btnAddCustomer.setDisable(true);
        loadEmployeeTable();
    }

    public void btnSearchCustomerOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        CustomerModel customer = bo.findCustomerById(id);
        if (customer != null) {
            txtName.setText(customer.getName());
            txtEmail.setText(customer.getEmail());
            txtCity.setText(customer.getCity());
            txtProvince.setText(customer.getProvince());
            txtPostalCode.setText(customer.getPostalCode());
            txtAddress.setText(customer.getAddress());
            txtContact.setText(customer.getContact());
            showNotification("Customer found and data loaded");
        } else {
            showNotification("Customer not found");
            clearFields(); // Clear fields if customer not found
        }
    }
    private void clearFields() {
        txtName.clear();
        txtEmail.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        txtAddress.clear();
        txtContact.clear();
    }
}
