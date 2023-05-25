package blog.dongguabai.dongguabai.security.core;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Map;

public class X5RequestHttpInputMessage implements HttpInputMessage {

    private final HttpServletRequest servletRequest;
    @Nullable
    private HttpHeaders headers;

    public X5RequestHttpInputMessage(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    @Override
    public InputStream getBody() throws IOException {
        Object data = servletRequest.getAttribute(X5REQUEST_ENCODE);
        data = data == null ? StringUtils.EMPTY : data;
        String content = (String) data;
        ByteArrayInputStream inputStream = new ByteArrayInputStream((content.getBytes()));
        return inputStream;

    }

    @Override
    public HttpHeaders getHeaders() {
        if (this.headers == null) {
            this.headers = new HttpHeaders();
            for (Enumeration<?> names = this.servletRequest.getHeaderNames(); names.hasMoreElements(); ) {
                String headerName = (String) names.nextElement();
                for (Enumeration<?> headerValues = this.servletRequest.getHeaders(headerName);
                     headerValues.hasMoreElements(); ) {
                    String headerValue = (String) headerValues.nextElement();
                    this.headers.add(headerName, headerValue);
                }
            }
            // HttpServletRequest exposes some headers as properties:
            // we should include those if not already present
            try {
                MediaType contentType = this.headers.getContentType();
                if (contentType == null) {
                    String requestContentType = this.servletRequest.getContentType();
                    if (StringUtils.isNotEmpty(requestContentType)) {
                        contentType = MediaType.parseMediaType(requestContentType);
                        this.headers.setContentType(contentType);
                    }
                }
                if (contentType != null && contentType.getCharset() == null) {
                    String requestEncoding = this.servletRequest.getCharacterEncoding();
                    if (StringUtils.isNotEmpty(requestEncoding)) {
                        Charset charSet = Charset.forName(requestEncoding);
                        Map<String, String> params = new LinkedCaseInsensitiveMap<>();
                        params.putAll(contentType.getParameters());
                        params.put("charset", charSet.toString());
                        MediaType mediaType = new MediaType(contentType.getType(), contentType.getSubtype(), params);
                        this.headers.setContentType(mediaType);
                    }
                }
            } catch (InvalidMediaTypeException ex) {
                // Ignore: simply not exposing an invalid content type in HttpHeaders...
            }
            if (this.headers.getContentLength() < 0) {
                int requestContentLength = this.servletRequest.getContentLength();
                if (requestContentLength != -1) {
                    this.headers.setContentLength(requestContentLength);
                }
            }
        }
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return this.headers;
    }


}
