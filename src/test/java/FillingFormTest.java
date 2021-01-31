import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Arrays;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class FillingFormTest extends TestBase {
    SelenideElement firstNameField = $("#firstName");
    SelenideElement lastNameField = $("#lastName");
    SelenideElement emailField = $("#userEmail");
    SelenideElement mobileNumberField = $("#userNumber");
    SelenideElement birthDateField = $("#dateOfBirthInput");
    SelenideElement subjectField = $("#subjectsInput");
    SelenideElement uploadPictureButton = $("#uploadPicture");
    SelenideElement addressField = $("#currentAddress");
    SelenideElement stateSelector = $x("//div[@id='state']//input");
    SelenideElement citySelector = $x("//div[@id='city']//input");
    SelenideElement submitButton = $("#submit");
    SelenideElement submittedFormContainer = $(".modal-content");

    File picture = new File("./src/test/resources/poly-3295856_1920.png");

    String firstName = "Ivan";
    String lastName = "Ivanov";
    String email = "test@test.ru";
    String gender = "Male";
    String mobileNumber = "1234567891";
    String birthDate = "1 December,1980";
    String [] subjects = {"Maths", "English"};
    String [] hobbies = {"Sports", "Reading"};
    String pictureName = picture.getName();
    String address = "Some city, another street";
    String state = "Haryana";
    String city = "Panipat";

    void selectGender(String gender) {
        $x(String.format("//input[@value='%s']//following-sibling::label", gender)).click();
    }

    void selectHobbies(String [] hobbyArr) {
        for (String hobby : hobbyArr) {
            $("#hobbiesWrapper").$(byText(hobby)).click();
        }
    }

    void selectSubject(String [] subjectArr) {
        for (String subject : subjectArr) {
            subjectField.setValue(subject).pressEnter();
        }
    }

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
        selectGender(gender);
        mobileNumberField.setValue(mobileNumber);
        selectSubject(subjects);
        selectHobbies(hobbies);
        birthDateField.click();
        DatePicker picker = new DatePicker();
        picker.selectDate(birthDate);
        uploadPictureButton.uploadFile(picture);
        addressField.setValue(address);
        stateSelector.setValue(state).pressEnter();
        citySelector.setValue(city).pressEnter();
        submitButton.click();

        submittedFormContainer.shouldBe(Condition.visible);
        checkFormValue("Student Name", firstName + " " + lastName);
        checkFormValue("Student Email", email);
        checkFormValue("Gender", gender);
        checkFormValue("Mobile", mobileNumber);
        checkFormValue("Date of Birth", birthDate);
        checkFormValue("Subjects", Arrays.toString(subjects).replaceFirst("\\[", "").replaceFirst("]", ""));
        checkFormValue("Hobbies", Arrays.toString(hobbies).replaceFirst("\\[", "").replaceFirst("]", ""));
        checkFormValue("Picture", pictureName);
        checkFormValue("Address", address);
        checkFormValue("State and City", state + " " + city);
    }
}
