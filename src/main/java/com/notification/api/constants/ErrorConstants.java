package com.notification.api.constants;

public interface ErrorConstants {

    String TEMPLATE_ALREADY_EXISTS_ERROR = "Template already exists with given name";

    String TEMPLATE_NOT_EXISTS_WITH_ID_ERROR = "Template doesn't exists with given id";

    String TEMPLATE_ID_IS_REQUIRED = "Template Id Is Required";
    String PUT_CACHING_ERROR = "Error while Caching Data";
    String CACHE_PARSING_ERROR = "Error while Parsing Cache Data";
}
