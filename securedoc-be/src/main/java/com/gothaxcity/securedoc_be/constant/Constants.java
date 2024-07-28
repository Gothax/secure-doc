package com.gothaxcity.securedoc_be.constant;

public class Constants {
    public static final String GET_ARRAYS_LLC = "GET_ARRAYS_LLC";
    public static final String EMPTY_VALUE = "empty";
    public static final String AUTHORITIES = "authorities";
    public static final String ROLE = "role";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTHORITY_DELIMITER = ",";
    public static final String USER_AUTHORITIES = "document:create,document:read,document:update,document:delete";
    public static final String ADMIN_AUTHORITIES = "document:create,document:read,document:update,document:delete,user:create,user:read,user:update,user:delete";
    public static final String SUPER_ADMIN_AUTHORITIES = "document:create,document:read,document:update,document:delete,user:create,user:read,user:update,user:delete";
    public static final String MANAGER_AUTHORITIES = "document:create,document:read,document:update,document:delete";
}
