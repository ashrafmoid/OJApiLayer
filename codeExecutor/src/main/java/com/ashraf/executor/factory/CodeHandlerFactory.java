package com.ashraf.executor.factory;

import com.ashraf.executor.handler.CodeHandler;

public interface CodeHandlerFactory {
    CodeHandler getCodeHandler(String language);

}
