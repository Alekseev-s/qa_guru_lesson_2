import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DatePicker {
    SelenideElement pickerContainer = $(".react-datepicker");
    SelenideElement monthSelector = $x("//select[@class='react-datepicker__month-select']");
    SelenideElement yearSelector = $x("//select[@class='react-datepicker__year-select']");
    SelenideElement monthCalendar = $x("//div[@class='react-datepicker__month']");

    public void selectDate(String date) {
        pickerContainer.shouldBe(Condition.visible);
        LocalDate birthDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d MMMM,yyyy", Locale.ENGLISH));
        String day = Integer.toString(birthDate.getDayOfMonth());
        String month = birthDate.getMonth().toString();
        String year = Integer.toString(birthDate.getYear());
        monthSelector.selectOption(month.substring(0, 1) + month.substring(1).toLowerCase());
        yearSelector.selectOption(year);
        monthCalendar.$x(String.format(".//div[not(contains(@class, 'react-datepicker__day--outside-month')) and text()='%s']", day)).click();
    }
}
