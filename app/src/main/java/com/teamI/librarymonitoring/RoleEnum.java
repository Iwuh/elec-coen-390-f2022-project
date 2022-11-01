package com.teamI.librarymonitoring;

public enum RoleEnum {
    Unknown,
    Student,
    Librarian;

    public static RoleEnum toRoleEnum(String string) {
        try{
            return valueOf(string);
        }catch(Exception ex){
            return Unknown;
        }
    }
}
