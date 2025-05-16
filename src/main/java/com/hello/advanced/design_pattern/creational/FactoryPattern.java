package com.hello.advanced.design_pattern.creational;

public class FactoryPattern {

    public static void main(String[] args) {
        Dialog dialog = new WindowsDialog();
        Button button = dialog.createButton();
        button.click();

        System.out.println("button instanceof WindowsButton :"+(button instanceof WindowsButton));
    }

}

interface Button {
    void click();
}

class WindowsButton implements Button {
    @Override
    public void click() {
        System.out.println("windows button clicked");
    }
}

class MacButton implements Button {
    @Override
    public void click() {
        System.out.println("mac button clicked");
    }
}

interface Dialog {
    Button createButton();
}

class WindowsDialog implements Dialog {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
}

class MacDialog implements Dialog {
    @Override
    public Button createButton() {
        return new MacButton();
    }
}