package com.github.ralfstuckert.junit.jupiter.extension.tempfolder;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TemporaryFolderExtension implements ParameterResolver, AfterTestExecutionCallback, TestInstancePostProcessor {
    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        // clean up test instance
        cleanUpTemporaryFolder(extensionContext);

        if (extensionContext.getParent().isPresent()) {
            // clean up injected member
            cleanUpTemporaryFolder(extensionContext.getParent().get());
        }
    }

    protected void cleanUpTemporaryFolder(ExtensionContext extensionContext) {
        for (TemporaryFolder temporaryFolder : getTemporaryFolders(extensionContext)) {
            temporaryFolder.after();
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return (extensionContext instanceof ExtensionContext) && (parameter.getType().isAssignableFrom(TemporaryFolder.class) ||
                                                                  (parameter.getType().isAssignableFrom(File.class) && (parameter.isAnnotationPresent(TempFolder.class)
                                                                                                                        || parameter.isAnnotationPresent(TempFile.class))));
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        ExtensionContext testExtensionContext = (ExtensionContext) extensionContext;
        try {
            TemporaryFolder temporaryFolder = createTemporaryFolder(testExtensionContext, testExtensionContext.getTestMethod().get());

            Parameter parameter = parameterContext.getParameter();
            if (parameter.getType().isAssignableFrom(TemporaryFolder.class)) {
                return temporaryFolder;
            }
            if (parameter.isAnnotationPresent(TempFolder.class)) {
                return temporaryFolder.newFolder();
            }
            if (parameter.isAnnotationPresent(TempFile.class)) {
                TempFile annotation = parameter.getAnnotation(TempFile.class);
                if (!annotation.value().isEmpty()) {
                    return temporaryFolder.newFile(annotation.value());
                }
                return temporaryFolder.newFile();
            }

            throw new ParameterResolutionException("unable to resolve parameter for " + parameterContext);
        } catch (IOException e) {
            throw new ParameterResolutionException("failed to create temp file or folder", e);
        }
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        for (Field field : testInstance.getClass().getDeclaredFields()) {
            if (field.getType().isAssignableFrom(TemporaryFolder.class)) {
                TemporaryFolder temporaryFolder = createTemporaryFolder(context, field);
                field.setAccessible(true);
                field.set(testInstance, temporaryFolder);
            }
        }
    }

    protected Iterable<TemporaryFolder> getTemporaryFolders(ExtensionContext extensionContext) {
        Map<Object, TemporaryFolder> map = getStore(extensionContext).get(extensionContext.getTestClass().get(), Map.class);
        if (map == null) {
            return Collections.emptySet();
        }
        return map.values();
    }

    protected TemporaryFolder createTemporaryFolder(ExtensionContext extensionContext, Member key) {
        Map<Member, TemporaryFolder> map =
                getStore(extensionContext).getOrComputeIfAbsent(extensionContext.getTestClass().get(),
                                                                (c) -> new ConcurrentHashMap<>(), Map.class);
        return map.computeIfAbsent(key, (k) -> new TemporaryFolder());
    }

    protected ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context));
    }
}