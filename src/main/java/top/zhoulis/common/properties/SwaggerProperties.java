package top.zhoulis.common.properties;

import lombok.Data;

@Data
public class SwaggerProperties {
    private String basePackage;
    private String title;
    private String description;
    private String author;
    private String url;
    private String email;
    private String version;
}
