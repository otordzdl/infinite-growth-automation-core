package io.github.otordzdl.infinitegrowth.core.selenium;

public enum BrowserType {
    CHROME("chrome"),
    SAFARI("safari"),
    FIREFOX("firefox"),
    OPERA("opera"),
    EDGE("edge");

    private String name;

    BrowserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}