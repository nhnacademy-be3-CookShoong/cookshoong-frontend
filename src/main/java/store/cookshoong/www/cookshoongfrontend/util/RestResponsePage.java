package store.cookshoong.www.cookshoongfrontend.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * RestTemplate Page 반환을 받기 위한 객체.
 *
 * @param <T> the type parameter
 * @author eora21(김주호)
 * @since 2023.07.15
 */
public class RestResponsePage<T> extends PageImpl<T> {
    /**
     * json 형태로 수신한 페이지 정보를 PageImpl 객체로 내보낼 수 있도록 하는 생성자 메서드.
     *
     * @param content          the content
     * @param number           the number
     * @param size             the size
     * @param totalElements    the total elements
     * @param pageable         the pageable
     * @param last             the last
     * @param totalPages       the total pages
     * @param sort             the sort
     * @param first            the first
     * @param numberOfElements the number of elements
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RestResponsePage(@JsonProperty("content") List<T> content,
                            @JsonProperty("number") int number,
                            @JsonProperty("size") int size,
                            @JsonProperty("totalElements") Long totalElements,
                            @JsonProperty("pageable") JsonNode pageable,
                            @JsonProperty("last") boolean last,
                            @JsonProperty("totalPages") int totalPages,
                            @JsonProperty("sort") JsonNode sort,
                            @JsonProperty("first") boolean first,
                            @JsonProperty("numberOfElements") int numberOfElements) {

        super(content, PageRequest.of(number, size), totalElements);
    }

    /**
     * pageable 형태로 수신한 페이지 정보를 PageImpl 객체로 내보낼 수 있도록 하는 생성자 메서드.
     *
     * @param content  the content
     * @param pageable the pageable
     * @param total    the total
     */
    public RestResponsePage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    /**
     * 리스트 형태로 수신한 페이지 정보를 PageImpl 객체로 내보낼 수 있도록 하는 생성자 메서드.
     *
     * @param content the content
     */
    public RestResponsePage(List<T> content) {
        super(content);
    }

    /**
     * 빈 형태로 수신한 페이지 정보를 PageImpl 객체로 내보낼 수 있도록 하는 생성자 메서드.
     */
    public RestResponsePage() {
        super(new ArrayList<>());
    }

    /**
     * pageable 객체를 파라미터 값으로 전환하는 메서드.
     *
     * @param uri      the uri
     * @param pageable the pageable
     * @return the uri
     */
    public static URI pageableToParameter(URI uri, Pageable pageable) {
        return UriComponentsBuilder.fromUri(uri)
            .queryParam("page", pageable.getPageNumber())
            .queryParam("size", pageable.getPageSize())
            .queryParam("sort", pageable.getSort().toString())
            .build().toUri();
    }

    /**
     * pageable 객체를 파라미터 값으로 전환하는 메서드.
     *
     * @param uriString the uri string
     * @param pageable  the pageable
     * @return the uri
     */
    public static URI pageableToParameter(String uriString, Pageable pageable) {
        return pageableToParameter(URI.create(uriString), pageable);
    }

    /**
     * pageable 객체를 파라미터 값으로 전환하는 메서드.
     *
     * @param builder  the builder
     * @param pageable the pageable
     * @return the uri
     */
    public static URI pageableToParameter(UriComponentsBuilder builder, Pageable pageable) {
        return pageableToParameter(builder.build().toUri(), pageable);
    }

    /**
     * pageable 객체를 파라미터 값으로 전환하는 메서드.
     *
     * @param components the components
     * @param pageable   the pageable
     * @return the uri
     */
    public static URI pageableToParameter(UriComponents components, Pageable pageable) {
        return pageableToParameter(UriComponentsBuilder.fromUri(components.toUri()), pageable);
    }
}
