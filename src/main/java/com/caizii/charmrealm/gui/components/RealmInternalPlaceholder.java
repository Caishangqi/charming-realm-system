package com.caizii.charmrealm.gui.components;

public interface RealmInternalPlaceholder {
    /**
     * paras specific placeholder from the string in specify Context
     * @param Context   The Context Object that possible needed by placeholder
     * @param placeholder   Placeholder String
     * @return  The parsed String
     */
    public String parasInternalPlaceholder(Object Context, String placeholder, String processString);
}
