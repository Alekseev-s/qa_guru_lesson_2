import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class FillingFormTest extends TestBase {
    SelenideElement firstNameField = $("#firstName");
    SelenideElement lastNameField = $("#lastName");
    SelenideElement emailField = $("#userEmail");
    SelenideElement genderOptions = $("#genterWrapper");
    SelenideElement mobileNumberField = $("#userNumber");
    SelenideElement birthDateField = $("#dateOfBirthInput");
    SelenideElement calendar = $(".react-datepicker");
    SelenideElement subjectField = $("#subjectsInput");
    SelenideElement subjectMenu = $(".subjects-auto-complete__menu");
    SelenideElement hobbyOptions = $("#hobbiesWrapper");
    SelenideElement uploadPictureButton = $("#uploadPicture");
    SelenideElement addressField = $("#currentAddress");
    SelenideElement stateSelector = $("#state");
    SelenideElement citySelector = $("#city");
    SelenideElement submitButton = $("#submit");
    SelenideElement submittedFormContainer = $(".modal-content");

    File picture = new File("./src/test/resources/poly-3295856_1920.png");

    String firstName = "Ivan";
    String lastName = "Ivanov";
    String email = "test@test.ru";
    String gender = "Male";
    String mobileNumber = "1234567891";
    String yearOfBirth = "1980";
    String monthOfBirth = "May";
    String dayOfBirth = "28";
    String fullBirthDate = dayOfBirth + " " + monthOfBirth + "," + yearOfBirth;
    ArrayList<String > subjects = new ArrayList<>(Arrays.asList("Maths", "English"));
    ArrayList<String> hobbies = new ArrayList<>(Arrays.asList("Sports", "Reading"));
    String pictureName = picture.getName();
    String address = "Some city, another street";
    String state = "Haryana";
    String city = "Panipat";

    void checkFormValue(String label, String value) {
        submittedFormContainer
                .$x(String.format(".//td[text()='%s']//following-sibling::td", label))
                .shouldHave(Condition.text(value));
    }

    @Test
    public void fillForm() {
        open("https://demoqa.com/automation-practice-form");
        firstNameField.setValue(firstName);
        lastNameField.setValue(lastName);
        emailField.setValue(email);
        genderOptions.$(byText(gender)).click();
        mobileNumberField.setValue(mobileNumber);
        subjectField.setValue(subjects.get(0));
        subjectMenu.$(byText(subjects.get(0))).click();
        subjectField.setValue(subjects.get(1));
        subjectMenu.$(byText(subjects.get(1))).click();
        hobbyOptions.$(byText(hobbies.get(0))).click();
        hobbyOptions.$(byText(hobbies.get(1))).click();
        birthDateField.click();
        calendar.$(".react-datepicker__year-select").selectOption(yearOfBirth);
        calendar.$(".react-datepicker__month-select").selectOption(monthOfBirth);
        calendar.$(":not(.react-datepicker__day--outside-month).react-datepicker__day--0" + dayOfBirth).click();
        uploadPictureButton.uploadFile(picture);
        addressField.setValue(address);
        stateSelector.scrollIntoView(true).click();
        stateSelector.$(byText(state)).click();
        citySelector.click();
        citySelector.$(byText(city)).click();
        submitButton.click();

        submittedFormContainer.shouldBe(Condition.visible);
        checkFormValue("Student Name", firstName + " " + lastName);
        checkFormValue("Student Email", email);
        checkFormValue("Gender", gender);
        checkFormValue("Mobile", mobileNumber);
        checkFormValue("Date of Birth", fullBirthDate);
        checkFormValue("Subjects", subjects.get(0) + ", " + subjects.get(1));
        checkFormValue("Hobbies", hobbies.get(0) + ", " + hobbies.get(1));
        checkFormValue("Picture", pictureName);
        checkFormValue("Address", address);
        checkFormValue("State and City", state + " " + city);
    }
}
