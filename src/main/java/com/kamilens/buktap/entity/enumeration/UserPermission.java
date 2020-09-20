package com.kamilens.buktap.entity.enumeration;

public enum UserPermission {

    BOOK_READ(Names.BOOK_READ),
    BOOK_WRITE(Names.BOOK_WRITE);

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public static class Names {
        private Names() {}
        public static final String BOOK_READ = "book:read";
        public static final String BOOK_WRITE = "book:write";
    }

}
