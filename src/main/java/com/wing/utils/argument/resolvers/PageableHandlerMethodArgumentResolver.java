package com.wing.utils.argument.resolvers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Extracts paging information from web requests and thus allows injecting {@link Pageable} instances into controller
 * methods. Request properties to be parsed can be configured. Default configuration uses request parameters beginning
 * with {@link #DEFAULT_PAGE_PARAMETER}{@link #DEFAULT_QUALIFIER_DELIMITER}.
 * 
 * @since 1.6
 * @author Oliver Gierke
 * @author Nick Williams
 * @author Mark Paluch
 */
public class PageableHandlerMethodArgumentResolver implements PageableArgumentResolver {

	private static final SortHandlerMethodArgumentResolver DEFAULT_SORT_RESOLVER = new SortHandlerMethodArgumentResolver();
	private static final String INVALID_DEFAULT_PAGE_SIZE = "Invalid default page size configured for method %s! Must not be less than one!";

	private static final String DEFAULT_PAGE_PARAMETER = "currentPage";
	private static final String DEFAULT_SIZE_PARAMETER = "size";
	private static final String DEFAULT_PREFIX = "";
	private static final String DEFAULT_QUALIFIER_DELIMITER = "_";
	private static final int DEFAULT_MAX_PAGE_SIZE = 2000;
	static final Pageable DEFAULT_PAGE_REQUEST = new PageRequest(0, 10);

	private Pageable fallbackPageable = DEFAULT_PAGE_REQUEST;
	private SortArgumentResolver sortResolver;
	private String pageParameterName = DEFAULT_PAGE_PARAMETER;
	private String sizeParameterName = DEFAULT_SIZE_PARAMETER;
	private String prefix = DEFAULT_PREFIX;
	private String qualifierDelimiter = DEFAULT_QUALIFIER_DELIMITER;
	private int maxPageSize = DEFAULT_MAX_PAGE_SIZE;
	private boolean oneIndexedParameters = false;

	/**
	 * Constructs an instance of this resolved with a default {@link SortHandlerMethodArgumentResolver}.
	 */
	public PageableHandlerMethodArgumentResolver() {
		this((SortArgumentResolver) null);
	}

	/**
	 * Constructs an instance of this resolver with the specified {@link SortHandlerMethodArgumentResolver}.
	 * 
	 * @param sortResolver the sort resolver to use
	 */
	public PageableHandlerMethodArgumentResolver(SortHandlerMethodArgumentResolver sortResolver) {
		this((SortArgumentResolver) sortResolver);
	}

	/**
	 * Constructs an instance of this resolver with the specified {@link SortArgumentResolver}.
	 *
	 * @param sortResolver the sort resolver to use
	 * @since 1.13
	 */
	public PageableHandlerMethodArgumentResolver(SortArgumentResolver sortResolver) {
		this.sortResolver = sortResolver == null ? DEFAULT_SORT_RESOLVER : sortResolver;
	}

	/**
	 * Configures the {@link Pageable} to be used as fallback in case no {@link PageableDefault} or
	 * {@link PageableDefaults} (the latter only supported in legacy mode) can be found at the method parameter to be
	 * resolved.
	 * <p>
	 * If you set this to {@literal null}, be aware that you controller methods will get {@literal null} handed into them
	 * in case no {@link Pageable} data can be found in the request. Note, that doing so will require you supply bot the
	 * page <em>and</em> the size parameter with the requests as there will be no default for any of the parameters
	 * available.
	 * 
	 * @param fallbackPageable the {@link Pageable} to be used as general fallback.
	 */
	public void setFallbackPageable(Pageable fallbackPageable) {
		this.fallbackPageable = fallbackPageable;
	}

	/**
	 * Returns whether the given {@link Pageable} is the fallback one.
	 * 
	 * @param pageable can be {@literal null}.
	 * @since 1.9
	 * @return
	 */
	public boolean isFallbackPageable(Pageable pageable) {
		return fallbackPageable == null ? false : fallbackPageable.equals(pageable);
	}

	/**
	 * Configures the maximum page size to be accepted. This allows to put an upper boundary of the page size to prevent
	 * potential attacks trying to issue an {@link OutOfMemoryError}. Defaults to {@link #DEFAULT_MAX_PAGE_SIZE}.
	 * 
	 * @param maxPageSize the maxPageSize to set
	 */
	public void setMaxPageSize(int maxPageSize) {
		this.maxPageSize = maxPageSize;
	}

	/**
	 * Retrieves the maximum page size to be accepted. This allows to put an upper boundary of the page size to prevent
	 * potential attacks trying to issue an {@link OutOfMemoryError}. Defaults to {@link #DEFAULT_MAX_PAGE_SIZE}.
	 * 
	 * @return the maximum page size allowed.
	 */
	protected int getMaxPageSize() {
		return this.maxPageSize;
	}

	/**
	 * Configures the parameter name to be used to find the page number in the request. Defaults to {@code page}.
	 * 
	 * @param pageParameterName the parameter name to be used, must not be {@literal null} or empty.
	 */
	public void setPageParameterName(String pageParameterName) {

		Assert.hasText(pageParameterName, "Page parameter name must not be null or empty!");
		this.pageParameterName = pageParameterName;
	}

	/**
	 * Retrieves the parameter name to be used to find the page number in the request. Defaults to {@code page}.
	 * 
	 * @return the parameter name to be used, never {@literal null} or empty.
	 */
	protected String getPageParameterName() {
		return this.pageParameterName;
	}

	/**
	 * Configures the parameter name to be used to find the page size in the request. Defaults to {@code size}.
	 * 
	 * @param sizeParameterName the parameter name to be used, must not be {@literal null} or empty.
	 */
	public void setSizeParameterName(String sizeParameterName) {

		Assert.hasText(sizeParameterName, "Size parameter name must not be null or empty!");
		this.sizeParameterName = sizeParameterName;
	}

	/**
	 * Retrieves the parameter name to be used to find the page size in the request. Defaults to {@code size}.
	 * 
	 * @return the parameter name to be used, never {@literal null} or empty.
	 */
	protected String getSizeParameterName() {
		return this.sizeParameterName;
	}

	/**
	 * Configures a general prefix to be prepended to the page number and page size parameters. Useful to namespace the
	 * property names used in case they are clashing with ones used by your application. By default, no prefix is used.
	 * 
	 * @param prefix the prefix to be used or {@literal null} to reset to the default.
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix == null ? DEFAULT_PREFIX : prefix;
	}

	/**
	 * The delimiter to be used between the qualifier and the actual page number and size properties. Defaults to
	 * {@code _}. So a qualifier of {@code foo} will result in a page number parameter of {@code foo_page}.
	 * 
	 * @param qualifierDelimiter the delimter to be used or {@literal null} to reset to the default.
	 */
	public void setQualifierDelimiter(String qualifierDelimiter) {
		this.qualifierDelimiter = qualifierDelimiter == null ? DEFAULT_QUALIFIER_DELIMITER : qualifierDelimiter;
	}

	/**
	 * Configures whether to expose and assume 1-based page number indexes in the request parameters. Defaults to
	 * {@literal false}, meaning a page number of 0 in the request equals the first page. If this is set to
	 * {@literal true}, a page number of 1 in the request will be considered the first page.
	 * 
	 * @param oneIndexedParameters the oneIndexedParameters to set
	 */
	public void setOneIndexedParameters(boolean oneIndexedParameters) {
		this.oneIndexedParameters = oneIndexedParameters;
	}

	/**
	 * Indicates whether to expose and assume 1-based page number indexes in the request parameters. Defaults to
	 * {@literal false}, meaning a page number of 0 in the request equals the first page. If this is set to
	 * {@literal true}, a page number of 1 in the request will be considered the first page.
	 * 
	 * @return whether to assume 1-based page number indexes in the request parameters.
	 */
	protected boolean isOneIndexedParameters() {
		return this.oneIndexedParameters;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
	 */
	public boolean supportsParameter(MethodParameter parameter) {
		return Pageable.class.equals(parameter.getParameterType());
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
	 */
	public Pageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

		assertPageableUniqueness(methodParameter);

		Pageable defaultOrFallback = getDefaultFromAnnotationOrFallback(methodParameter);

		String pageString = webRequest.getParameter(getParameterNameToUse(pageParameterName, methodParameter));
		String pageSizeString = webRequest.getParameter(getParameterNameToUse(sizeParameterName, methodParameter));

		boolean pageAndSizeGiven = StringUtils.hasText(pageString) && StringUtils.hasText(pageSizeString);

		if (!pageAndSizeGiven && defaultOrFallback == null) {
			return null;
		}

		int page = StringUtils.hasText(pageString) ? parseAndApplyBoundaries(pageString, Integer.MAX_VALUE, true)
				: defaultOrFallback.getPageNumber();
		int pageSize = StringUtils.hasText(pageSizeString) ? parseAndApplyBoundaries(pageSizeString, maxPageSize, false)
				: defaultOrFallback.getPageSize();

		// Limit lower bound
		pageSize = pageSize < 1 ? defaultOrFallback.getPageSize() : pageSize;
		// Limit upper bound
		pageSize = pageSize > maxPageSize ? maxPageSize : pageSize;

		Sort sort = sortResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

		// Default if necessary and default configured
		sort = sort == null && defaultOrFallback != null ? defaultOrFallback.getSort() : sort;

		return new PageRequest(page, pageSize, sort);
	}

	/**
	 * Returns the name of the request parameter to find the {@link Pageable} information in. Inspects the given
	 * {@link MethodParameter} for {@link Qualifier} present and prefixes the given source parameter name with it.
	 * 
	 * @param source the basic parameter name.
	 * @param parameter the {@link MethodParameter} potentially qualified.
	 * @return the name of the request parameter.
	 */
	protected String getParameterNameToUse(String source, MethodParameter parameter) {

		StringBuilder builder = new StringBuilder(prefix);

		if (parameter != null && parameter.hasParameterAnnotation(Qualifier.class)) {
			builder.append(parameter.getParameterAnnotation(Qualifier.class).value());
			builder.append(qualifierDelimiter);
		}

		return builder.append(source).toString();
	}

	private Pageable getDefaultFromAnnotationOrFallback(MethodParameter methodParameter) {

		if (methodParameter.hasParameterAnnotation(PageableDefault.class)) {
			return getDefaultPageRequestFrom(methodParameter);
		}

		return fallbackPageable;
	}

	private static Pageable getDefaultPageRequestFrom(MethodParameter parameter) {

		PageableDefault defaults = parameter.getParameterAnnotation(PageableDefault.class);

		Integer defaultPageNumber = defaults.page();
		Integer defaultPageSize = getSpecificPropertyOrDefaultFromValue(defaults, "size");

		if (defaultPageSize < 1) {
			Method annotatedMethod = parameter.getMethod();
			throw new IllegalStateException(String.format(INVALID_DEFAULT_PAGE_SIZE, annotatedMethod));
		}

		if (defaults.sort().length == 0) {
			return new PageRequest(defaultPageNumber, defaultPageSize);
		}

		return new PageRequest(defaultPageNumber, defaultPageSize, defaults.direction(), defaults.sort());
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T getSpecificPropertyOrDefaultFromValue(Annotation annotation, String property) {

		Object propertyDefaultValue = AnnotationUtils.getDefaultValue(annotation, property);
		Object propertyValue = AnnotationUtils.getValue(annotation, property);

		return (T) (ObjectUtils.nullSafeEquals(propertyDefaultValue, propertyValue) ? AnnotationUtils.getValue(annotation)
				: propertyValue);
	}

	/**
	 * Tries to parse the given {@link String} into an integer and applies the given boundaries. Will return 0 if the
	 * {@link String} cannot be parsed.
	 * 
	 * @param parameter the parameter value.
	 * @param upper the upper bound to be applied.
	 * @param shiftIndex whether to shift the index if {@link #oneIndexedParameters} is set to true.
	 * @return
	 */
	private int parseAndApplyBoundaries(String parameter, int upper, boolean shiftIndex) {

		try {
			int parsed = Integer.parseInt(parameter) - (oneIndexedParameters && shiftIndex ? 1 : 0);
			return parsed < 0 ? 0 : parsed > upper ? upper : parsed;
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	/**
	 * Asserts uniqueness of all {@link Pageable} parameters of the method of the given {@link MethodParameter}.
	 * 
	 * @param parameter must not be {@literal null}.
	 */
	public static void assertPageableUniqueness(MethodParameter parameter) {

		Method method = parameter.getMethod();

		if (containsMoreThanOnePageableParameter(method)) {
			Annotation[][] annotations = method.getParameterAnnotations();
			assertQualifiersFor(method.getParameterTypes(), annotations);
		}
	}
	
	/**
	 * Returns whether the given {@link Method} has more than one {@link Pageable} parameter.
	 * 
	 * @param method must not be {@literal null}.
	 * @return
	 */
	private static boolean containsMoreThanOnePageableParameter(Method method) {

		boolean pageableFound = false;

		for (Class<?> type : method.getParameterTypes()) {

			if (pageableFound && type.equals(Pageable.class)) {
				return true;
			}

			if (type.equals(Pageable.class)) {
				pageableFound = true;
			}
		}

		return false;
	}
	
	/**
	 * Asserts that every {@link Pageable} parameter of the given parameters carries an {@link Qualifier} annotation to
	 * distinguish them from each other.
	 * 
	 * @param parameterTypes must not be {@literal null}.
	 * @param annotations must not be {@literal null}.
	 */
	public static void assertQualifiersFor(Class<?>[] parameterTypes, Annotation[][] annotations) {

		Set<String> values = new HashSet<String>();

		for (int i = 0; i < annotations.length; i++) {

			if (Pageable.class.equals(parameterTypes[i])) {

				Qualifier qualifier = findAnnotation(annotations[i]);

				if (null == qualifier) {
					throw new IllegalStateException(
							"Ambiguous Pageable arguments in handler method. If you use multiple parameters of type Pageable you need to qualify them with @Qualifier");
				}

				if (values.contains(qualifier.value())) {
					throw new IllegalStateException("Values of the user Qualifiers must be unique!");
				}

				values.add(qualifier.value());
			}
		}
	}
	
	/**
	 * Returns a {@link Qualifier} annotation from the given array of {@link Annotation}s. Returns {@literal null} if the
	 * array does not contain a {@link Qualifier} annotation.
	 * 
	 * @param annotations must not be {@literal null}.
	 * @return
	 */
	public static Qualifier findAnnotation(Annotation[] annotations) {

		for (Annotation annotation : annotations) {
			if (annotation instanceof Qualifier) {
				return (Qualifier) annotation;
			}
		}

		return null;
	}
}
