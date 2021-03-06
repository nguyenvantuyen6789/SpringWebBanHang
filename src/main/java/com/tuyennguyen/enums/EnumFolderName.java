package com.tuyennguyen.enums;

public enum EnumFolderName {
    folder1(1, "folder1"),
    folder2(2, "folder2");

    private final int key;

    private final String value;

    EnumFolderName(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
