package core.selenium;

public enum DeviceFarmType {
    BROWSERSTACK("browserstack"),
    SAUCELABS("saucelabs"),
    LAMDATEST("lamdatest");

    private String name;

    DeviceFarmType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
