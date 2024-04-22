package com.lith.minecord;

public class Static {
    final public static class ConfigKeys {
        public static final String TOKEN = ("token");
        public static final String SERVER_ID = ("server_id");
        public static final String INVITE_LINK = ("invite_link");
    }

    final public static class Command {
        final public static class Names {
            public static final String RELOAD = "dcReload";
        }

        final public static class PermissionKeys {
            public static final String PREFIX = "minecord";
            public static final String RELOAD = PermissionKeys.PREFIX + "reload";
        }
    }
}
