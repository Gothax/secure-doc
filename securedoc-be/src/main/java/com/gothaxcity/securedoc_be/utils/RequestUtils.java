package com.gothaxcity.securedoc_be.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gothaxcity.securedoc_be.domain.Response;
import com.gothaxcity.securedoc_be.exception.ApiException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static java.time.LocalTime.now;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class RequestUtils {

    private static final BiConsumer<HttpServletResponse, Response> writeResponse = (httpServletResponse, response) -> {
        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            new ObjectMapper().writeValue(outputStream, response);
            outputStream.flush();
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    };

    private static final BiFunction<Exception, HttpStatus, String> errorReason = (exception, httpStatus) -> {
        if (httpStatus.isSameCodeAs(FORBIDDEN)) {
            return "You do not have enough permissions to perform this action";
        }
        if (httpStatus.isSameCodeAs(UNAUTHORIZED)) {
            return "You are not logged in";
        }
        if (exception instanceof DisabledException || exception instanceof LockedException || exception instanceof BadCredentialsException || exception instanceof CredentialsExpiredException || exception instanceof ApiException) {
            return exception.getMessage();
        }
        if (httpStatus.is5xxServerError()) {
            return "Internal Server Error occurred";
        } else {
            return "An error occurred. Please try again later.";
        }
    };

    public static Response getResponse(HttpServletRequest request, Map<?, ?> data, String message, HttpStatus status) {
        return new Response(now().toString(), status.value(), request.getRequestURI(), HttpStatus.valueOf(status.value()), message, EMPTY, data);
    }

    public static void handleErrorResponse(HttpServletRequest request, HttpServletResponse response, Exception e) {
        if (e instanceof AccessDeniedException) {
            Response apiResponse = getErrorResponse(request, response, e, FORBIDDEN);
            writeResponse.accept(response, apiResponse);
        }
    }

    private static Response getErrorResponse(HttpServletRequest request, HttpServletResponse response, Exception e, HttpStatus httpStatus) {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
        return new Response(now().toString(), httpStatus.value(), request.getRequestURI(), HttpStatus.valueOf(httpStatus.value()), errorReason.apply(e, httpStatus), getRootCauseMessage(e), emptyMap());
    }
}
