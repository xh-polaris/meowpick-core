package com.xhpolaris.meowpick.common.enums;

import com.xhpolaris.meowpick.common.TagData;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Component
public class EnumMetaRegister implements InitializingBean, ResourceLoaderAware, EnvironmentAware {
    private ResourceLoader resourceLoader;
    private Environment environment;
    private Map<TagData, List<TagData>> tags = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() {
        registerEnumMeta();
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment);
    }

    @SneakyThrows
    private void registerEnumMeta() {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(resourceLoader);
        scanner.addIncludeFilter(new AssignableTypeFilter(Enums.class));

        Set<BeanDefinition> candidateComponents = new HashSet<>();
        Set<String> basePackages = getBasePackages();
        for (String basePackage : basePackages) {
            candidateComponents.addAll(scanner.findCandidateComponents(basePackage));
        }

        for (BeanDefinition candidateComponent : candidateComponents) {
            AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
            AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();

            String beanClassName = candidateComponent.getBeanClassName();
            if (beanClassName == null || annotationMetadata.isInterface()) {
                continue;
            }
            Class<?> loadedClass = ClassUtils.forName(beanClassName,
                                                      this.getClass().getClassLoader());
            if (Enum.class.isAssignableFrom(loadedClass) && loadedClass.isEnum()) {
                Class<? extends Enum<?>> cls = (Class<? extends Enum<?>>) loadedClass;

                String treeName = Optional.ofNullable(AnnotatedElementUtils.getMergedAnnotation(
                                                  cls,
                                                  EnumValue.class))
                                          .map(EnumValue::value)
                                          .orElse(cls.getSimpleName());

                populateTags(treeName, cls);
            }
        }
    }

    private void populateTags(String treeName, Class<? extends Enum<?>> cls) {
        tags.put(new TagData(treeName, cls.getSimpleName()),
                 Arrays.stream(cls.getEnumConstants()).map(Enums.class::cast).map(e -> {
                     TagData tag = new TagData();

                     tag.setLabel(e.getLabel());
                     tag.setType(e.getValue());

                     return tag;
                 }).toList());
    }

    private Set<String> getBasePackages() {
        return Set.of("com.xhpolaris.meowpick");
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
