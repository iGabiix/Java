// File name: midterms_practicalexam_Gamboa.java

public class midterms_practicalexam_Gamboa {

    // These variables hold the current time values.
    private int hour = 0;
    private int minute = 0;
    private int second = 0;

    // Constructor – runs when we make a new object of this class.
    // It calls setTime() to set the starting hour, minute, and second.
    public midterms_practicalexam_Gamboa(int h, int m, int s) {
        this.setTime(h, m, s);
    }

    // Getter for hour – used to access the value of hour.
    public int getHour() {
        return this.hour;
    }

    // Getter for minute – returns the current minute value.
    public int getMinute() {
        return this.minute;
    }

    // Getter for second – returns the current second value.
    public int getSecond() {
        return this.second;
    }

    // Setter for hour – makes sure the hour is between 0 and 23.
    // If not, it will reset the hour to 0.
    public void setHour(int h) {
        this.hour = (h >= 0 && h < 24) ? h : 0;
    }

    // Setter for minute – makes sure the minute is between 0 and 59.
    // If not, it will reset the minute to 0.
    public void setMinute(int m) {
        this.minute = (m >= 0 && m < 60) ? m : 0;
    }

    // Setter for second – makes sure the second is between 0 and 59.
    // If not, it will reset the second to 0.
    public void setSecond(int s) {
        this.second = (s >= 0 && s < 60) ? s : 0;
    }

    // Method to set all time values (hour, minute, second) at once.
    // It uses the other setter methods so the inputs are always valid.
    public void setTime(int h, int m, int s) {
        this.setHour(h);
        this.setMinute(m);
        this.setSecond(s);
    }

    // Displays the time in hh:mm:ss format.
    // The %02d makes sure that single numbers like 1 become 01.
    public void print() {
        System.out.printf("%02d:%02d:%02d\n", this.hour, this.minute, this.second);
    }

    // Adds one second to the time.
    // If seconds reach 60, it resets to 0 and adds 1 to minutes.
    // If minutes reach 60, it resets to 0 and adds 1 to hours.
    // If hours reach 24, it resets to 0.
    public void nextSecond() {
        this.second++;
        if (this.second >= 60) {
            this.second = 0;
            this.minute++;
            if (this.minute >= 60) {
                this.minute = 0;
                this.hour++;
                if (this.hour >= 24) {
                    this.hour = 0;
                }
            }
        }
    }

    // Main method – the starting point of the program.
    // It creates a Time object, shows the time, adds one second, and shows the new
    // time.
    public static void main(String[] args) {
        midterms_practicalexam_Gamboa t = new midterms_practicalexam_Gamboa(0, 1, 59);
        t.print(); // Shows 00:01:59
        t.nextSecond(); // Adds one second
        t.print(); // Shows 00:02:00
    }
}
