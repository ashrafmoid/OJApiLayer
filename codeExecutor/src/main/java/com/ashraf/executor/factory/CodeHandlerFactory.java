package com.ashraf.executor.factory;

import com.ashraf.executor.enums.ProgrammingLanguage;
import com.ashraf.executor.handler.CodeHandler;
import com.ashraf.executor.handler.impl.CodeHandlerForC;

import java.util.HashMap;
import java.util.Map;

public class CodeHandlerFactory {
    private static final Map<String, CodeHandler> LANGUAGE_TO_CODE_HANDLER_MAP = new HashMap<>();

    public static CodeHandler getCodeHandler(String language) {
        return LANGUAGE_TO_CODE_HANDLER_MAP.computeIfAbsent(language, CodeHandlerFactory::getCodeHandlerForLanguage);
    }

    private static CodeHandler getCodeHandlerForLanguage(String language) {
        if (ProgrammingLanguage.C.getValue().equals(language)) {
            return new CodeHandlerForC();
        }
        return null;
    }
}
