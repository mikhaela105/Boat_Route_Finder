/**
 * Author: Mikhaela Tajonera
 * ID: 16927679
 */
public class Person {
    private String name, phoneNumber;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String name, String phoneNumber, int age) {
        setName(name);
        setPhoneNumber(phoneNumber);
        setAge(age);
    }

    @Override
    public int hashCode() {
        char[] chars = name.toCharArray();
        int hashCode = 0;

        for (char aChar : chars) {
            hashCode += aChar;
        }

        return hashCode;
    }

    public String toString() {
        return name;
    }
}
