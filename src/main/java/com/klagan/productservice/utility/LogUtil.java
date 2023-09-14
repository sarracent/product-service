package com.klagan.productservice.utility;


import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static com.klagan.productservice.utility.Logs.Basic.*;
import static com.klagan.productservice.utility.Logs.Pattern.*;

/**
 * The type Log utils.
 * <p>
 * The only method you should use or customize from this class is the Intermediate Operation
 * other logs are handled by aspects
 */
public class LogUtil {

    public static final String MENSAJE_FIN_OPERACION = "MENSAJE_FIN_OPERACION";
    public static final String MENSAJE_OPERACION_INTERMEDIA = "MENSAJE_OPERACION_INTERMEDIA";
    public static final String MENSAJE_ERROR = "MENSAJE_ERROR";
    private static final Set<Enum<?>> structure = new LinkedHashSet<>();

    private LogUtil() {
    }

    @SafeVarargs
    public static void initializeStructure(Collection<? extends Enum<?>>... enums) {
        if (enums != null && enums.length > 0)
            Arrays.stream(enums).forEach(structure::addAll);
    }

    private static Map<String, Object> getStructure(String operation, String code, String description, String elapsed, String request, String response) {
        Map<String, Object> structureMap = new LinkedHashMap<>();
        if (!structure.isEmpty())
            structure.forEach(x -> structureMap.put(x.name(), getValue(x.name(), operation, code, description, elapsed, request, response)));

        return structureMap;
    }

    private static Object getValue(String name, String operation, String code, String description, String elapsed, String request, String response) {
        String value = ThreadContext.get(name);
        if (OPERATION.name().equalsIgnoreCase(name))
            return Util.isNullOrEmpty(operation) ? value : operation;
        if (CODE.name().equalsIgnoreCase(name))
            return code;
        if (DESCRIPTION.name().equalsIgnoreCase(name))
            return description;
        if (ELAPSED.name().equalsIgnoreCase(name))
            return elapsed;
        if (REQUEST.name().equalsIgnoreCase(name))
            return Util.isNullOrEmpty(request) ? value : request;
        if (RESPONSE.name().equalsIgnoreCase(name))
            return Util.isNullOrEmpty(response) ? value : response;
        return value;
    }

    private static String formatGeneric(String msgOperation, Map<String, Object> structureMap) {
        String resultExtra;
        if (structureMap.size() != 0) {
            var strExtra = new StringBuilder();
            structureMap.forEach((key, value) -> strExtra.append(String.format(EXTRA_FORMAT.getDescription(), key, value == null ? "" : value,
                    SEPARATOR.getDescription())));
            resultExtra = strExtra.substring(0, strExtra.lastIndexOf(SEPARATOR.getDescription())).trim();
        } else {
            resultExtra = "";
        }
        return String.format(GENERIC_DATA.getDescription(), msgOperation, resultExtra);
    }

    public static void logCleanAll() {
        ThreadContext.clearAll();
    }

    public static void logFinishOperation(Logger logger, String code, String description, String elapsed) {
        String finishOperation = formatGeneric(MENSAJE_FIN_OPERACION, getStructure(null, code, description, elapsed, null, null));
        logger.info(finishOperation);
    }

    public static void logIntermediateOperation(Logger logger, String operation, String code, String description, String elapsed, String request, String response) {
        String intermediateOperation = formatGeneric(MENSAJE_OPERACION_INTERMEDIA, getStructure(operation, code, description, elapsed, request, response));
        if (logger.isDebugEnabled())
            logger.debug(intermediateOperation);
    }

    public static void logErrorOperation(Logger logger, String operation, String code, String description, String elapsed, String request, String response) {
        String errorOperation = formatGeneric(MENSAJE_ERROR, getStructure(operation, code, description, elapsed, request, response));
        logger.error(errorOperation);
    }

    public static Map<String, String> getThreadContext(List<String> keyList) {
        if (keyList == null || keyList.isEmpty())
            return Optional.ofNullable(ThreadContext.getContext()).orElse(new HashMap<>());
        return keyList.stream().filter(x -> ThreadContext.get(x) != null).collect(Collectors.toMap(x -> x, ThreadContext::get, (a, b) -> b));
    }

    public static void removeThreadContext(List<String> keyList) {
        if (keyList != null)
            keyList.forEach(ThreadContext::remove);
    }

    public static void cleanThreadContext() {
        ThreadContext.clearAll();
    }

    public static void putThreadContext(String key, String value) {
        ThreadContext.put(key, value);
    }

    public static void putAllThreadContext(Map<String, String> threadContextMap) {
        ThreadContext.putAll(threadContextMap);
    }

}
